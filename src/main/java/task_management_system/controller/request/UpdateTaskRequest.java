package task_management_system.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import task_management_system.enums.Priority;
import task_management_system.enums.TaskStatus;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class UpdateTaskRequest {

    @NotEmpty(message = "UserId cannot be empty.")
    private Integer userId;

    @NotEmpty(message = "Title cannot be empty.")
    private String title;

    @NotEmpty(message = "Description cannot be empty.")
    private String description;

    @NotEmpty(message = "Status cannot be empty.")
    private TaskStatus status;

    @NotEmpty(message = "Priority cannot be empty.")
    private Priority priority;

    @NotEmpty(message = "DueDate cannot be empty.")
    private LocalDate dueDate;
}
