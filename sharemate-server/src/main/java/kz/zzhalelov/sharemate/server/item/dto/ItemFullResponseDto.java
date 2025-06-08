package kz.zzhalelov.sharemate.server.item.dto;

import kz.zzhalelov.sharemate.server.comment.dto.CommentResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemFullResponseDto {
    int id;
    String name;
    String description;
    Boolean available;
    List<CommentResponseDto> comments;
    BookingResponseDto lastBooking;
    BookingResponseDto nextBooking;
}