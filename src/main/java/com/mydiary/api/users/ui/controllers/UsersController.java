package com.mydiary.api.users.ui.controllers;

import java.lang.reflect.Type;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydiary.api.users.service.UsersService;
import com.mydiary.api.users.shared.UserDTO;
import com.mydiary.api.users.ui.model.UserRequestModel;
import com.mydiary.api.users.ui.model.UserResponseModel;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private Environment env;

	@Autowired
	UsersService usersService;

	@Autowired
	ModelMapper mapper;

//	@GetMapping("/status/check")
//	public String status() {
//		return "Working on port " + env.getProperty("local.server.port");
//	}

	// Just for testing connectivity with angular frontend
	@GetMapping("/status/check")
	public UserResponseModel status() {
		// throw new RuntimeException("Some Error has occured. Please contact support
		// team.");
		return new UserResponseModel("My app is working on port " + env.getProperty("local.server.port"));
	}

	/**
	 * Register User
	 * 
	 * @param userDetails
	 * @return
	 */
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> registerUser(@Valid @RequestBody UserRequestModel userDetails) {

//		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDTO userDTO = mapper.map(userDetails, UserDTO.class);
		UserDTO createdUserDTO = usersService.registerUser(userDTO);

		UserResponseModel userResponseModel = mapper.map(createdUserDTO, UserResponseModel.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseModel);
	}

	/**
	 * Get User by Id
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping(path = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> getUserById(@PathVariable String userId) {
		UserDTO userDTO = usersService.getUserById(userId);

		UserResponseModel userResponseModel = mapper.map(userDTO, UserResponseModel.class);

		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}

	/**
	 * Update User
	 * 
	 * @param userId
	 * @param userDetails
	 * @return
	 */
	@PutMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserResponseModel> updateUser(@PathVariable String userId,
			@RequestBody UserRequestModel userDetails) {

//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserDTO userDTO = mapper.map(userDetails, UserDTO.class);
		userDTO.setUserId(userId);
		UserDTO updatedUserDTO = usersService.updateUser(userDTO);

		UserResponseModel userResponseModel = mapper.map(updatedUserDTO, UserResponseModel.class);

		return ResponseEntity.status(HttpStatus.OK).body(userResponseModel);
	}

	/**
	 * Delete User
	 * 
	 * @param userId
	 * @return
	 */
	@DeleteMapping(path = "/{userId}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> deleteUser(@PathVariable String userId) {

		usersService.deleteUser(userId);

		return ResponseEntity.noContent().build();
	}

	/**
	 * Get All Users
	 * 
	 * @return
	 */
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserResponseModel>> getAllUsers() {
		
		List<UserDTO> userDTOList = usersService.getAllUsers();
		
		Type listType = new TypeToken<List<UserResponseModel>>(){}.getType();
		List<UserResponseModel> userResponseModelList = mapper.map(userDTOList, listType);

		return ResponseEntity.status(HttpStatus.OK).body(userResponseModelList);
	}
	
	/**
	 * Search User
	 * 
	 * @param keyword
	 * @return
	 */
	@GetMapping(path = "/search/{keyword}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserResponseModel>> searchUser(@PathVariable String keyword) {
		List<UserDTO> userDTOList = usersService.searchUser(keyword);

		Type listType = new TypeToken<List<UserResponseModel>>(){}.getType();
		List<UserResponseModel> userResponseModelList = mapper.map(userDTOList, listType);

		return ResponseEntity.status(HttpStatus.OK).body(userResponseModelList);
	}

}
