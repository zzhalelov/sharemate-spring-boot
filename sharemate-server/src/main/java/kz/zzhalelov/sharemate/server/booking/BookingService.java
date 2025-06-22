package kz.zzhalelov.sharemate.server.booking;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking, int bookerId);

    List<Booking> findAllByBooker(int bookerId, BookingState bookingState, int from, int size);

    List<Booking> findAllByOwner(int ownerId, BookingState bookingState, int from, int size);

    Booking findById(int bookingId, int userId);

    Booking update(int bookingId, int userId, boolean isApproved);
}