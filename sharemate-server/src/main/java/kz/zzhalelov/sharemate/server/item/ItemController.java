package kz.zzhalelov.sharemate.server.item;

import jakarta.validation.Valid;
import kz.zzhalelov.sharemate.server.comment.Comment;
import kz.zzhalelov.sharemate.server.comment.CommentService;
import kz.zzhalelov.sharemate.server.comment.dto.CommentCreateDto;
import kz.zzhalelov.sharemate.server.comment.dto.CommentMapper;
import kz.zzhalelov.sharemate.server.comment.dto.CommentResponseDto;
import kz.zzhalelov.sharemate.server.item.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {
    private final ItemMapper itemMapper;
    private final ItemService itemService;
    private final CommentMapper commentMapper;
    private final CommentService commentService;
    private static final String HEADER = "X-Sharer-User-Id";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponseDto create(@RequestBody @Valid ItemCreateDto itemCreate,
                                  @RequestHeader(HEADER) int userId) {
        Item item = itemMapper.fromCreate(itemCreate);
        Integer requestId = itemCreate.getRequestId();
        return itemMapper.toResponse(itemService.create(item, userId, requestId));
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto update(@PathVariable int itemId,
                                  @RequestBody @Valid ItemUpdateDto itemUpdate,
                                  @RequestHeader(HEADER) int userId) {
        Item item = itemMapper.fromUpdate(itemUpdate);
        return itemMapper.toResponse(itemService.update(itemId, item, userId));
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemFullResponseDto findById(@PathVariable int itemId,
                                        @RequestHeader(HEADER) int userId) {
        return itemMapper.toFullResponse(itemService.findById(itemId, userId));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<ItemResponseDto> findByName(@RequestParam String text) {
        return itemService.findByText(text)
                .stream()
                .map(itemMapper::toResponse)
                .toList();
    }

    @GetMapping
    public List<ItemResponseDto> findAllByOwner(@RequestHeader(HEADER) int userId) {
        return itemService.findAllByOwner(userId)
                .stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{itemId}")
    public void deleteById(@PathVariable int itemId) {
        itemService.deleteById(itemId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentResponseDto create(@PathVariable int itemId,
                                     @RequestBody CommentCreateDto createDto,
                                     @RequestHeader(HEADER) int userId) {
        Comment comment = commentMapper.fromCreate(createDto);
        return commentMapper.toResponse(commentService.create(itemId, userId, comment));
    }
}