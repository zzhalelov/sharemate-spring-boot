package kz.zzhalelov.sharemate.server.booking;

import kz.zzhalelov.sharemate.server.user.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByBookerOrderByStartDesc(User booker, Pageable pageable);

    List<Booking> findAllByItem_Owner(User owner);

    List<Booking> findAllByItem_OwnerOrderByStartDesc(User owner, Pageable pageable);

    Optional<Booking> findByItem_IdAndBooker_IdAndStatusAndStartBefore(int itemId,
                                                                       int userId,
                                                                       BookingStatus bookingStatus,
                                                                       LocalDateTime localDateTime,
                                                                       Limit limit);

    List<Booking> findAllByBookerAndStartIsAfterOrderByStartDesc(User booker, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerAndStartBeforeAndEndIsAfter(User booker, LocalDateTime now, LocalDateTime now1, Pageable pageable);

    List<Booking> findAllByBookerAndEndIsBeforeOrderByStartDesc(User booker, LocalDateTime now, Pageable pageable);

    List<Booking> findAllByBookerAndStatus(User booker, BookingStatus bookingStatus, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndStartIsAfterOrderByStartDesc(User owner, LocalDateTime time, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndStartBeforeAndEndIsAfter(User owner, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndEndIsBeforeOrderByStartDesc(User owner, LocalDateTime time, Pageable pageable);

    List<Booking> findAllByItem_OwnerAndStatus(User owner, BookingStatus status, Pageable pageable);

    Optional<Booking> findTopByItem_IdAndStatusAndStartBeforeOrderByEndDesc(int itemId, BookingStatus status, LocalDateTime now);

    Optional<Booking> findTopByItem_IdAndStatusAndStartAfterOrderByStart(int itemId, BookingStatus status, LocalDateTime now);
}