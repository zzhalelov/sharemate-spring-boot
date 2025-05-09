package kz.zzhalelov.sharematespringboot.item;


import com.fasterxml.jackson.databind.ObjectMapper;
import kz.zzhalelov.sharematespringboot.comment.CommentService;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentMapper;
import kz.zzhalelov.sharematespringboot.item.dto.ItemCreateDto;
import kz.zzhalelov.sharematespringboot.item.dto.ItemFullResponseDto;
import kz.zzhalelov.sharematespringboot.item.dto.ItemMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest({ItemController.class, ItemMapper.class})
class ItemControllerTest {
    @MockitoBean
    ItemService itemService;
    @MockitoBean
    ItemMapper itemMapper;
    @MockitoBean
    CommentMapper commentMapper;
    @MockitoBean
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void create_shouldReturnCreated() {
        int userId = 1;
        int itemId = 1;

        ItemCreateDto createDto = new ItemCreateDto();
        createDto.setName("test");
        createDto.setDescription("description");
        createDto.setAvailable(true);

        Item savedItem = new Item();
        savedItem.setId(itemId);
        savedItem.setName("test");
        savedItem.setDescription("description");
        savedItem.setAvailable(true);

        Mockito
                .when(itemService.create(Mockito.any(Item.class), Mockito.eq(userId)))
                .thenReturn(savedItem);

        String json = objectMapper.writeValueAsString(createDto);

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.available").value("description"));
    }

    @Test
    @SneakyThrows
    void findById_whenItemExists_shouldReturnOk() {
        int itemId = 1;

        Item item = new Item();
        item.setId(itemId);
        item.setName("test");
        item.setAvailable(true);
        item.setDescription("test");

        ItemFullResponseDto responseDto = new ItemFullResponseDto();
        responseDto.setId(item.getId());
        responseDto.setName(item.getName());
        responseDto.setAvailable(item.getAvailable());
        responseDto.setDescription(item.getDescription());

        Mockito
                .when(itemService.findById(Mockito.anyInt()))
                .thenReturn(item);

        Mockito.when(itemMapper.toFullResponse(item))
                .thenReturn(responseDto);

        mockMvc.perform(get("/items/" + itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value(item.getName()))
                .andExpect(jsonPath("$.available").value(item.getAvailable()))
                .andExpect(jsonPath("$.description").value(item.getDescription()));
    }

//    @Test
//    @SneakyThrows
//    void findByName_whenItemExists_shouldReturnOk() {
//        String text = "test";
//        int itemId = 1;
//
//        Item item = new Item();
//        item.setId(itemId);
//        item.setName("test");
//        item.setAvailable(true);
//        item.setDescription("test");
//
//        Mockito
//                .when(itemService.findByText(Mockito.anyString()))
//                .thenReturn(List.of(item));
//
//        mockMvc.perform(get("/items/search")
//                        .param("text", text))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(itemId))
//                .andExpect(jsonPath("$[0].name").value(item.getName()))
//                .andExpect(jsonPath("$[0].available").value(item.getAvailable()))
//                .andExpect(jsonPath("$[0].description").value(item.getDescription()));
//    }
}