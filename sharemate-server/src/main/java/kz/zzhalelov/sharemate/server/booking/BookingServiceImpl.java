package kz.zzhalelov.sharemate.server.booking;

import kz.zzhalelov.sharemate.server.exception.BadRequestException;
import kz.zzhalelov.sharemate.server.exception.NotFoundException;
import kz.zzhalelov.sharemate.server.item.Item;
import kz.zzhalelov.sharemate.server.item.ItemRepository;
import kz.zzhalelov.sharemate.server.user.User;
import kz.zzhalelov.sharemate.server.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Booking create(Booking booking, int bookerId) {
        if (booking.getStart().isAfter(booking.getEnd())
                || (booking.getEnd().isBefore(booking.getStart()))
                || (booking.getStart().isEqual(booking.getEnd()))) {
            throw new BadRequestException("Даты некорректные");
        }

        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Товар не найден"));

        if (item.getOwner().getId().equals(bookerId)) {
            throw new NotFoundException("Владелец не может бронировать свои вещи");
        }

        if (!item.getAvailable()) {
            throw new BadRequestException("Товар не досупен");
        }

        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAllByBooker(int bookerId, BookingState bookingState) {
        User booker = userRepository.findById(bookerId).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        switch (bookingState) {
            case ALL:
                return bookingRepository.findAllByBookerOrderByStartDesc(booker);
            case FUTURE:
                return bookingRepository.findAllByBookerAndStartIsAfterOrderByStartDesc(booker, now);
            case CURRENT:
                return bookingRepository.findAllByBookerAndStartBeforeAndEndIsAfter(booker, now, now);
            case PAST:
                return bookingRepository.findAllByBookerAndEndIsBeforeOrderByStartDesc(booker, now);
            case REJECTED:
                return bookingRepository.findAllByBookerAndStatus(booker, BookingStatus.REJECTED);
            default:
                return bookingRepository.findAllByBookerAndStatus(booker, BookingStatus.WAITING);
        }
    }

    @Override
    public List<Booking> findAllByOwner(int ownerId, BookingState state) {
        User owner = userRepository.findById(ownerId).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        switch (state) {
            case ALL:
                return bookingRepository.findAllByItem_OwnerOrderByStartDesc(owner);
            case FUTURE:
                return bookingRepository.findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(owner, now);
            case CURRENT:
                return bookingRepository.findAllByItem_OwnerAndStartBeforeAndEndIsAfter(owner, now, now);
            case PAST:
                return bookingRepository.findAllByItem_OwnerAndEndIsBeforeOrderByStartDesc(owner, now);
            case REJECTED:
                return bookingRepository.findAllByItem_OwnerAndStatus(owner, BookingStatus.REJECTED);
            default:
                return bookingRepository.findAllByItem_OwnerAndStatus(owner, BookingStatus.WAITING);
        }
    }

    @Override
    public Booking findById(int bookingId, int userId) {

        Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Бронирование не найдено"));

        if (!booking.getBooker().getId().equals(userId)
                && !booking.getItem().getOwner().getId().equals(userId)) {
            throw new NotFoundException("Бронирование не найдено");
        }
        return booking;
    }

    @Override
    public Booking update(int bookingId, int userId, boolean isApproved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getItem().getOwner().getId() != userId) {
            throw new NotFoundException("Бронирование не найдено");
        }

        if (booking.getStatus().equals(BookingStatus.APPROVED)) {
            throw new BadRequestException("Бронирование уже одобрено");
        }

        if (isApproved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingRepository.save(booking);
    }
}