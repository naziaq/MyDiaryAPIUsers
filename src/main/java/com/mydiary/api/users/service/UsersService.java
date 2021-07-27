package com.mydiary.api.users.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mydiary.api.users.shared.UserDTO;

public interface UsersService extends UserDetailsService {
	
	UserDTO registerUser(UserDTO userDetails);
	
	UserDTO getUserDetailsByEmail(String email);
	
	UserDTO getUserById(String userId);
	
	UserDTO updateUser(UserDTO userDetails);
	
	void deleteUser(String userId);
	
	List<UserDTO> searchUser(String keyword);
	
	List<UserDTO> getAllUsers();

}
