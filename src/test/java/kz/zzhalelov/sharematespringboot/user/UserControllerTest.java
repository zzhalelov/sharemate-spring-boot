package kz.zzhalelov.sharematespringboot.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.sharematespringboot.user.dto.UserCreateDto;
import kz.zzhalelov.sharematespringboot.user.dto.UserMapper;
import kz.zzhalelov.sharematespringboot.user.dto.UserUpdateDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest({UserController.class, UserMapper.class})
class UserControllerTest {
    @MockitoBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void createObjectGiven_shouldReturnCreated() {
        int assignableUserId = 1;

        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setName("abc");
        userCreateDto.setEmail("abc@gmail.com");

        Mockito
                .when(userService.create(Mockito.any(User.class)))
                .thenAnswer(invocationOnMock -> {
                    User argument = invocationOnMock.getArgument(0, User.class);
                    argument.setId(assignableUserId);
                    return argument;
                });

        String json = objectMapper.writeValueAsString(userCreateDto);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(assignableUserId))
                .andExpect(jsonPath("$.name").value(userCreateDto.getName()))
                .andExpect(jsonPath("$.email").value(userCreateDto.getEmail()));
    }

    @Test
    @SneakyThrows
    void update_shouldReturnOk() {
        int userId = 1;

        UserUpdateDto userUpdateDto = new UserUpdateDto();
        userUpdateDto.setName("updatedName");
        userUpdateDto.setEmail("oldEmail@gmail.com");

        User user = new User();
        user.setId(userId);
        user.setName("updatedName");
        user.setEmail("oldEmail@gmail.com");

        Mockito
                .when(userService.update(Mockito.anyInt(), Mockito.any(User.class)))
                .thenReturn(user);

        String json = objectMapper.writeValueAsString(userUpdateDto);

        mockMvc.perform(patch("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @SneakyThrows
    void findById_whenUserExists_shouldReturnOk() {
        int userId = 7;

        User user = new User();
        user.setId(userId);
        user.setName("Miss Erma Nicolas");
        user.setEmail("Marge_Beier5@yahoo.com");

        Mockito
                .when(userService.findById(Mockito.anyInt()))
                .thenReturn(user);

        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    @SneakyThrows
    void findAll_shouldReturnNonEmptyList() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("updateName");
        user1.setEmail("updateName@user.com");

        User user2 = new User();
        user2.setId(2);
        user2.setName("user");
        user2.setEmail("user@user.com");

        Mockito
                .when(userService.findAll())
                .thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(user1.getId()))
                .andExpect(jsonPath("$[0].name").value(user1.getName()))
                .andExpect(jsonPath("$[0].email").value(user1.getEmail()))
                .andExpect(jsonPath("$[1].id").value(user2.getId()))
                .andExpect(jsonPath("$[1].name").value(user2.getName()))
                .andExpect(jsonPath("$[1].email").value(user2.getEmail()));
    }

    @Test
    @SneakyThrows
    void deleteById_shouldReturnOk() {
        int userId = 1;

        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isOk());
    }
}