package task_management_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import task_management_system.controller.request.CreateTaskRequest;
import task_management_system.controller.request.UpdateTaskRequest;
import task_management_system.controller.response.TaskResponse;
import task_management_system.service.TaskManagementService;
import task_management_system.util.PageResponse;



@RestController
@RequestMapping(path = "/api/v1/task-management")
public class TaskManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagementController.class);

    @Autowired
    private TaskManagementService taskManagementService;

    @Operation(summary = "Create task")
    @ApiResponse(responseCode = "201", description = "Task created successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Integer response = taskManagementService.createTask(request);
        LOGGER.info("Task created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Delete task")
    @ApiResponse(responseCode = "200", description = "Task deleted successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer id) {
        Integer response = taskManagementService.deleteTask(id);
        LOGGER.info("Task deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Update task")
    @ApiResponse(responseCode = "200", description = "Task updated successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Integer.class))})
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateTask(@PathVariable Integer id,@Valid @RequestBody UpdateTaskRequest request) {
        Integer response = taskManagementService.updateTask(id, request);
        LOGGER.info("Task updated successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get task by id")
    @ApiResponse(responseCode = "200", description = "Task retrieved successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskResponse.class))})
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable Integer id) {
        TaskResponse response = taskManagementService.getTaskById(id);
        LOGGER.info("Task retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Get all tasks")
    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully...", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PageResponse.class))})
    @GetMapping
    public ResponseEntity<?> getAllTasks( @RequestParam(value = "pageNo", required = true) Integer pageNo,
                                                @RequestParam(value = "pageSize", required = true) Integer pageSize) {
        PageResponse response = taskManagementService.getAllTasks(pageNo,pageSize);
        LOGGER.info("Tasks retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
