package kz.zzhalelov.sharemate.gateway.booking;

import jakarta.validation.Valid;
import kz.zzhalelov.sharemate.server.booking.BookingService;
import kz.zzhalelov.sharemate.server.booking.dto.BookingCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") long bookerId,
                                         @Valid @RequestBody BookingCreateDto bookingCreateDto) {
        return bookingClient.create(bookerId, bookingCreateDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> update(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable long bookingId,
                                         @RequestParam boolean approved) {
        return bookingClient.update(userId, bookingId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findById(@RequestHeader("X-Sharer-User-Id") long userId,
                                           @PathVariable long bookingId) {
        return bookingClient.findById(userId, bookingId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findAllByOwner(@RequestHeader("X-Sharer-User-Id") long userId) {
        return bookingClient.findAllByOwner(userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByBooker(@RequestHeader("X-Sharer-User-Id") long userId,
                                                  @RequestParam(defaultValue = "ALL") String state) {
        return bookingClient.findAll(userId, state);
    }
}