package kz.zzhalelov.sharemate.server.item;

import java.util.List;

public interface ItemService {
    Item create(Item item, int userId);

    Item update(int itemId, Item updatedItem, int userId);

    Item findById(int itemId);

    void deleteById(int itemId);

    List<Item> findByText(String text);

    List<Item> findAllByOwner(int userId);
}