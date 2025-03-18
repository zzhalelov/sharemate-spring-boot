package kz.zzhalelov.sharematespringboot.item;

import kz.zzhalelov.sharematespringboot.exception.ForbiddenException;
import kz.zzhalelov.sharematespringboot.exception.NotFoundException;
import kz.zzhalelov.sharematespringboot.item.dto.ItemMapper;
import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
    }

    @Override
    public Item create(Item item, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден user"));
        item.setOwner(user);
        return itemRepository.save(item);
    }

    @Override
    public Item update(int itemId, Item updatedItem, int userId) {
        Item existingItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Не найден товар"));
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Не найден user"));
        if (existingItem.getOwner().getId() != userId) {
            throw new ForbiddenException("Этот пользователь не явдяется владельцем предмета");
        }
        itemMapper.merge(existingItem, updatedItem);
        return itemRepository.save(existingItem);
    }

    @Override
    public Item findById(int itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Не найден user"));
    }

    @Override
    public void deleteById(int itemId) {
        itemRepository.findById(itemId);
    }

    @Override
    public List<Item> findAllByOwner(int userId) {
        return itemRepository.findByOwner_Id(userId);
    }

    @Override
    public List<Item> findByText(String text) {
        if (text == null || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.search(text);
    }
}