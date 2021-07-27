package com.mydiary.api.users.ui.controllers;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mydiary.api.users.MyDiaryAPIUsersApplication;
import com.mydiary.api.users.shared.UserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyDiaryAPIUsersApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsersControllerIT {

	private final static String TEST_USER_ID = "c4994ba1-33ab-4832-9c0a-ad71b9f41264";

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Test
	public void testGetUserById() throws Exception {

		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/users/" + TEST_USER_ID),
				HttpMethod.GET, entity, String.class);

		String expected = "{firstName:Rumana,lastName:Anwar,email:test07@test.com,userId:c4994ba1-33ab-4832-9c0a-ad71b9f41264}";

		JSONAssert.assertEquals(expected, response.getBody(), false);
	}

	@Test
	public void testRegisterUser() throws Exception {

		UserDTO userDTO = new UserDTO("Nadir", "Ali", "nadirAli@test.com", "29041986", null);

		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<UserDTO> entity = new HttpEntity<UserDTO>(userDTO, headers);
		
		ResponseEntity<UserDTO> response = restTemplate.exchange(createURLWithPort("/users/"), HttpMethod.POST, entity, UserDTO.class);
				
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
