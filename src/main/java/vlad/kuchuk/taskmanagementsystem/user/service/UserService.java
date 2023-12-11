package vlad.kuchuk.taskmanagementsystem.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskDto;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskMapper;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.FilteredPageableTasksRequest;
import vlad.kuchuk.taskmanagementsystem.tasks.repository.TaskRepository;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserDto;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserMapper;
import vlad.kuchuk.taskmanagementsystem.user.exception.NoSuchUserException;
import vlad.kuchuk.taskmanagementsystem.user.exception.UserOperationException;
import vlad.kuchuk.taskmanagementsystem.user.repository.UserRepository;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    public UserDto getById(Long userId) {
        return userRepository.findById(userId)
                             .map(userMapper::toDto)
                             .orElseThrow(() -> new NoSuchUserException(
                                     String.format("User with id = %d not found", userId)));
    }

    public Optional<UserDto> getOptionalByEmail(String email) {
        return userRepository.findByEmail(email)
                             .map(userMapper::toDto);
    }

    @Transactional
    public UserDto save(UserDto user) {
        return Stream.of(user)
                     .map(userMapper::toEntity)
                     .map(userRepository::save)
                     .map(userMapper::toDto)
                     .findFirst()
                     .orElseThrow(() -> new UserOperationException(
                             String.format("User with email = %s not saved", user.getEmail())));
    }

    public Page<TaskDto> getTasksByAssignee(Long assigneeId, FilteredPageableTasksRequest request) {
        return taskRepository.findByAuthorIdOrAssigneeId(null, assigneeId, request.toPageable())
                             .map(taskMapper::toDto);
    }

    public Page<TaskDto> getTasksByAuthor(Long authorId, FilteredPageableTasksRequest request) {
        return taskRepository.findByAuthorIdOrAssigneeId(authorId, null, request.toPageable())
                             .map(taskMapper::toDto);
    }
}
