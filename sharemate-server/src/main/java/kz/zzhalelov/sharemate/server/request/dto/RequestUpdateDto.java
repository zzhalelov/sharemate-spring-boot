package kz.zzhalelov.sharemate.server.request.dto;

import kz.zzhalelov.sharemate.server.item.dto.ItemCreateDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestUpdateDto {
    private int id;
    private String description;
    private LocalDateTime created;
    private List<ItemCreateDto> items = new ArrayList<>();
}