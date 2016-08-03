/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.service.task;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.quickstart.entity.Clan;
import org.springside.examples.quickstart.entity.Task;
import org.springside.examples.quickstart.entity.WifeAHusBd;
import org.springside.examples.quickstart.repository.ClanDao;
import org.springside.examples.quickstart.repository.TaskDao;
import org.springside.examples.quickstart.repository.WifeAHusBdDao;
import org.springside.examples.quickstart.util.CodeGenerator;
import org.springside.examples.quickstart.web.vo.Result;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

// Spring Bean的标识.
@Component
// 类中所有public函数都纳入事务管理的标识.
@Transactional
public class TaskService {

	private TaskDao taskDao;

	@Autowired
	private WifeAHusBdDao wifeAHusBdDao;

	@Autowired
	private ClanDao clanDao;

	public String getWifeOrHusBandsNames(Long id) {
		Task me = taskDao.findOne(id);

		StringBuffer wifesName = new StringBuffer(5);
		if ("男".equals(me.getGender())) {
			List<WifeAHusBd> whs = wifeAHusBdDao.findByHusbandId(id);
			wifesName.append("妻子  ( ");
			for (WifeAHusBd wifeAHusBd : whs) {
				wifesName.append(wifeAHusBd.getWife().getName() + " ");
			}

			if (whs.isEmpty()) {
				wifesName.append("未成家");
			}

		} else if ("女".equals(me.getGender())) {
			List<WifeAHusBd> whs = wifeAHusBdDao.findByWifeId(id);
			wifesName.append("丈夫  ( ");
			for (WifeAHusBd wifeAHusBd : whs) {
				wifesName.append(wifeAHusBd.getHusband().getName() + " ");
			}
			if (whs.isEmpty()) {
				wifesName.append("未成家");
			}

		}
		wifesName.append(" ) ");
		return wifesName.toString();
	}

	public List<Task> getInfosByPidAndRelationOrderByIdDesc(Long pid, String relation) {
		return taskDao.findByParentsAndRelationOrderByIdDesc(pid, relation);
	}

	public List<Task> getChildsByCode(String code) {
		return taskDao.findByCodeStartingWith(code);
	}

	public Task getTask(Long id) {
		return taskDao.findOne(id);
	}

	public Result saveTask(Task entity) {
		Result result = Result.getInstance();
		if (entity.getParents() == 0L) {
			if (getTasksByParent(0L, entity.getClanId()).isEmpty() == false) {
				entity.setCode("000");
				result.setSuccess(false);
				result.setMessage("顶级宗族已建立.");
				return result;
			} else {
				Clan clan = new Clan();
				clan.setBranch(UUID.randomUUID().toString());
				clan.setSurname(entity.getName().substring(1));
				entity.setCode(getNextChildCodeByPid(entity.getParents()));
				clanDao.save(clan);
			}
		}

		if ("夫妻".equals(entity.getGenerations()) && "男".equals(entity.getGender())) {
			WifeAHusBd wh = new WifeAHusBd();
			wh.setHusband(entity);
			wh.setWife(taskDao.findOne(entity.getParents()));
			wifeAHusBdDao.save(wh);
		} else if ("夫妻".equals(entity.getGenerations()) && "女".equals(entity.getGender())) {
			WifeAHusBd wh = new WifeAHusBd();
			wh.setHusband(taskDao.findOne(entity.getParents()));
			wh.setWife(entity);
			wifeAHusBdDao.save(wh);
		}

		taskDao.save(entity);
		return result;
	}

	public String getNextChildCodeByPid(Long pid) {
		List<Task> tasks = taskDao.findByParentsOrderByIdDesc(pid);
		String code = null;
		if (tasks.isEmpty()) {
			code = taskDao.findOne(pid).getCode() + "000";

		} else {
			code = CodeGenerator.getChildNext(tasks.get(0).getCode());
		}

		return code;
	}

	public void deleteTask(Long id) {
		taskDao.delete(id);
	}

	public List<Task> getAllTask() {
		return (List<Task>) taskDao.findAll();
	}

	public List<Task> getTasksByParent(Long pid, Long clanId) {
		return taskDao.findByParentsAndClanIdAndIdNotOrderByIdDesc(pid, clanId, pid);
	}

	public Page<Task> getUserTask(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize, String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Task> spec = buildSpecification(userId, searchParams);

		return taskDao.findAll(spec, pageRequest);
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<Task> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Task> spec = DynamicSpecifications.bySearchFilter(filters.values(), Task.class);
		return spec;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

}
