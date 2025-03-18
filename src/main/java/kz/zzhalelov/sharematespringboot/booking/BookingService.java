package kz.zzhalelov.sharematespringboot.booking;

import kz.zzhalelov.sharematespringboot.user.User;

import java.awt.print.Book;
import java.util.List;

public interface BookingService {
    Booking create(Booking booking, int bookerId);

    List<Booking> findAllByBooker(int bookerId);

    List<Booking> findAllByOwner(int ownerId);

    Booking findById(int bookingId, int userId);

    Booking update(int bookingId, int userId, boolean isApproved);
}