package org.springside.examples.quickstart.web.vo;

import java.util.List;

import org.springside.examples.quickstart.entity.Task;

public class InfoVo {

	// me
	private Task mInfo;
	// wifes
	private List<Task> wInfos;
	// hasbands
	private List<Task> hInfos;
	// childrens
	private List<Task> cInfos;

	public List<Task> gethInfos() {
		return hInfos;
	}

	public void sethInfos(List<Task> hInfos) {
		this.hInfos = hInfos;
	}

	public Task getmInfo() {
		return mInfo;
	}

	public void setmInfo(Task mInfo) {
		this.mInfo = mInfo;
	}

	public List<Task> getwInfos() {
		return wInfos;
	}

	public void setwInfos(List<Task> wInfos) {
		this.wInfos = wInfos;
	}

	public List<Task> getcInfos() {
		return cInfos;
	}

	public void setcInfos(List<Task> cInfos) {
		this.cInfos = cInfos;
	}

	// private String surname;
	// private String fullName;
	// private String generations;
	private String posteritys;
	private String wifeOrHasbandNames;

	public Long getId() {
		return mInfo.getId();
	}

	public String getSurname() {
		return mInfo.getName().substring(0, 1);
	}

	public String getFullName() {
		return mInfo.getName();
	}

	public String getGenerations() {
		return mInfo.getGenerations();
	}

	public String getPosteritys() {
		return posteritys;
	}

	public void setPosteritys(String posteritys) {
		this.posteritys = posteritys;
	}

	public String getWifeOrHasbandNames() {
		return wifeOrHasbandNames;
	}

	public void setWifeOrHasbandNames(String wifeOrHasbandNames) {
		this.wifeOrHasbandNames = wifeOrHasbandNames;
	}
}
