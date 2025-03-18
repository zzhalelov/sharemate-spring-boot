package kz.zzhalelov.sharematespringboot.booking;

import kz.zzhalelov.sharematespringboot.booking.dto.BookingCreateDto;
import kz.zzhalelov.sharematespringboot.booking.dto.BookingMapper;
import kz.zzhalelov.sharematespringboot.booking.dto.BookingResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    //POST /bookings   X-Shared-User-Id
    @PostMapping
    public BookingResponseDto create(@RequestBody BookingCreateDto bookingCreate,
                                     @RequestHeader("X-Sharer-User-Id") int bookerId) {
        Booking booking = bookingMapper.fromCreate(bookingCreate);
        return bookingMapper.toResponse(bookingService.create(booking, bookerId));
    }

    //PATCH /bookings/{id}?approved= true   X-Shared-User-Id
    @PatchMapping("/{bookingId}")
    public BookingResponseDto update(@PathVariable int bookingId,
                                     @RequestParam boolean approved,
                                     @RequestHeader("X-Sharer-User-Id") int userId) {
        return bookingMapper.toResponse(bookingService.update(bookingId, userId, approved));
    }


    //GET /bookings/{id}        X-Shared-User-Id
    @GetMapping("/{bookingId}")
    public BookingResponseDto findById(@PathVariable int bookingId,
                                       @RequestHeader("X-Sharer-User-Id") int userId) {
        return bookingMapper.toResponse(bookingService.findById(bookingId, userId));
    }

    //GET /bookings/owner    X-Shared-User-Id
    @GetMapping("/owner")
    public List<BookingResponseDto> findByOwner(@RequestHeader("X-Sharer-User-Id") int userId) {
        return bookingService.findAllByOwner(userId)
                .stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    //GET /bookings         X-Shared-User-Id
    @GetMapping
    public List<BookingResponseDto> findAll(@RequestHeader("X-Sharer-User-Id") int userId) {
        return bookingService.findAllByBooker(userId)
                .stream()
                .map(bookingMapper::toResponse)
                .collect(Collectors.toList());
    }
}