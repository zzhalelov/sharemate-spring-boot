package kz.zzhalelov.sharemate.server.item;

import kz.zzhalelov.sharemate.server.booking.BookingRepository;
import kz.zzhalelov.sharemate.server.booking.BookingStatus;
import kz.zzhalelov.sharemate.server.exception.NotFoundException;
import kz.zzhalelov.sharemate.server.item.dto.ItemMapper;
import kz.zzhalelov.sharemate.server.user.User;
import kz.zzhalelov.sharemate.server.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemMapper itemMapper;
    private final BookingRepository bookingRepository;

    public ItemServiceImpl(ItemRepository itemRepository, UserRepository userRepository, ItemMapper itemMapper, BookingRepository bookingRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.itemMapper = itemMapper;
        this.bookingRepository = bookingRepository;
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
        itemMapper.merge(existingItem, updatedItem);
        return itemRepository.save(existingItem);
    }

    @Override
    public Item findById(int itemId, int userId) {
        return itemRepository.findById(itemId)
                .map(item -> {
                    if (item.getOwner().getId().equals(userId)) {
                        item.setLastBooking(bookingRepository
                                .findTopByItem_IdAndStatusAndEndBeforeOrderByEndDesc(itemId, BookingStatus.APPROVED, LocalDateTime.now()).orElse(null));
                        item.setNextBooking(bookingRepository.findTopByItem_IdAndStatusAndStartAfterOrderByStart(itemId, BookingStatus.APPROVED, LocalDateTime.now()).orElse(null));
                    }
                    return item;
                })
                .orElseThrow(() -> new NotFoundException("Не найден user"));
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