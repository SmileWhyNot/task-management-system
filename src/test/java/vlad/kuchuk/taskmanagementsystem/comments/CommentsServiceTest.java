package vlad.kuchuk.taskmanagementsystem.comments;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CommentDto;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CommentMapper;
import vlad.kuchuk.taskmanagementsystem.comments.dto.CreateCommentRequest;
import vlad.kuchuk.taskmanagementsystem.comments.entity.Comment;
import vlad.kuchuk.taskmanagementsystem.comments.repository.CommentsRepository;
import vlad.kuchuk.taskmanagementsystem.comments.service.CommentsService;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Priority;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Status;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;
import vlad.kuchuk.taskmanagementsystem.tasks.repository.TaskRepository;
import vlad.kuchuk.taskmanagementsystem.user.repository.UserRepository;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
class CommentsServiceTest {

    @Mock
    private CommentsRepository commentsRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private CommentsService commentsService;

    @Test
    void testGetComments() {
        UserEntity user = UserEntity.builder()
                                    .id(1L)
                                    .email("email@email.ru")
                                    .password("12345")
                                    .build();
        Task task = new Task(1L, "task", "desc", Status.PENDING, Priority.HIGH, Collections.emptySet(), user, user);
        Comment comment1 = new Comment(1L, "comment1", ZonedDateTime.now(), task, user);
        Comment comment2 = new Comment(2L, "comment2", ZonedDateTime.now(), task, user);
        List<Comment> comments = Arrays.asList(comment1, comment2);

        Mockito.when(commentsRepository.getCommentsByTaskId(task.getId()))
               .thenReturn(comments);
        Mockito.when(commentMapper.toDtoSet(comments))
               .thenReturn(new HashSet<>());
        Set<CommentDto> commentDtos = commentsService.getComments(task.getId());

        assertNotNull(commentDtos);
    }


    @Test
    void testCreateComment() {
        CreateCommentRequest commentDto = new CreateCommentRequest();
        Comment comment = new Comment();
        UserEntity user = new UserEntity();
        Task task = new Task();

        Mockito.when(commentMapper.toEntity(commentDto))
               .thenReturn(comment);
        Mockito.when(userRepository.findById(commentDto.getUserId()))
               .thenReturn(Optional.of(user));
        Mockito.when(taskRepository.findById(commentDto.getTaskId()))
               .thenReturn(Optional.of(task));
        Mockito.when(commentsRepository.save(comment))
               .thenReturn(comment);
        Mockito.when(commentMapper.toDto(comment))
               .thenReturn(new CommentDto());

        CommentDto createdComment = commentsService.createComment(commentDto);

        assertNotNull(createdComment);
    }
}
