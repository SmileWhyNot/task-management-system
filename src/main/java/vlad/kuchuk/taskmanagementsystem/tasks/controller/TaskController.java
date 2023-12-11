package vlad.kuchuk.taskmanagementsystem.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskDto;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.AssignTaskPerformerRequest;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.StatusUpdateRequest;
import vlad.kuchuk.taskmanagementsystem.tasks.service.TaskService;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "Tasks API",
        description = "Tasks API contains methods for creating, updating, deleting and getting tasks"
)
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Get Task",
            description = "Returns a specific task by ID"
    )
    public TaskDto getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    @Operation(
            summary = "Create Task",
            description = "Creates a new task from the provided data"
    )
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Update Task",
            description = """
                          Updates a specific task by ID partially, retrieves only the fields need to be updated!
                          Next fields are ignored:\s
                          1) author - can't be reassigned after creation\s
                          2) assignee - can be only assigned if haven't yet at another endpoint\s
                          3) status - can be changed only by assignee at another endpoint\s
                          4) comments - can't be changed at all\s
                          """
    )
    public TaskDto updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    @PatchMapping("/{taskId}/status")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Update Task Status",
            description = "Updates task status. Status could be updated only by assignee"
    )
    public TaskDto updateTaskStatus(@PathVariable Long taskId, @Valid @RequestBody StatusUpdateRequest newStatus) {
        return taskService.updateTaskStatus(taskId, newStatus);
    }

    @PatchMapping("/{taskId}/assignee")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Assign Task",
            description = "Assigns task to a specific user only if it wasn't assigned yet and task exists"
    )
    public TaskDto assignTaskPerformer(@PathVariable Long taskId,
                                       @Valid @RequestBody AssignTaskPerformerRequest assigneeId) {
        return taskService.assignTaskPerformer(taskId, assigneeId);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Delete Task",
            description = "Deletes a specific task by ID"
    )
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
