package kz.zzhalelov.sharemate.gateway.item;

import jakarta.validation.Valid;
import kz.zzhalelov.sharemate.server.comment.dto.CommentCreateDto;
import kz.zzhalelov.sharemate.server.item.dto.ItemCreateDto;
import kz.zzhalelov.sharemate.server.item.dto.ItemUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findById(@PathVariable Long itemId,
                                           @RequestHeader("X-Sharer-User-Id") int userId) {
        return itemClient.findById(itemId, userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @Valid @RequestBody ItemCreateDto itemCreateDto) {
        return itemClient.create(userId, itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable long itemId,
                                         @Valid @RequestBody ItemUpdateDto itemUpdateDto) {
        return itemClient.update(userId, itemId, itemUpdateDto);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchByName(@RequestParam String text) {
        return itemClient.searchByText(text);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByOwner(@RequestHeader("X-Sharer-User-Id") long userId) {
        return itemClient.findAllByOwner(userId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable long itemId,
                                                @Valid @RequestBody CommentCreateDto commentCreateDto) {
        return itemClient.createComment(userId, itemId, commentCreateDto);
    }

    @DeleteMapping("/itemId")
    public ResponseEntity<Object> delete(@PathVariable long itemId) {
        return itemClient.delete(itemId);
    }
}