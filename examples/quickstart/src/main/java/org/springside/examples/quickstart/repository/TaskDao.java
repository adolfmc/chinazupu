/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Task;

public interface TaskDao extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {

	Page<Task> findByUserId(Long id, Pageable pageRequest);

	@Modifying
	@Query("delete from Task task where task.userId=?1")
	void deleteByUserId(Long id);

	List<Task> findByParentsAndClanIdAndIdNotAndStatusNotOrderByIdDesc(Long pid, Long clanId,Long parentId,String status);

	List<Task> findByParentsAndStatusNotOrderByIdDesc(Long pid,String status);

	List<Task> findByParentsAndRelationAndStatusNotOrderByIdDesc(Long pid, String relation,String status);

	List<Task> findByUserIdAndCodeAndStatusNot(Long userId, String code, String status);

	List<Task> findByCodeStartingWithAndClanIdAndStatusNot(String code, Long clanId, String status);

	@Modifying
	@Query(value = "delete ss_task  t where t.id in (  select t.id from ss_task t left join ss_task t2 on t.clan_id = t2.clan_id and t.code like t2.code||'%' and t2.id =?1 )" ,nativeQuery=true)
	void removeById(Long id);

}
