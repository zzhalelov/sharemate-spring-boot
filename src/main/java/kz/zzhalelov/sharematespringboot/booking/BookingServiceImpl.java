package kz.zzhalelov.sharematespringboot.booking;

import kz.zzhalelov.sharematespringboot.booking.dto.BookingMapper;
import kz.zzhalelov.sharematespringboot.exception.BadRequestException;
import kz.zzhalelov.sharematespringboot.exception.ForbiddenException;
import kz.zzhalelov.sharematespringboot.exception.NotFoundException;
import kz.zzhalelov.sharematespringboot.item.Item;
import kz.zzhalelov.sharematespringboot.item.ItemRepository;
import kz.zzhalelov.sharematespringboot.user.User;
import kz.zzhalelov.sharematespringboot.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public BookingServiceImpl(BookingMapper bookingMapper, BookingRepository bookingRepository, UserRepository userRepository, ItemRepository itemRepository) {
        this.bookingMapper = bookingMapper;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Booking create(Booking booking, int bookerId) {
        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));

        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Товар не найден"));
        if (!item.getAvailable()) {
            throw new BadRequestException("Товар не досупен");
        }

        booking.setBooker(booker);
        booking.setItem(item);
        booking.setStatus(BookingStatus.WAITING);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAllByBooker(int bookerId) {
        User booker = userRepository.findById(bookerId).orElseThrow();
        return bookingRepository.findAllByBooker(booker);
    }

    @Override
    public List<Booking> findAllByOwner(int ownerId) {
        User owner = userRepository.findById(ownerId).orElseThrow();
        return bookingRepository.findAllByItem_Owner(owner);
    }

    @Override
    public Booking findById(int bookingId, int userId) {
        return bookingRepository.findById(bookingId).orElseThrow();
    }

    @Override
    public Booking update(int bookingId, int userId, boolean isApproved) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if (booking.getItem().getOwner().getId() != userId) {
            throw new ForbiddenException("Не является владеотцем");
        }

        if (isApproved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return bookingRepository.save(booking);
    }
}