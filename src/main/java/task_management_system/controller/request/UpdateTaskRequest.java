package task_management_system.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "UserId cannot be null.")
    private Integer userId;

    @NotEmpty(message = "Title cannot be empty.")
    private String title;

    @NotEmpty(message = "Description cannot be empty.")
    private String description;

    @NotNull(message = "Status cannot be null.")
    private TaskStatus status;

    @NotNull(message = "Priority cannot be null.")
    private Priority priority;

    @NotNull(message = "DueDate cannot be null.")
    private LocalDate dueDate;
}
