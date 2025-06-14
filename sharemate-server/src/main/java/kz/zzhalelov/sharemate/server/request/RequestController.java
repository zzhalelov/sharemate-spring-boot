package kz.zzhalelov.sharemate.server.request;

import jakarta.validation.Valid;
import kz.zzhalelov.sharemate.server.item.ItemService;
import kz.zzhalelov.sharemate.server.request.dto.RequestCreateDto;
import kz.zzhalelov.sharemate.server.request.dto.RequestMapper;
import kz.zzhalelov.sharemate.server.request.dto.RequestResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class RequestController {
    private final ItemService itemService;
    private final RequestService requestService;
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RequestResponseDto createRequest(@RequestBody @Valid RequestCreateDto requestCreateDto,
                                            @RequestHeader(HEADER) int requesterId) {
        Request request = requestMapper.fromCreate(requestCreateDto);
        return requestMapper.toResponse(requestService.create(requesterId, request));
    }

    @GetMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public RequestResponseDto findById(@PathVariable int requestId,
                                       @RequestHeader(HEADER) int requesterId) {
        return requestMapper.toResponse(requestService.findById(requesterId, requestId));
    }

    @GetMapping
    public List<RequestResponseDto> findAllMyRequests(@RequestHeader(HEADER) int requesterId) {
        return requestService.findAllMyRequests(requesterId)
                .stream()
                .map(requestMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/all")
    public List<RequestResponseDto> findAllRequestsExceptMine(@RequestHeader(HEADER) int requesterId) {
        return requestService.findAllRequestsExceptMine(requesterId)
                .stream()
                .map(requestMapper::toResponse)
                .collect(Collectors.toList());
    }
}