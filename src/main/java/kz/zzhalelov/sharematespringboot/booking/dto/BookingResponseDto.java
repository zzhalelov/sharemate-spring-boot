package kz.zzhalelov.sharematespringboot.booking.dto;

import kz.zzhalelov.sharematespringboot.booking.BookingStatus;
import kz.zzhalelov.sharematespringboot.item.dto.ItemResponseDto;
import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.dto.UserResponseDto;
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