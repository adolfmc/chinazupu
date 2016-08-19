/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.entity;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

//JPA标识
@Entity
@Table(name = "ss_task")
public class Task extends IdEntity {
	private String title;
	private String description;
	private User user;
	private Long clanId;
	private Long userId;
	private String name;
	private String generations;
	private String branch;
	private Long age;
	private String birthday;
	private String gender;
	private String pic;
	private Long parents;
	private String relation;
	private String introduction;
	private String code;
	private String pName;
	private String status;

	private ArrayList<Task> parentts = new ArrayList<Task>();
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@Transient
	public ArrayList<Task> getParentts() {
		return parentts;
	}

	public void setParentts(ArrayList<Task> parentts) {
		this.parentts = parentts;
	}


	public Long getClanId() {
		return clanId;
	}

	public void setClanId(Long clanId) {
		this.clanId = clanId;
	}

	@Transient
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenerations() {
		return generations;
	}

	public void setGenerations(String generations) {
		this.generations = generations;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Long getParents() {
		return parents;
	}

	public void setParents(Long parents) {
		this.parents = parents;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	// JSR303 BeanValidator的校验规则
	// @NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	// JPA 基于USER_ID列的多对一关系定义
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Column(length=2000)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
