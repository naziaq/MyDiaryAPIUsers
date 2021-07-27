package com.mydiary.api.users.data;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mydiary.api.users.ui.model.TaskResponseModel;

@FeignClient(name = "tasks-ws")
public interface TasksServiceClient {

	@GetMapping("/users/{userId}/tasks")
	public List<TaskResponseModel> getTasks(@PathVariable String userId);
}
