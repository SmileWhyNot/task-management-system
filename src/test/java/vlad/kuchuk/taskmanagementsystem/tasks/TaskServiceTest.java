package vlad.kuchuk.taskmanagementsystem.tasks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskDto;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskMapper;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.requests.AssignTaskPerformerRequest;
import vlad.kuchuk.taskmanagementsystem.tasks.entity.Task;
import vlad.kuchuk.taskmanagementsystem.tasks.repository.TaskRepository;
import vlad.kuchuk.taskmanagementsystem.tasks.service.TaskService;
import vlad.kuchuk.taskmanagementsystem.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetTask() {
        Long taskId = 1L;
        Task task = new Task();
        TaskDto taskDto = new TaskDto();

        Mockito.when(taskRepository.findById(taskId))
               .thenReturn(Optional.of(task));
        Mockito.when(taskMapper.toDto(task))
               .thenReturn(taskDto);

        TaskDto result = taskService.getTask(taskId);

        assertNotNull(result);
    }


    @Test
    void testUpdateTask() {
        Long taskId = 1L;
        TaskDto updatedTaskDto = new TaskDto();
        Task task = new Task();
        TaskDto existingTaskDto = new TaskDto();

        Mockito.when(taskRepository.findById(taskId))
               .thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(task))
               .thenReturn(task);
        Mockito.when(taskMapper.partialUpdate(updatedTaskDto, task))
               .thenReturn(task);
        Mockito.when(taskMapper.toDto(task))
               .thenReturn(existingTaskDto);

        TaskDto result = taskService.updateTask(taskId, updatedTaskDto);

        assertNotNull(result);
    }


    @Test
    void testAssignTaskPerformer() {
        Long taskId = 1L;
        AssignTaskPerformerRequest assigneeId = new AssignTaskPerformerRequest();
        Task task = new Task();
        TaskDto existingTaskDto = new TaskDto();

        Mockito.when(userRepository.findById(any()))
               .thenReturn(Optional.of(new UserEntity()));
        Mockito.when(taskRepository.findById(taskId))
               .thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(task))
               .thenReturn(task);
        Mockito.when(taskMapper.toDto(task))
               .thenReturn(existingTaskDto);

        TaskDto result = taskService.assignTaskPerformer(taskId, assigneeId);

        assertNotNull(result);
    }

    @Test
    void testDeleteTask() {
        Long taskId = 1L;

        Mockito.doNothing()
               .when(taskRepository)
               .deleteById(taskId);

        taskService.deleteTask(taskId);

        Mockito.verify(taskRepository, Mockito.times(1))
               .deleteById(taskId);
    }
}
