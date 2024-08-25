package task_management_system.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import task_management_system.controller.request.CreateTaskRequest;
import task_management_system.controller.request.UpdateTaskRequest;
import task_management_system.controller.response.TaskResponse;
import task_management_system.entity.Task;
import task_management_system.entity.User;
import task_management_system.enums.TaskStatus;
import task_management_system.repository.TaskRepository;
import task_management_system.repository.UserRepository;
import task_management_system.service.TaskManagementService;
import task_management_system.util.PageResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskManagementServiceImpl implements TaskManagementService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private final ModelMapper modelMapper;

    public TaskManagementServiceImpl() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT).setSkipNullEnabled(true);
    }

    /**
     * service method for create task
     * @return task Id
     */
    @Override
    public Integer createTask(CreateTaskRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        if(user.isEmpty()){
            throw new IllegalArgumentException("Not found any user for given userId : "+request.getUserId());
        }
        Task task = modelMapper.map(request,Task.class);
        task.setUserId(user.get());
        task.setStatus(TaskStatus.NEW);
        task.setCreateDate(LocalDate.now());
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task).getId();
    }

    /**
     * service method for delete task
     * @param id
     * @return id of deleted task
     */
    @Override
    public Integer deleteTask(Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new IllegalArgumentException("Not found any task for given taskId : "+id);
        }
        taskRepository.delete(task.get());
        return id;
    }

    /**
     * service method for update task
     * @param id
     * @param request
     * @return id of updated task
     */
    @Override
    public Integer updateTask(Integer id, UpdateTaskRequest request) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new IllegalArgumentException("Not found any task for given taskId : "+id);
        }
        Optional<User> user = userRepository.findById(request.getUserId());
        if(user.isEmpty()){
            throw new IllegalArgumentException("Not found any user for given userId : "+request.getUserId());
        }
        modelMapper.map(request, task.get());
        task.get().setUserId(user.get());
        task.get().setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task.get()).getId();
    }

    /**
     * service method for get task by id
     * @param id
     * @return task response
     */
    @Override
    public TaskResponse getTaskById(Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        if(task.isEmpty()){
            throw new IllegalArgumentException("Not found any task for given taskId : "+id);
        }
        return new TaskResponse(task.get());
    }

    /**
     * service method for get all tasks
     * @param pageNo
     * @param pageSize
     * @return page response
     */
    @Override
    public PageResponse getAllTasks(Integer pageNo, Integer pageSize) {
        Page<Task> taskResponsePage = taskRepository.findAll(PageRequest.of(pageNo-1,pageSize));
        List<TaskResponse> taskList = taskResponsePage.getContent().stream()
                .map(TaskResponse::new)
                .collect(Collectors.toList());
        return new PageResponse(taskList,taskResponsePage);
    }


}
