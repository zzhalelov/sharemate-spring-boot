package kz.zzhalelov.sharematespringboot.item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    Optional<Item> findById(int itemId);

    List<Item> findByText(String text);

    List<Item> findAllByOwner(int userId);
}