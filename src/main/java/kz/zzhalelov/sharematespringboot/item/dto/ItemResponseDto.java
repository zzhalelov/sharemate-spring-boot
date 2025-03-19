package kz.zzhalelov.sharematespringboot.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemResponseDto {
    int id;
    String name;
    String description;
    Boolean available;
}