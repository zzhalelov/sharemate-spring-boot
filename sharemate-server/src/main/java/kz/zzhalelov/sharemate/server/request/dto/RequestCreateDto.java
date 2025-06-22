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
public class RequestCreateDto {
    private String description;
}