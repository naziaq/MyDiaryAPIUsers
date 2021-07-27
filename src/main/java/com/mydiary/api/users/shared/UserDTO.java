package com.mydiary.api.users.shared;

import java.io.Serializable;
import java.util.List;

import com.mydiary.api.users.ui.model.TaskResponseModel;



public class UserDTO implements Serializable {

	private static final long serialVersionUID = -5604929241402261240L;
	
	//private String id;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	private String userId;
	private String encryptedPassword;
	private List<TaskResponseModel> tasks;
	
	public UserDTO() {
		
	}
	
	public UserDTO(String firstName, String lastName, String email, String password, String userId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.userId = userId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public List<TaskResponseModel> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskResponseModel> tasks) {
		this.tasks = tasks;
	}
	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", userId=" + userId
				+ "]";
	}

	

}
