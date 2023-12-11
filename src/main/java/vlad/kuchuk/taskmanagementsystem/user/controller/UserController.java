package vlad.kuchuk.taskmanagementsystem.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskDto;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.FilteredPageableTasksRequest;
import vlad.kuchuk.taskmanagementsystem.user.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
@Tag(
        name = "User API",
        description = """
                      User API works with users' tasks, providing a list of tasks assigned to the user\s
                      OR a list of tasks created by the user\s
                      """
)
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/assigned-tasks")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Get Assigned Tasks",
            description = "Retrieve a list of tasks assigned to a specific user pageable."
    )
    public Page<TaskDto> getAssignedTasks(@PathVariable Long userId,
                                          @ModelAttribute @Valid FilteredPageableTasksRequest request) {
        return userService.getTasksByAssignee(userId, request);
    }

    @GetMapping("/{userId}/authored-tasks")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    @Operation(
            summary = "Get Authored Tasks",
            description = "Retrieve a list of tasks created by a specific user pageable."
    )
    public Page<TaskDto> getTasksByAuthor(@PathVariable Long userId,
                                          @ModelAttribute FilteredPageableTasksRequest request) {
        return userService.getTasksByAuthor(userId, request);
    }
}
