package kz.zzhalelov.sharematespringboot.comment.dto;

import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponseDto {
    int id;
    String text;
    String authorName;
    LocalDateTime created;
}