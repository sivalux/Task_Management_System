package task_management_system.service;

import task_management_system.controller.request.CreateTaskRequest;
import task_management_system.controller.request.UpdateTaskRequest;
import task_management_system.controller.response.TaskResponse;
import task_management_system.util.PageResponse;

public interface TaskManagementService {

    /**
     * service method for create task
     * @return task Id
     */
    Integer createTask(final CreateTaskRequest request);

    /**
     * service method for delete task
     * @param id
     * @return id of deleted task
     */
    Integer deleteTask(final Integer id);

    /**
     * service method for update task
     * @param id
     * @param request
     * @return id of updated task
     */
    Integer updateTask(final Integer id, final UpdateTaskRequest request);

    /**
     * service method for get task by id
     * @param id
     * @return task response
     */
    TaskResponse getTaskById(final Integer id);

    /**
     * service method for get all tasks
     * @param pageNo
     * @param pageSize
     * @return page response
     */
    PageResponse getAllTasks(final Integer pageNo, final Integer pageSize);
}
