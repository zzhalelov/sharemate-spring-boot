package kz.zzhalelov.sharemate.server.booking;

import kz.zzhalelov.sharemate.server.user.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByBookerOrderByStartDesc(User booker);

    List<Booking> findAllByItem_Owner(User owner);

    List<Booking> findAllByItem_OwnerOrderByStartDesc(User owner);

    Optional<Booking> findByItem_IdAndBooker_IdAndStatusAndStartBefore(int itemId,
                                                                       int userId,
                                                                       BookingStatus bookingStatus,
                                                                       LocalDateTime localDateTime,
                                                                       Limit limit);

    List<Booking> findAllByBookerAndStartIsAfterOrderByStartDesc(User booker, LocalDateTime start);

    List<Booking> findAllByBookerAndStartBeforeAndEndIsAfter(User booker, LocalDateTime now, LocalDateTime now1);

    List<Booking> findAllByBookerAndEndIsBefore(User booker, LocalDateTime now);

    List<Booking> findAllByBookerAndStatus(User booker, BookingStatus bookingStatus);

    List<Booking> findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(User owner, LocalDateTime time);

    List<Booking> findAllByItem_OwnerAndStartBeforeAndEndIsAfter(User owner, LocalDateTime start, LocalDateTime end);

    List<Booking> findAllByItem_OwnerAndEndIsBefore(User owner, LocalDateTime time);

    List<Booking> findAllByItem_OwnerAndStatus(User owner, BookingStatus status);

    Optional<Booking> findTopByItem_IdAndStatusAndEndBeforeOrderByEndDesc(int itemId, BookingStatus status, LocalDateTime now);

    Optional<Booking> findTopByItem_IdAndStatusAndStartAfterOrderByStart(int itemId, BookingStatus status, LocalDateTime now);
}