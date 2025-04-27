//package kz.zzhalelov.sharematespringboot.item;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kz.zzhalelov.sharematespringboot.booking.BookingController;
//import kz.zzhalelov.sharematespringboot.comment.CommentRepository;
//import kz.zzhalelov.sharematespringboot.comment.CommentService;
//import kz.zzhalelov.sharematespringboot.comment.CommentServiceImpl;
//import kz.zzhalelov.sharematespringboot.comment.dto.CommentMapper;
//import kz.zzhalelov.sharematespringboot.item.dto.ItemMapper;
//import lombok.SneakyThrows;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@WebMvcTest({ItemController.class,
//        ItemMapper.class,
//        CommentMapper.class,
//        CommentService.class,
//        ItemRepository.class,
//        CommentServiceImpl.class})
//class ItemControllerTest {
//    @MockitoBean
//    ItemService itemService;
//
//    @MockitoBean
//    ItemRepository itemRepository;
//    @MockitoBean
//    CommentRepository commentRepository;
//    @MockitoBean
//    BookingController bookingController;
//    @MockitoBean
//    CommentServiceImpl commentService;
//    @Mock
//    ItemMapper itemMapper;
//    @Mock
//    CommentMapper commentMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @SneakyThrows
//    void findById_shouldReturnOk() {
//        int itemId = 1;
//
//        Item item = new Item();
//        item.setId(itemId);
//        item.setName("test");
//
//        Mockito
//                .when(itemService.findById(Mockito.anyInt()))
//                .thenReturn(item);
//
//        mockMvc.perform(get("/items/" + itemId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(itemId))
//                .andExpect(jsonPath("$.name").value(item.getName()));
//    }
//}