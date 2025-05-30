package kz.zzhalelov.sharemate.server.booking.dto;

import kz.zzhalelov.sharemate.server.booking.Booking;
import kz.zzhalelov.sharemate.server.item.Item;
import kz.zzhalelov.sharemate.server.item.dto.ItemMapper;
import kz.zzhalelov.sharemate.server.user.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingMapper {
    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public Booking fromCreate(BookingCreateDto bookingCreateDto) {
        Item item = new Item();
        item.setId(bookingCreateDto.getItemId());
        Booking booking = new Booking();
        booking.setStart(bookingCreateDto.getStart());
        booking.setEnd(bookingCreateDto.getEnd());
        booking.setItem(item);
        return booking;
    }

    public BookingResponseDto toResponse(Booking booking) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setStart(booking.getStart());
        dto.setEnd(booking.getEnd());
        dto.setItem(itemMapper.toResponse(booking.getItem()));
        dto.setStatus(booking.getStatus());
        dto.setBooker(userMapper.toResponse(booking.getBooker()));
        return dto;
    }
}