package com.mydiary.api.users;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyDiaryAPIUsersApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class MyDiaryAPIUsersApplicationTests2 {
	
	private final static String TEST_USER_ID = "c4994ba1-33ab-4832-9c0a-ad71b9f41264";
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void testGetUserById() throws Exception {
		
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/users/"+TEST_USER_ID), HttpMethod.GET, entity, String.class);
		
		String expected = "{firstName:Rumana,lastName:Anwar,email:test07@test.com,userId:c4994ba1-33ab-4832-9c0a-ad71b9f41264}";
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}


}
