package kz.zzhalelov.sharemate.server.comment.dto;

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