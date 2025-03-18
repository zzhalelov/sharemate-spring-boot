package kz.zzhalelov.sharematespringboot.booking;

import kz.zzhalelov.sharematespringboot.item.Item;
import kz.zzhalelov.sharematespringboot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByBooker(User booker);

    List<Booking> findAllByItem_Owner(User owner);

    List<Booking> findAllByItemAndBookerAndStatusAndStartAfter(Item item, User user, BookingStatus bookingStatus, LocalDateTime localDateTime);
}