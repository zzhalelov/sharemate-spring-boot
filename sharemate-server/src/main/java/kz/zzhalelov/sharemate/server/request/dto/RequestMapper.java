package kz.zzhalelov.sharemate.server.request.dto;

import kz.zzhalelov.sharemate.server.request.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RequestMapper {

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
        return dto;
    }
}