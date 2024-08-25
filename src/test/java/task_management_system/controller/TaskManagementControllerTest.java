package task_management_system.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import task_management_system.controller.request.CreateTaskRequest;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import task_management_system.entity.Task;
import task_management_system.enums.Priority;
import task_management_system.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
class TaskManagementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreateTaskRequest createTaskRequest;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    void testCreateTaskRequest() throws Exception{
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setUserId(1);
        createTaskRequest.setTitle("Sample Task");
        createTaskRequest.setDescription("This is a sample description.");
        createTaskRequest.setPriority(Priority.HIGH);
        createTaskRequest.setDueDate(LocalDate.now().plusDays(1));
        mockMvc.perform(post("/api/v1/task-management")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTaskRequest)))
                .andExpect(status().isCreated());
    }
    @Test
    void testCreateTaskRequest_InvalidTitle() throws Exception {
        CreateTaskRequest invalidBody = new CreateTaskRequest();
        invalidBody.setUserId(1);
        invalidBody.setDescription("This is a sample description.");
        invalidBody.setPriority(Priority.HIGH);
        invalidBody.setDueDate(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/v1/task-management")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Title cannot be empty."))
                .andExpect(jsonPath("$.timeStamp").exists());
    }

    @Test
    void testCreateTaskRequest_InvalidUserID() throws Exception {
        CreateTaskRequest invalidBody = new CreateTaskRequest();
        invalidBody.setTitle("Sample");
        invalidBody.setDescription("This is a sample description.");
        invalidBody.setPriority(Priority.HIGH);
        invalidBody.setDueDate(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/v1/task-management")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("UserId cannot be null."))
                .andExpect(jsonPath("$.timeStamp").exists());
    }

    @Test
    void testCreateTaskRequest_InvalidDescription() throws Exception {
        CreateTaskRequest invalidBody = new CreateTaskRequest();
        invalidBody.setUserId(1);
        invalidBody.setTitle("Sample");
        invalidBody.setPriority(Priority.HIGH);
        invalidBody.setDueDate(LocalDate.now().plusDays(1));

        mockMvc.perform(post("/api/v1/task-management")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidBody)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Description cannot be empty."))
                .andExpect(jsonPath("$.timeStamp").exists());
    }
    @Test
    public void testGetTaskById() throws Exception {
        List<Task> tasks = taskRepository.findAll();
        mockMvc.perform(get("/api/v1/task-management/{id}", tasks.get(0).getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").exists());
    }



}