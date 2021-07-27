package com.mydiary.api.users.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mydiary.api.users.data.TasksServiceClient;
import com.mydiary.api.users.data.UserEntity;
import com.mydiary.api.users.data.UsersRepository;
import com.mydiary.api.users.shared.UserDTO;
import com.mydiary.api.users.ui.model.TaskResponseModel;

@Service
public class UserServiceImpl implements UsersService {

	UsersRepository usersRepository;

	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	ModelMapper modelMapper;
	
	Environment environment;
	
	//RestTemplate restTemplate;
	
	TasksServiceClient taskServiceClient;

	@Autowired
	public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, 
			Environment environment, /*RestTemplate restTemplate*/ TasksServiceClient taskServiceClient) {
		this.usersRepository = usersRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.environment = environment;
		//this.restTemplate = restTemplate;
		this.taskServiceClient = taskServiceClient;
	}

	@Override
	public UserDTO registerUser(UserDTO userDetails) {

		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

//		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);

		usersRepository.save(userEntity);

		UserDTO createdUserDTO = modelMapper.map(userEntity, UserDTO.class);

		return createdUserDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity userEntity = usersRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public UserDTO getUserDetailsByEmail(String email) {

		UserEntity userEntity = usersRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return modelMapper.map(userEntity, UserDTO.class);
	}

	@Override
	public UserDTO getUserById(String userId) {

		UserEntity userEntity = usersRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
		
		//http://TASKS-WS/users/188f2ddd-ce24-465a-9583-22dab8bb175e/tasks
		//http://TASKS-WS/users/%s/tasks
//        String taskURL = String.format(environment.getProperty("tasks.url"), userId);
//        
//        ResponseEntity<List<TaskResponseModel>> taskListResponse = restTemplate.exchange(taskURL, HttpMethod.GET, null, new ParameterizedTypeReference<List<TaskResponseModel>>() {
//        });
//        List<TaskResponseModel> taskList = taskListResponse.getBody(); 
		
		List<TaskResponseModel> taskList = taskServiceClient.getTasks(userId);
        
        userDTO.setTasks(taskList);
		
		return userDTO;
	}

	@Override
	public UserDTO updateUser(UserDTO userDetails) {
		
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity =  modelMapper.map(userDetails, UserEntity.class);
		
		usersRepository.save(userEntity);
		
		UserDTO updatedUserDTO =  modelMapper.map(userEntity, UserDTO.class);
		
		return updatedUserDTO;
	}
	
	@Override
	public void deleteUser(String userId) {
		usersRepository.deleteById(userId);
	}

	@Override
	public List<UserDTO> searchUser(String keyword) {
		
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<UserEntity> serachedUsersEntityList = usersRepository.searchUser(keyword);
		
		Type listType = new TypeToken<List<UserDTO>>(){}.getType();
		List<UserDTO> searchedUsersDTOList = modelMapper.map(serachedUsersEntityList, listType);
		
		return searchedUsersDTOList;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<UserEntity> usersEntityList = usersRepository.findAll();
		
		Type listType = new TypeToken<List<UserDTO>>(){}.getType();
		List<UserDTO> usersDTOList =  modelMapper.map(usersEntityList, listType);
		
		return usersDTOList;
	}
}
