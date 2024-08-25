package task_management_system.controller.response;

import lombok.Getter;
import lombok.Setter;
import task_management_system.entity.Task;

import java.time.LocalDate;

@Setter
@Getter
public class TaskResponse {

    private Integer id;

    private Integer userId;

    private String title;

    private String description;

    private String status;

    private String priority;

    private LocalDate createDate;

    private LocalDate dueDate;

    public TaskResponse(Task task){
        setId(task.getId());
        setUserId(task.getUserId().getId());
        setTitle(task.getTitle());
        setDescription(task.getDescription());
        setStatus(task.getStatus().name());
        setPriority(task.getPriority().name());
        setCreateDate(task.getCreateDate());
        setDueDate(task.getDueDate());
    }


}
