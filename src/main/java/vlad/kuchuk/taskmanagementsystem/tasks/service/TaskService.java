package vlad.kuchuk.taskmanagementsystem.tasks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskDto;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskMapper;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.AssignTaskPerformerRequest;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.StatusUpdateRequest;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskNotFoundException;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskOperationException;
import vlad.kuchuk.taskmanagementsystem.tasks.repository.TaskRepository;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserMapper;
import vlad.kuchuk.taskmanagementsystem.user.exception.NoSuchUserException;
import vlad.kuchuk.taskmanagementsystem.user.repository.UserRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TaskService {

    private static final String TASK_NOT_FOUND = "Task with id = %d does not exist";
    private static final String USER_NOT_FOUND = "User with id = %d does not exist";

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final UserMapper userMapper;

    public TaskDto getTask(Long taskId) {
        return taskRepository.findById(taskId)
                             .map(taskMapper::toDto)
                             .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND, taskId)));
    }

    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        return Stream.of(taskDto)
                     .map(this::getAuthorIfExists)
                     .map(this::getAssigneeIfExistsOrNull)
                     .map(taskMapper::toEntity)
                     .map(taskRepository::save)
                     .map(taskMapper::toDto)
                     .findFirst()
                     .orElseThrow(() -> new TaskOperationException("Failed to create task"));
    }

    private TaskDto getAuthorIfExists(TaskDto task) {
        return userRepository.findById(task.getAuthor()
                                           .getId())
                             .map(userMapper::toSmallDto)
                             .map(task::setAuthor)
                             .orElseThrow(() -> new NoSuchUserException(String.format(USER_NOT_FOUND,
                                               task.getAuthor()
                                                                                                                    .getId())));
    }

    private TaskDto getAssigneeIfExistsOrNull(TaskDto task) {
        if (Objects.isNull(task.getAssignee())) return task;

        return userRepository.findById(task.getAssignee()
                                           .getId())
                             .map(userMapper::toSmallDto)
                             .map(task::setAssignee)
                             .orElseThrow(() -> new NoSuchUserException(String.format(USER_NOT_FOUND,
                                               task.getAssignee()
                                                                                                                    .getId())));
    }

    @Transactional
    public TaskDto updateTask(Long taskId, TaskDto updatedTask) {
        return taskRepository.findById(taskId)
                             .map(task -> taskMapper.partialUpdate(updatedTask, task))
                             .map(taskRepository::save)
                             .map(taskMapper::toDto)
                             .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND, taskId)));
    }

    @Transactional
    public TaskDto updateTaskStatus(Long taskId, StatusUpdateRequest newStatus) {
        return taskRepository.findById(taskId)
                             .map(this::getTaskIfCurUserIsAssignee)
                             .map(task -> {
                                 task.setStatus(newStatus.getStatus());
                                 return task;
                             })
                             .map(taskRepository::save)
                             .map(taskMapper::toDto)
                             .orElseThrow(() -> new TaskNotFoundException(String.format(TASK_NOT_FOUND, taskId)));
    }

    private Task getTaskIfCurUserIsAssignee(Task task) {
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        String email = authentication.getName();

        return Optional.of(task)
                       .filter(t -> t.getAssignee() != null)
                       .flatMap(t -> userRepository.findByEmail(email))
                       .filter(user -> Objects.equals(user.getId(), task.getAssignee()
                                                                        .getId()))
                       .map(user -> task)
                       .orElseThrow(() -> new TaskOperationException("You can't change status! Task assigned to " +
                               "another user"));
    }

    @Transactional
    public TaskDto assignTaskPerformer(Long taskId, AssignTaskPerformerRequest assigneeId) {
        Optional<UserEntity> assignee = userRepository.findById(assigneeId.getAssigneeId());
        if (assignee.isPresent()) {
            return taskRepository.findById(taskId)
                                 .filter(task -> Objects.isNull(task.getAssignee()))
                                 .map(task -> {
                                     task.setAssignee(assignee.get());
                                     return task;
                                 })
                                 .map(taskRepository::save)
                                 .map(taskMapper::toDto)
                                 .orElseThrow(() -> new TaskOperationException("Failed to assign task: already " +
                                         "assigned or no such task exist"));
        } else throw new NoSuchUserException(String.format(USER_NOT_FOUND, assigneeId.getAssigneeId()));
    }

    @Transactional
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
