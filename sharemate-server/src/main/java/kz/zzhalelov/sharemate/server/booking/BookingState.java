package kz.zzhalelov.sharemate.server.booking;

import kz.zzhalelov.sharemate.server.exception.BadRequestException;

public enum BookingState {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static BookingState from(String value) {
        for (BookingState bookingState : values()) {
            if (bookingState.name().equals(value)) {
                return bookingState;
            }
        }
        throw new BadRequestException("Unknown state: " + value);
    }
}