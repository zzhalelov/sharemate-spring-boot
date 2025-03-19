package kz.zzhalelov.sharematespringboot.item;

import jakarta.validation.Valid;
import kz.zzhalelov.sharematespringboot.comment.Comment;
import kz.zzhalelov.sharematespringboot.comment.CommentService;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentCreateDto;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentMapper;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentResponseDto;
import kz.zzhalelov.sharematespringboot.item.dto.*;
import lombok.RequiredArgsConstructor;
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
    public ItemResponseDto create(@RequestBody @Valid ItemCreateDto itemCreate,
                                  @RequestHeader(HEADER) int userId) {
        Item item = itemMapper.fromCreate(itemCreate);
        return itemMapper.toResponse(itemService.create(item, userId));
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto update(@PathVariable int itemId,
                                  @RequestBody @Valid ItemUpdateDto itemUpdate,
                                  @RequestHeader(HEADER) int userId) {
        Item item = itemMapper.fromUpdate(itemUpdate);
        return itemMapper.toResponse(itemService.update(itemId, item, userId));
    }

    @GetMapping("/{itemId}")
    public ItemFullResponseDto findById(@PathVariable int itemId) {
        return itemMapper.toFullResponse(itemService.findById(itemId));
    }

    @GetMapping("/search")
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