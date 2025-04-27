//package kz.zzhalelov.sharematespringboot.comment;
//
//import kz.zzhalelov.sharematespringboot.item.Item;
//import kz.zzhalelov.sharematespringboot.user.User;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class CommentServiceImplTest {
//    @Mock
//    CommentRepository commentRepository;
//
//    @InjectMocks
//    CommentServiceImpl commentService;
//
//    @Test
//    void findById_whenCommentExists_shouldReturnComment() {
//        User user = new User();
//        user.setId(1);
//        user.setName("owner");
//
//        Item item = new Item();
//        item.setId(1);
//        item.setName("testItem1");
//        item.setAvailable(true);
//        item.setDescription("testDescription1");
//        item.setOwner(user);
//
//        int commentId = 1;
//
//        Comment existingComment = new Comment();
//        existingComment.setId(commentId);
//        existingComment.setItem(item);
//        existingComment.setAuthor(user);
//        existingComment.setText("test");
//        existingComment.setCreatedAt(LocalDateTime.now());
//
//        item.setComments(List.of(existingComment));
//
//        Mockito
//                .when(commentRepository.findById(Mockito.anyInt()))
//                .thenReturn(Optional.of(existingComment));
//
//        List<Comment> comments = commentService.findAllByItem(item.getId());
//        assertEquals(1, comments.size());
//    }
//
//}