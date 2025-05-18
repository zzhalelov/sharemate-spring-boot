package kz.zzhalelov.sharemate.server.user.dto;

import kz.zzhalelov.sharemate.server.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User fromCreate(UserCreateDto userCreateDto) {
        User user = new User();
        user.setName(userCreateDto.getName());
        user.setEmail(userCreateDto.getEmail());
        return user;
    }

    public User fromUpdate(UserUpdateDto userUpdateDto) {
        User user = new User();
        user.setName(userUpdateDto.getName());
        user.setEmail(userUpdateDto.getEmail());
        return user;
    }

    public UserResponseDto toResponse(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }
}