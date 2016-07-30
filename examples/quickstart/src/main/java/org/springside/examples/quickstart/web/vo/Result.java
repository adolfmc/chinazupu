package org.springside.examples.quickstart.web.vo;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private String message;
	private boolean success = true;
	private List<Object> results = new ArrayList<Object>();

	public Result() {
	}

	public Result(boolean success, String message) {
		this.setSuccess(success);
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<Object> getResults() {
		return results;
	}

	public void setResults(List<Object> results) {
		this.results = results;
	}

	public static Result getInstance() {
		return new Result();
	}
}
