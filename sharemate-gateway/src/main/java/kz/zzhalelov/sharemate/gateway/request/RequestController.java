package kz.zzhalelov.sharemate.gateway.request;

import jakarta.validation.Valid;
import kz.zzhalelov.sharemate.server.request.dto.RequestCreateDto;
import kz.zzhalelov.sharemate.server.request.dto.RequestUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {
    private final RequestClient requestClient;

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findById(@PathVariable Long requestId,
                                           @RequestHeader("X-Sharer-User-Id") int requesterId) {
        return requestClient.findById(requestId, requesterId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                         @Valid @RequestBody RequestCreateDto requestCreateDto) {
        return requestClient.create(requesterId, requestCreateDto);
    }

    @PatchMapping("/{requestId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                         @PathVariable long requestId,
                                         @Valid @RequestBody RequestUpdateDto requestUpdateDto) {
        return requestClient.update(requesterId, requestId, requestUpdateDto);
    }

    @GetMapping
    public ResponseEntity<Object> findAllMyRequests(@RequestHeader("X-Sharer-User-Id") long requesterId) {
        return requestClient.findAllMyRequests(requesterId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllRequestsExceptMine(@RequestHeader("X-Sharer-User-Id") long requesterId,
                                                            @RequestParam(defaultValue = "0") int from,
                                                            @RequestParam(defaultValue = "20") int size) {
        return requestClient.findAllRequestsExceptMine(requesterId, from, size);
    }
}