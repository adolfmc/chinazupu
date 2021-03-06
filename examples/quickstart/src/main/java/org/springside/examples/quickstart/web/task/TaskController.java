/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.web.task;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.examples.quickstart.entity.Task;
import org.springside.examples.quickstart.service.account.ShiroDbRealm.ShiroUser;
import org.springside.examples.quickstart.service.task.TaskService;
import org.springside.examples.quickstart.web.vo.InfoVo;
import org.springside.examples.quickstart.web.vo.Result;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;

/**
 * Task管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /task/ Create page : GET /task/create Create action : POST
 * /task/create Update page : GET /task/update/{id} Update action : POST
 * /task/update Delete action : GET /task/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/task")
public class TaskController {

	private static final String PAGE_SIZE = "3";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private TaskService taskService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber, @RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Task> tasks = taskService.getUserTask(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("tasks", tasks);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "task/taskList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("task", new Task());
		model.addAttribute("action", "create");
		return "task/taskForm";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("task", taskService.getTask(id));
		model.addAttribute("action", "update");
		return "task/taskForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) {
		taskService.saveTask(task);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/task/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		taskService.deleteTask(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/task/";
	}


	@ResponseBody
	@RequestMapping(value = "getIndexTasks")
	public Result getIndexTask(Long id) {
		Result result = Result.getInstance();
		InfoVo info = new InfoVo();
		Task me = taskService.getTask(id);
		List<Task> childs = taskService.getTasksByIdAndClanId(id, me.getClanId());
		List<Task> childscount = taskService.getChildsByCodeAndClanId(me.getCode(),me.getClanId());
		info.setmInfo(me);
		info.setcInfos(childs);

		List<Task> wifes = taskService.getInfosByPidAndRelationOrderByIdDesc(id, "夫妻");
		if (id == 0) {
			info.setPosteritys(String.valueOf(childscount.size() - 1 - wifes.size()));
		} else {
			info.setPosteritys(String.valueOf(childscount.size() - 1));
		}

		info.setWifeOrHasbandNames(taskService.getWifeOrHusBandsNames(id));
		result.setResults(info);
		return result;
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getTask(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("task", taskService.getTask(id));
		}
	}

	@ResponseBody
	@RequestMapping(value = "getTaskById")
	public Result getTask(Long id) {
		Result result = Result.getInstance();
		InfoVo info = new InfoVo();
		Task me = taskService.getTask(id);
		List<Task> childs = taskService.getTasksByIdAndClanId(id, me.getClanId());
		List<Task> childscount = taskService.getChildsByCodeAndClanId(me.getCode(), me.getClanId());
		info.setmInfo(me);
		info.setcInfos(childs);

		List<Task> wifes = taskService.getInfosByPidAndRelationOrderByIdDesc(id, "夫妻");
		if (id == 0) {
			info.setPosteritys(String.valueOf(childscount.size() - 1 - wifes.size()));
		} else {
			info.setPosteritys(String.valueOf(childscount.size() - 1));
		}

		info.setWifeOrHasbandNames(taskService.getWifeOrHusBandsNames(id));

		result.setResults(info);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "edit")
	public Result edit(Long id) {
		Result result = Result.getInstance();
		InfoVo info = new InfoVo();
		Task me = taskService.getTask(id);
		Task t = taskService.getTask(me.getParents());
		me.setpName(t.getName());
		info.setmInfo(me);
		result.setResults(info);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "remove")
	public Result remove(Long id) {
		taskService.removeById(id);
		return Result.getInstance();
	}

	@ResponseBody
	@RequestMapping(value = "create")
	public Result create(Task newTask, String relation) {
		newTask.setPic("img/150x165/durgesh-soni.png");
		return taskService.saveTask(newTask);
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
