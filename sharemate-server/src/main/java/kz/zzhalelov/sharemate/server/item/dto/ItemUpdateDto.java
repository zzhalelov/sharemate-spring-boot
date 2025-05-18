package kz.zzhalelov.sharemate.server.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemUpdateDto {
    String name;
    String description;
    Boolean available;
}