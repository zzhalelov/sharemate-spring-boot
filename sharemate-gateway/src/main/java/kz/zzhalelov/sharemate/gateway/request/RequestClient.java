package kz.zzhalelov.sharemate.gateway.request;

import kz.zzhalelov.sharemate.server.request.dto.RequestCreateDto;
import kz.zzhalelov.sharemate.server.request.dto.RequestUpdateDto;
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
public class RequestClient {
    private final RestTemplate restTemplate;

    public RequestClient(@Value("${sharemate-server.server.url}") String url,
                         RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> findById(Long requesterId, long requestId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(requesterId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/requests/{requesterId}",
                HttpMethod.GET,
                entity,
                Object.class,
                Map.of("requestId", requestId));
    }

    public ResponseEntity<Object> findAllMyRequests(Long requesterId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(requesterId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/requests",
                HttpMethod.GET,
                entity,
                Object.class);
    }

    public ResponseEntity<Object> findAllRequestsExceptMine(Long requesterId,
                                                            int from,
                                                            int size) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(requesterId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/requests/all?from={from}&size={size}",
                HttpMethod.GET,
                entity,
                Object.class,
                Map.of("from", from, "size", size));
    }

    public ResponseEntity<Object> create(long requesterId, RequestCreateDto requestCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(requesterId));
        HttpEntity<RequestCreateDto> entity = new HttpEntity<>(requestCreateDto, headers);
        return restTemplate.postForEntity("/requests", entity, Object.class);
    }

    public ResponseEntity<Object> update(long requesterId,
                                         long requestId,
                                         RequestUpdateDto requestUpdateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(requesterId));
        HttpEntity<RequestUpdateDto> entity = new HttpEntity<>(requestUpdateDto, headers);
        return restTemplate.exchange("/requests/{requestId}",
                HttpMethod.PATCH,
                entity,
                Object.class,
                Map.of("requestId", requestId));
    }
}