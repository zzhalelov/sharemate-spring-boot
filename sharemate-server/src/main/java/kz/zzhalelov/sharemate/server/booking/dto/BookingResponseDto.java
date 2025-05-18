package kz.zzhalelov.sharemate.server.booking.dto;

import kz.zzhalelov.sharemate.server.booking.BookingStatus;
import kz.zzhalelov.sharemate.server.item.dto.ItemResponseDto;
import kz.zzhalelov.sharemate.server.user.dto.UserResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingResponseDto {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    BookingStatus status;
    UserResponseDto booker;
    ItemResponseDto item;
}