package org.springside.examples.quickstart.web.vo;

import java.util.List;

import org.springside.examples.quickstart.entity.Task;

public class InfoVo {
	
	// me
	private Task mInfo;
	// wifes
	private List<Task> wInfos;
	// childrens
	private List<Task> cInfos;

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

//	private String surname;
//	private String fullName;
//	private String generations;
//	private String posteritys;

	public String getSurname() {
		return mInfo.getName().substring(0);
	}

	public String getFullName() {
		return mInfo.getName();
	}

	public String getGenerations() {
		return mInfo.getGenerations();
	}

	public String getPosteritys() {
		return null;
	}
	
	
}
