package kz.zzhalelov.sharemate.server.booking;

import kz.zzhalelov.sharemate.server.user.User;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findAllByBooker(User booker);

    List<Booking> findAllByItem_Owner(User owner);

    Optional<Booking> findByItem_IdAndBooker_IdAndStatusAndStartBefore(int itemId,
                                                                       int userId,
                                                                       BookingStatus bookingStatus,
                                                                       LocalDateTime localDateTime,
                                                                       Limit limit);
}