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

}
