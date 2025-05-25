package kz.zzhalelov.sharemate.gateway.booking;

import kz.zzhalelov.sharemate.server.booking.dto.BookingCreateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class BookingClient {
    private final RestTemplate restTemplate;
    private final String HEADER = "X-Sharer-User-Id";

    public BookingClient(@Value("${sharemate-server.server.url}") String url,
                         RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> create(long bookerId,
                                         BookingCreateDto bookingCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER, String.valueOf(bookerId));
        HttpEntity<BookingCreateDto> entity = new HttpEntity<>(bookingCreateDto, headers);
        return restTemplate.postForEntity("/bookings", entity, Object.class);
    }

    public ResponseEntity<Object> update(long userId,
                                         long bookingId,
                                         boolean approved) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER, String.valueOf(userId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/bookings/{bookingId}?approved={approved}",
                HttpMethod.PATCH, entity, Object.class,
                Map.of("bookingId", bookingId, "approved", approved));
    }

    public ResponseEntity<Object> findById(long userId,
                                           long bookingId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER, String.valueOf(userId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/bookings/{bookingId}",
                HttpMethod.GET, entity, Object.class,
                Map.of("bookingId", bookingId));
    }

    public ResponseEntity<Object> findAllByOwner(long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER, String.valueOf(userId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/bookings/owner", HttpMethod.GET, entity, Object.class);
    }

    public ResponseEntity<Object> findAll(long userId, String state) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER, String.valueOf(userId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/bookings?state={state}",
                HttpMethod.GET, entity, Object.class,
                Map.of("state", state));
    }
}