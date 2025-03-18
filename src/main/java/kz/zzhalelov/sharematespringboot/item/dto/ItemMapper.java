package kz.zzhalelov.sharematespringboot.item.dto;

import kz.zzhalelov.sharematespringboot.comment.Comment;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentMapper;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentResponseDto;
import kz.zzhalelov.sharematespringboot.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return dto;
    }

    public ItemFullResponseDto fullResponseDto(Item item) {
        ItemFullResponseDto dto = new ItemFullResponseDto();
        List<CommentResponseDto> comments = item.getComments()
                .stream()
                .map(commentMapper::toResponse)
                .toList();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());

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
}