package kz.zzhalelov.sharemate.server.request.dto;

import kz.zzhalelov.sharemate.server.item.Item;
import kz.zzhalelov.sharemate.server.item.dto.ItemMapper;
import kz.zzhalelov.sharemate.server.item.dto.ItemResponseDto;
import kz.zzhalelov.sharemate.server.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RequestMapper {
    private final ItemMapper itemMapper;

    public Request fromCreate(RequestCreateDto requestCreateDto) {
        Request request = new Request();
        request.setDescription(requestCreateDto.getDescription());
        request.setCreated(LocalDateTime.now());
        return request;
    }

    public RequestResponseDto toResponse(Request request) {
        RequestResponseDto dto = new RequestResponseDto();
        dto.setId(request.getId());
        dto.setDescription(request.getDescription());
        dto.setCreated(request.getCreated());
        //TODO: если item null то ниже код не делается
        List<ItemResponseDto> items = new ArrayList<>();
        if (request.getItems() != null) {
            items = request.getItems()
                    .stream()
                    .map(itemMapper::toResponse)
                    .toList();
        }
        dto.setItems(items);
        return dto;
    }

    private ItemResponseDto toResponse(Item item) {
        ItemResponseDto dto = new ItemResponseDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        if (item.getRequest() != null) {
            dto.setRequestId(item.getRequest().getId());
        }
        return dto;
    }
}