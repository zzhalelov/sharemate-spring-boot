package kz.zzhalelov.sharemate.server.request.dto;

import kz.zzhalelov.sharemate.server.item.dto.ItemResponseDto;
import kz.zzhalelov.sharemate.server.user.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestResponseDto {
    private int id;
    private User requester;
    private String description;
    private LocalDateTime created;
    private List<ItemResponseDto> items;
}