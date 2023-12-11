package vlad.kuchuk.taskmanagementsystem.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CommentDto;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CommentMapper;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CreateCommentRequest;
import vlad.kuchuk.taskmanagementsystem.comments.entity.Comment;
import vlad.kuchuk.taskmanagementsystem.comments.repository.CommentsRepository;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;
import vlad.kuchuk.taskmanagementsystem.tasks.exception.TaskNotFoundException;
import vlad.kuchuk.taskmanagementsystem.comments.exception.CreateCommentException;
import vlad.kuchuk.taskmanagementsystem.tasks.repository.TaskRepository;
import vlad.kuchuk.taskmanagementsystem.user.exception.NoSuchUserException;
import vlad.kuchuk.taskmanagementsystem.user.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public Set<CommentDto> getComments(Long taskId) {
        List<Comment> comments = commentsRepository.getCommentsByTaskId(taskId);
        return commentMapper.toDtoSet(comments);
    }

    @Transactional
    public CommentDto createComment(CreateCommentRequest commentDto) {
        return Stream.of(commentDto)
                     .map(commentMapper::toEntity)
                     .map(comment -> setUpCreateCommentDto(comment, commentDto))
                     .map(commentsRepository::save)
                     .map(commentMapper::toDto)
                     .findFirst()
                     .orElseThrow(() -> new CreateCommentException("Failed to create comment"));

    }

    private Comment setUpCreateCommentDto(Comment comment, CreateCommentRequest request) {
        Task task = taskRepository.findById(request.getTaskId())
                                  .orElseThrow(() -> new TaskNotFoundException(
                                          String.format("Task with id %d not " + "found", request.getTaskId())));
        UserEntity author = userRepository.findById(request.getUserId())
                                          .orElseThrow(() -> new NoSuchUserException(
                                                  String.format("User with id %d " + "not found",
                                                                request.getUserId())));
        comment.setAuthor(author);
        comment.setTask(task);
        return comment;
    }
}
