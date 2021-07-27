package com.mydiary.api.users.ui.controllers;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mydiary.api.users.service.UsersService;
import com.mydiary.api.users.shared.UserDTO;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersController.class)
@WithMockUser
public class UsersControllerTest {
	
	private final static String TEST_USER_ID = "c4994ba1-33ab-4832-9c0a-ad71b9f41264";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsersService usersService;
	
	UserDTO userDTO = new UserDTO("Rumana", "Anwar", "test07@test.com", null, "c4994ba1-33ab-4832-9c0a-ad71b9f41264");
	
	String exampleUserResponseJson = "{\"firstName\":\"Aamir\",\"lastName\":\"Ali\",\"password\":\"13041982\",\"email\":\"AamirAli@test.com\"}";
	
	@Test
	public void getUserById() throws Exception{
		
		Mockito.when(usersService.getUserById(Mockito.anyString())).thenReturn(userDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/users/"+TEST_USER_ID)				
				.accept(MediaType.APPLICATION_JSON);
				
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		String expected = "{firstName:Rumana,lastName:Anwar,email:test07@test.com,userId:c4994ba1-33ab-4832-9c0a-ad71b9f41264}";
		
		// {"firstName": "Rumana","lastName": "Anwar","email": "test07@test.com","userId": "c4994ba1-33ab-4832-9c0a-ad71b9f41264","statusMessage": null,"tasks": []}
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
	@Test
	public void registerUser() throws Exception{
		
		UserDTO mockUserDTO = new UserDTO("Aamir", "Ali", "AamirAli@test.com", "13041982", null);
		
		Mockito.when(usersService.registerUser(Mockito.any(UserDTO.class))).thenReturn(mockUserDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users/")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.content(exampleUserResponseJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

}
