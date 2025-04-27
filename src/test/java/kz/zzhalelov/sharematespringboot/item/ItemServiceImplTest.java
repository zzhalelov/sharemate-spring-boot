package kz.zzhalelov.sharematespringboot.item;

import kz.zzhalelov.sharematespringboot.comment.dto.CommentMapper;
import kz.zzhalelov.sharematespringboot.item.dto.ItemMapper;
import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {
    @Mock
    ItemRepository itemRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ItemServiceImpl itemService;

    @Spy
    ItemMapper itemMapper = new ItemMapper(null);

    @Test
    void create_shouldCreate() {
        User user = new User();
        user.setId(1);
        user.setName("test");

        Item item = new Item();
        item.setId(1);
        item.setName("test");
        item.setAvailable(true);
        item.setDescription("test");
        item.setOwner(user);

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        Mockito
                .when(itemRepository.save(Mockito.any(Item.class)))
                .thenReturn(item);

        Item savedItem = itemService.create(item, user.getId());

        assertEquals(item.getId(), savedItem.getId());
        assertEquals(item.getName(), savedItem.getName());
        assertEquals(item.getAvailable(), savedItem.getAvailable());
        assertEquals(item.getDescription(), savedItem.getDescription());
    }

    @Test
    void update_updateItem() {
        User user = new User();
        user.setId(1);
        user.setName("test");
        int itemId = 1;

        Item existingItem = new Item();
        existingItem.setId(itemId);
        existingItem.setName("test");
        existingItem.setDescription("test");
        existingItem.setAvailable(true);
        existingItem.setOwner(user);

        Item updatedItem = new Item();
        updatedItem.setName("new test name");

        Mockito
                .when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        Mockito
                .when(itemRepository.findById(itemId))
                .thenReturn(Optional.of(existingItem));

        Mockito
                .when(itemRepository.save(Mockito.any(Item.class)))
                .thenAnswer(invocationOnMock -> {
                    Item argument = invocationOnMock.getArgument(0, Item.class);
                    argument.setId(itemId);
                    return argument;
                });

        Item item = itemService.update(itemId, updatedItem, user.getId());

        assertEquals("new test name", item.getName());
    }

    @Test
    void findById_whenItemExists_shouldReturnItem() {
        int itemId = 1;

        Item existingItem = new Item();
        existingItem.setId(itemId);
        existingItem.setName("testItem");
        existingItem.setAvailable(true);
        existingItem.setDescription("testDescription");

        Mockito
                .when(itemRepository.findById(Mockito.anyInt()))
                .thenReturn(Optional.of(existingItem));

        Item item = itemService.findById(itemId);
        assertEquals(itemId, item.getId());
        assertEquals(existingItem.getName(), item.getName());
        assertEquals(existingItem.getAvailable(), item.getAvailable());
        assertEquals(existingItem.getDescription(), item.getDescription());
    }

    @Test
    void findAllByOwnerId_shouldReturnListOfItems() {
        int ownerId = 1;

        User owner = new User();
        owner.setId(ownerId);
        owner.setName("owner");

        Item existingItem1 = new Item();
        existingItem1.setId(1);
        existingItem1.setName("testItem1");
        existingItem1.setAvailable(true);
        existingItem1.setDescription("testDescription1");
        existingItem1.setOwner(owner);

        Item existingItem2 = new Item();
        existingItem2.setId(2);
        existingItem2.setName("testItem2");
        existingItem2.setAvailable(true);
        existingItem2.setDescription("testDescription2");
        existingItem2.setOwner(owner);

        List<Item> existingItems = List.of(existingItem1, existingItem2);

        Mockito
                .when(itemRepository.findByOwner_Id(ownerId))
                .thenReturn(existingItems);

        List<Item> items = itemService.findAllByOwner(ownerId);
        assertEquals(2, items.size());
        assertEquals(existingItem1.getName(), items.get(0).getName());
        assertEquals(existingItem1.getName(), items.get(0).getName());
        assertEquals(existingItem1.getAvailable(), items.get(0).getAvailable());
        assertEquals(existingItem1.getDescription(), items.get(0).getDescription());
        assertEquals(existingItem1.getOwner(), items.get(0).getOwner());
        assertEquals(existingItem2.getName(), items.get(1).getName());
        assertEquals(existingItem2.getName(), items.get(1).getName());
        assertEquals(existingItem2.getAvailable(), items.get(1).getAvailable());
        assertEquals(existingItem2.getDescription(), items.get(1).getDescription());
        assertEquals(existingItem2.getOwner(), items.get(1).getOwner());
    }

    @Test
    void findByText_shouldReturnListOfItems() {
        String text = "text";

        Item existingItem = new Item();
        existingItem.setId(1);
        existingItem.setName("text");
        existingItem.setAvailable(true);
        existingItem.setDescription("text");

        List<Item> existingItems = List.of(existingItem);

        Mockito
                .when(itemRepository.search(text))
                .thenReturn(existingItems);

        List<Item> itemList = itemService.findByText(text);
        assertEquals(1, itemList.size());
        assertEquals(existingItem.getName(), itemList.get(0).getName());
        assertEquals(existingItem.getAvailable(), itemList.get(0).getAvailable());
        assertEquals(existingItem.getDescription(), itemList.get(0).getDescription());
    }
}