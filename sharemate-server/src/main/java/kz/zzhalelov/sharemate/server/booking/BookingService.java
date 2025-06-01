package kz.zzhalelov.sharemate.server.booking;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking, int bookerId);

    List<Booking> findAllByBooker(int bookerId, BookingState bookingState);

    List<Booking> findAllByOwner(int ownerId, BookingState bookingState);

    Booking findById(int bookingId, int userId);

    Booking update(int bookingId, int userId, boolean isApproved);
}