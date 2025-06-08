package kz.zzhalelov.sharemate.server.item.dto;

import kz.zzhalelov.sharemate.server.booking.Booking;
import kz.zzhalelov.sharemate.server.comment.dto.CommentMapper;
import kz.zzhalelov.sharemate.server.comment.dto.CommentResponseDto;
import kz.zzhalelov.sharemate.server.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static kz.zzhalelov.sharemate.server.item.dto.ItemFullResponseDto.*;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    private final CommentMapper commentMapper;

    public Item fromCreate(ItemCreateDto itemCreateDto) {
        Item item = new Item();
        item.setName(itemCreateDto.getName());
        item.setDescription(itemCreateDto.getDescription());
        item.setAvailable(itemCreateDto.getAvailable());

        return item;
    }

    public Item fromUpdate(ItemUpdateDto itemUpdateDto) {
        Item item = new Item();
        item.setName(itemUpdateDto.getName());
        item.setDescription(itemUpdateDto.getDescription());
        item.setAvailable(itemUpdateDto.getAvailable());
        return item;
    }

    public ItemResponseDto toResponse(Item item) {
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        dto.setLastBooking(toResponse(item.getLastBooking()));
        dto.setNextBooking(toResponse(item.getNextBooking()));
        return dto;
    }

    public ItemFullResponseDto toFullResponse(Item item) {
        ItemFullResponseDto dto = new ItemFullResponseDto();
        List<CommentResponseDto> comments = item.getComments()
                .stream()
                .map(commentMapper::toResponse)
                .toList();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        dto.setLastBooking(toResponse(item.getLastBooking()));
        dto.setNextBooking(toResponse(item.getNextBooking()));

        dto.setComments(comments);
        return dto;
    }

    public void merge(Item existingItem, Item updatedItem) {
        if (updatedItem.getName() != null && !updatedItem.getName().isBlank()) {
            existingItem.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null && !updatedItem.getDescription().isBlank()) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getAvailable() != null) {
            existingItem.setAvailable(updatedItem.getAvailable());
        }
    }

    //конвертация Booking в BookingResponseDto
    public BookingResponseDto toResponse(Booking booking) {
        if (booking == null) {
            return null;
        }
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setBookerId(booking.getBooker().getId());
        return dto;
    }
}