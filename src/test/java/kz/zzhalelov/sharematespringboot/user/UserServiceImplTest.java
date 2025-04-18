package kz.zzhalelov.sharematespringboot.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void create_shouldCreate() {
        User user = new User();
        user.setId(1);
        user.setName("abc");
        user.setEmail("abc@gmail.com");

        Mockito
                .when(userRepository.save(Mockito.any(User.class)))
                .thenReturn(user);

        User savedUser = userService.create(user);

        assertEquals(user.getId(), savedUser.getId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void update_UpdateName() {
        int userId = 1;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("abc");
        existingUser.setEmail("abc@gmail.com");

        User updatedUser = new User();
        updatedUser.setName("abcde");

        Mockito
                .when(userRepository.findById(userId))
                .thenReturn(Optional.of(existingUser));

        Mockito
                .when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocationOnMock -> {
                    User argument = invocationOnMock.getArgument(0, User.class);
                    argument.setId(userId);
                    return argument;
                });

        User user = userService.update(userId, updatedUser);

        assertEquals("abcde", user.getName());
        assertEquals("abc@gmail.com", user.getEmail());
    }

    @Test
    void update_UpdateEmail() {
        int userId = 1;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("abc");
        existingUser.setEmail("abc@gmail.com");

        User updatedUser = new User();
        updatedUser.setEmail("abcde@gmail.com");

        Mockito
                .when(userRepository.findById(userId))
                .thenReturn(Optional.of(existingUser));

        Mockito
                .when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocationOnMock -> {
                    User argument = invocationOnMock.getArgument(0, User.class);
                    argument.setId(userId);
                    return argument;
                });

        User user = userService.update(userId, updatedUser);

        assertEquals("abc", user.getName());
        assertEquals("abcde@gmail.com", user.getEmail());
    }

    @Test
    void findById_whenUserExists_shouldReturnUser() {
        int userId = 1;

        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("updateName");
        existingUser.setEmail("updateName@user.com");

        Mockito
                .when(userRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(existingUser));

        User user = userService.findById(userId);
        assertEquals(userId, user.getId());
        assertEquals(existingUser.getName(), user.getName());
        assertEquals(existingUser.getEmail(), user.getEmail());
    }

    @Test
    void findAll_shouldReturnList() {
        User existingUser1 = new User();
        existingUser1.setId(1);
        existingUser1.setName("updateName");
        existingUser1.setEmail("updateName@user.com");

        User existingUser2 = new User();
        existingUser2.setId(3);
        existingUser2.setName("user");
        existingUser2.setEmail("user@user.com");

        List<User> existingUsers = List.of(existingUser1, existingUser2);

        Mockito
                .when(userRepository.findAll())
                .thenReturn(existingUsers);

        List<User> users = userService.findAll();
        assertEquals(2, users.size());
        assertEquals(existingUser1.getName(), users.get(0).getName());
        assertEquals(existingUser1.getEmail(), users.get(0).getEmail());
        assertEquals(existingUser2.getName(), users.get(1).getName());
        assertEquals(existingUser2.getEmail(), users.get(1).getEmail());
    }
}