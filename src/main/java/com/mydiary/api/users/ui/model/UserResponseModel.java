package com.mydiary.api.users.ui.model;

import java.util.List;

public class UserResponseModel {
	
	private String firstName;
	private String lastName;
	private String email;
	private String userId;
	private String statusMessage;	
	private List<TaskResponseModel> tasks;
	
	public UserResponseModel() {
		
	}
	
	// Just for testing connectivity with angular frontend
	public UserResponseModel(String statusMessage) {
		super();
		this.statusMessage = statusMessage;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<TaskResponseModel> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskResponseModel> tasks) {
		this.tasks = tasks;
	}


}
