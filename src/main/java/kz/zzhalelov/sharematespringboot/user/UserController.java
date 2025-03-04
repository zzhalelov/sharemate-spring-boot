package kz.zzhalelov.sharematespringboot.user;

import jakarta.validation.Valid;
import kz.zzhalelov.sharematespringboot.user.dto.UserCreateDto;
import kz.zzhalelov.sharematespringboot.user.dto.UserMapper;
import kz.zzhalelov.sharematespringboot.user.dto.UserResponseDto;
import kz.zzhalelov.sharematespringboot.user.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping
    public UserResponseDto create(@RequestBody @Valid UserCreateDto userCreate) {
        User user = userMapper.fromCreate(userCreate);
        return userMapper.toResponse(userService.create(user));
    }

    //PATCH /user/{id}
    @PatchMapping("/{userId}")
    public UserResponseDto update(@PathVariable int userId, @RequestBody @Valid UserUpdateDto userUpdate) {
        User user = userMapper.fromUpdate(userUpdate);
        return userMapper.toResponse(userService.update(userId, user));
    }

    //GET /user/{id}
    @GetMapping("/{userId}")
    public UserResponseDto findById(@PathVariable int userId) {
        return userMapper.toResponse(userService.findById(userId));
    }

    //DELETE /user/{id}
    @DeleteMapping("/{userId}")
    public void deleteById(@PathVariable int userId) {
        userService.delete(userId);
    }

    //GET /users
    @GetMapping
    public List<UserResponseDto> findAll() {
        return userService.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}