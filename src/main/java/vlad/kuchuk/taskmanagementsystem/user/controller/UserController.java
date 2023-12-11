package vlad.kuchuk.taskmanagementsystem.user.controller;

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
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/assigned-tasks")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Page<TaskDto> getAssignedTasks(@PathVariable Long userId,
                                          @ModelAttribute @Valid FilteredPageableTasksRequest request) {
        return userService.getTasksByAssignee(userId, request);
    }

    @GetMapping("/{userId}/authored-tasks")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Page<TaskDto> getTasksByAuthor(@PathVariable Long userId,
                                          @ModelAttribute FilteredPageableTasksRequest request) {
        return userService.getTasksByAuthor(userId, request);
    }
}
