package com.mydiary.api.users;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.mydiary.api.users.service.UsersService;
import com.mydiary.api.users.shared.UserDTO;
import com.mydiary.api.users.ui.controllers.UsersController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UsersController.class)
//@SpringBootTest
@WithMockUser
class MyDiaryAPIUsersApplicationTests {

	@Test
	void contextLoads() {
	}
	
	private final static String TEST_USER_ID = "c4994ba1-33ab-4832-9c0a-ad71b9f41264";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UsersService usersService;
	
	UserDTO userDTO = new UserDTO("Rumana", "Anwar", "test07@test.com", null, "c4994ba1-33ab-4832-9c0a-ad71b9f41264");
	
	//String exampleUserResponseJson = "{\"firstName\":\"Rumana\",\"lastName\":\"Anwar\",\"email\":\"test07@test.com\",\"userId\":\"c4994ba1-33ab-4832-9c0a-ad71b9f41264\"}";
	
	@Test
	public void getUserById() throws Exception{
		
		Mockito.when(usersService.getUserById(Mockito.anyString())).thenReturn(userDTO);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/users/"+TEST_USER_ID)
				//.with(user(TEST_USER_ID))
				//.with(csrf())
				.accept(MediaType.APPLICATION_JSON);
				//.header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjNDk5NGJhMS0zM2FiLTQ4MzItOWMwYS1hZDcxYjlmNDEyNjQiLCJleHAiOjE2Mjc5NTc5MzN9.8lcCN4JMT8PRaFWk8GzRGyhMEm-Po8pxhAxPvdlSdHIPFuEbdl9-bdwyFFN1IksDpIJ3ag4aU43747Q1yXApPA");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
		
		String expected = "{firstName:Rumana,lastName:Anwar,email:test07@test.com,userId:c4994ba1-33ab-4832-9c0a-ad71b9f41264}";
		
		// {"firstName": "Rumana","lastName": "Anwar","email": "test07@test.com","userId": "c4994ba1-33ab-4832-9c0a-ad71b9f41264","statusMessage": null,"tasks": []}
		
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

}
