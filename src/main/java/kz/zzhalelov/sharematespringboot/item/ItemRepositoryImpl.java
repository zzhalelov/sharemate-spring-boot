package kz.zzhalelov.sharematespringboot.item;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ItemRepositoryImpl implements ItemRepository {
    private final Map<Integer, Item> itemMap = new HashMap<>();
    private int counter = 1;

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            item.setId(getUniqueId());
        }
        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(int itemId) {
        return Optional.ofNullable(itemMap.get(itemId));
    }

    @Override
    public List<Item> findAllByOwner(int userId) {
        return itemMap.values()
                .stream()
                .filter(item -> item.getOwner().getId() == userId)
                .toList();
    }

    private int getUniqueId() {
        return counter++;
    }

    @Override
    public List<Item> findByText(String text) {
        return itemMap.values()
                .stream()
                .filter(item -> (item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase()))
                        && item.getAvailable())
                .toList();
    }
}