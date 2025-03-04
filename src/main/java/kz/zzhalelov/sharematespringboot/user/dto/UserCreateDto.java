package kz.zzhalelov.sharematespringboot.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDto {
    @NotBlank(message = "Имя не может быть пустым")
    String name;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "")
    String email;

}
