package task_management_system.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import task_management_system.controller.request.CreateTaskRequest;
import task_management_system.controller.request.UpdateTaskRequest;
import task_management_system.controller.response.TaskResponse;
import task_management_system.entity.Task;
import task_management_system.entity.User;
import task_management_system.enums.Priority;
import task_management_system.enums.TaskStatus;
import task_management_system.repository.TaskRepository;
import task_management_system.repository.UserRepository;
import task_management_system.util.PageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskManagementServiceImplTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskManagementServiceImpl taskManagementService;

    private CreateTaskRequest createTaskRequest;
    private UpdateTaskRequest updateTaskRequest;
    User user;
    Task task;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setId(1);
        user.setEmail("john@gmail.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        task = new Task();
        task.setId(1);
        task.setUserId(user);
        task.setDescription("Test Task");
        task.setPriority(Priority.HIGH);
        task.setStatus(TaskStatus.NEW);
        task.setCreateDate(LocalDate.now());
        task.setCreatedAt(LocalDateTime.now());

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setUserId(1);
        createTaskRequest.setDescription("Test Task");

        updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setUserId(1);
        updateTaskRequest.setDescription("Updated Task Description");
    }

    @Test
    void testCreateTask_Valid() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Integer taskId = taskManagementService.createTask(createTaskRequest);

        assertEquals(task.getId(), taskId);
        verify(taskRepository, times(1)).save(any(Task.class));

    }

    @Test
    void testCreateTask_InvalidUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskManagementService.createTask(createTaskRequest)
        );

        assertEquals("Not found any user for given userId : 1", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testDeleteTask_Valid() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(task));

        Integer deletedTaskId = taskManagementService.deleteTask(1);

        assertEquals(1, deletedTaskId);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_Invalid() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskManagementService.deleteTask(1)
        );

        assertEquals("Not found any task for given taskId : 1", exception.getMessage());
        verify(taskRepository, never()).delete(any(Task.class));
    }

    @Test
    void testUpdateTask_Valid() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(task));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Integer updatedTaskId = taskManagementService.updateTask(1, updateTaskRequest);

        assertEquals(task.getId(), updatedTaskId);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_InvalidTask() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskManagementService.updateTask(1, updateTaskRequest)
        );

        assertEquals("Not found any task for given taskId : 1", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testUpdateTask_InvalidUser() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(task));
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskManagementService.updateTask(1, updateTaskRequest)
        );

        assertEquals("Not found any user for given userId : 1", exception.getMessage());
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    void testGetTaskById_Valid() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.of(task));

        TaskResponse taskResponse = taskManagementService.getTaskById(1);

        assertNotNull(taskResponse);
        assertEquals(task.getId(), taskResponse.getId());
        verify(taskRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetTaskById_Invalid() {
        when(taskRepository.findById(anyInt())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                taskManagementService.getTaskById(1)
        );
        assertEquals("Not found any task for given taskId : 1", exception.getMessage());
        verify(taskRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetAllTasks_Valid() {
        List<Task> tasks = Collections.singletonList(task);
        Page<Task> page = new PageImpl<>(tasks);
        when(taskRepository.findAll(any(PageRequest.class))).thenReturn(page);

        PageResponse pageResponse = taskManagementService.getAllTasks(1, 10);

        assertNotNull(pageResponse);

        verify(taskRepository, times(1)).findAll(any(PageRequest.class));
    }
}