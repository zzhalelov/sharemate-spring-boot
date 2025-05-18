package kz.zzhalelov.sharemate.server.comment;

import kz.zzhalelov.sharemate.server.booking.Booking;
import kz.zzhalelov.sharemate.server.booking.BookingRepository;
import kz.zzhalelov.sharemate.server.booking.BookingStatus;
import kz.zzhalelov.sharemate.server.exception.BadRequestException;
import kz.zzhalelov.sharemate.server.item.Item;
import kz.zzhalelov.sharemate.server.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final BookingRepository bookingRepository;

    @Override
    public Comment create(int itemId,
                          int userId,
                          Comment comment) {
        Booking booking = bookingRepository.findByItem_IdAndBooker_IdAndStatusAndStartBefore(
                        itemId,
                        userId,
                        BookingStatus.APPROVED,
                        LocalDateTime.now(),
                        Limit.of(1))
                .orElseThrow(() -> new BadRequestException("Данный пользователь ранее не бронировал предмет"));
        comment.setItem(booking.getItem());
        comment.setAuthor(booking.getBooker());
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> findAllByItem(int itemId) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        return commentRepository.findAllByItem(item);
    }
}