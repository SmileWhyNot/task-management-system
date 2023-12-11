package vlad.kuchuk.taskmanagementsystem.tasks.controller;

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
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    public TaskDto getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @PostMapping
    @ResponseStatus(value = org.springframework.http.HttpStatus.CREATED)
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    public TaskDto updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    @PatchMapping("/{taskId}/status")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    public TaskDto updateTaskStatus(@PathVariable Long taskId, @Valid @RequestBody StatusUpdateRequest newStatus) {
        return taskService.updateTaskStatus(taskId, newStatus);
    }

    @PatchMapping("/{taskId}/assignee")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    public TaskDto assignTaskPerformer(@PathVariable Long taskId,
                                       @Valid @RequestBody AssignTaskPerformerRequest assigneeId) {
        return taskService.assignTaskPerformer(taskId, assigneeId);
    }

    @DeleteMapping("/{taskId}")
    @ResponseStatus(value = org.springframework.http.HttpStatus.OK)
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
