package kz.zzhalelov.sharematespringboot.user;

import jakarta.validation.Valid;
import kz.zzhalelov.sharematespringboot.user.dto.UserCreateDto;
import kz.zzhalelov.sharematespringboot.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

}