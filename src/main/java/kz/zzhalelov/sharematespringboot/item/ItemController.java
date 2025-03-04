package kz.zzhalelov.sharematespringboot.item;

import jakarta.validation.Valid;
import kz.zzhalelov.sharematespringboot.item.dto.ItemCreateDto;
import kz.zzhalelov.sharematespringboot.item.dto.ItemMapper;
import kz.zzhalelov.sharematespringboot.item.dto.ItemResponseDto;
import kz.zzhalelov.sharematespringboot.item.dto.ItemUpdateDto;
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

    @PostMapping
    public ItemResponseDto create(@RequestBody @Valid ItemCreateDto itemCreate,
                                  @RequestHeader("X-Sharer-User-Id") int userId) {
        Item item = itemMapper.fromCreate(itemCreate);
        return itemMapper.toResponse(itemService.create(item, userId));
    }

    @PatchMapping("/{itemId}")
    public ItemResponseDto update(@PathVariable int itemId,
                                  @RequestBody @Valid ItemUpdateDto itemUpdate,
                                  @RequestHeader("X-Sharer-User-Id") int userId) {
        Item item = itemMapper.fromUpdate(itemUpdate);
        return itemMapper.toResponse(itemService.update(itemId, item, userId));
    }

    @GetMapping("/{itemId}")
    public ItemResponseDto findById(@PathVariable int itemId) {
        return itemMapper.toResponse(itemService.findById(itemId));
    }

    @GetMapping("/search")
    public List<ItemResponseDto> findByName(@RequestParam String text) {
        return itemService.findByText(text)
                .stream()
                .map(itemMapper::toResponse)
                .toList();
    }

    @GetMapping
    public List<ItemResponseDto> findAllByOwner(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.findAllByOwner(userId)
                .stream()
                .map(itemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{itemId}")
    public void deleteById(@PathVariable int itemId) {
        itemService.deleteById(itemId);
    }
}