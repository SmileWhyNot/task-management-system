package vlad.kuchuk.taskmanagementsystem.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;
import vlad.kuchuk.taskmanagementsystem.tasks.dto.TaskMapper;
import vlad.kuchuk.taskmanagementsystem.tasks.repository.TaskRepository;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserDto;
import vlad.kuchuk.taskmanagementsystem.user.dto.UserMapper;
import vlad.kuchuk.taskmanagementsystem.user.exception.NoSuchUserException;
import vlad.kuchuk.taskmanagementsystem.user.repository.UserRepository;
import vlad.kuchuk.taskmanagementsystem.user.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetById_UserExists_ReturnsUserDto() {
        Long userId = 1L;
        UserEntity userEntity = new UserEntity();
        UserDto expectedUserDto = new UserDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userService.getById(userId);

        assertNotNull(actualUserDto);
        assertSame(expectedUserDto, actualUserDto);
    }

    @Test
    void testGetById_UserNotFound_ThrowsNoSuchUserException() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchUserException.class, () -> userService.getById(userId));
    }

    @Test
    void testSave_ValidUserDto_ReturnsSavedUserDto() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();

        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);

        UserDto savedUserDto = userService.save(userDto);

        assertNotNull(savedUserDto);
        assertSame(userDto, savedUserDto);
    }
}
