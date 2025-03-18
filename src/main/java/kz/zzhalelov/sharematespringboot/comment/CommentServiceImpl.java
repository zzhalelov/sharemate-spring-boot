package kz.zzhalelov.sharematespringboot.comment;

import kz.zzhalelov.sharematespringboot.booking.Booking;
import kz.zzhalelov.sharematespringboot.booking.BookingRepository;
import kz.zzhalelov.sharematespringboot.booking.BookingStatus;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentCreateDto;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentMapper;
import kz.zzhalelov.sharematespringboot.comment.dto.CommentResponseDto;
import kz.zzhalelov.sharematespringboot.exception.BadRequestException;
import kz.zzhalelov.sharematespringboot.exception.NotFoundException;
import kz.zzhalelov.sharematespringboot.item.Item;
import kz.zzhalelov.sharematespringboot.item.ItemRepository;
import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Comment create(int itemId,
                          int userId,
                          Comment comment) {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Не найден товар"));
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        List<Booking> bookings = bookingRepository.findAllByItemAndBookerAndStatusAndStartAfter(item, user, BookingStatus.APPROVED, LocalDateTime.now());
        if (bookings.isEmpty()) {
            throw new BadRequestException("Данный пользователь ранее не ьронировал предмет");
        }
        comment.setItem(item);
        comment.setAuthor(user);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByItem(int itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return commentRepository.findAllByItem(item);
    }
}