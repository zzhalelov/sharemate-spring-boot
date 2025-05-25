package kz.zzhalelov.sharemate.gateway.item;

import kz.zzhalelov.sharemate.server.comment.dto.CommentCreateDto;
import kz.zzhalelov.sharemate.server.item.dto.ItemCreateDto;
import kz.zzhalelov.sharemate.server.item.dto.ItemUpdateDto;
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
public class ItemClient {
    private final RestTemplate restTemplate;

    public ItemClient(@Value("${sharemate-server.server.url}") String url,
                      RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> findById(long itemId) {
        return restTemplate.getForEntity("/items/{id}", Object.class,
                Map.of("id", itemId));
    }

    public ResponseEntity<Object> searchByText(String text) {
        return restTemplate.getForEntity("/items/search?text={text}",
                Object.class,
                Map.of("text", text));
    }

    public ResponseEntity<Object> findAllByOwner(long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange("/items",
                HttpMethod.GET,
                entity,
                Object.class);
    }

    public ResponseEntity<Object> create(long userId, ItemCreateDto itemCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<ItemCreateDto> entity = new HttpEntity<>(itemCreateDto, headers);
        return restTemplate.postForEntity("/items", entity, Object.class);
    }

    public ResponseEntity<Object> findAll() {
        return restTemplate.getForEntity("items", Object.class);
    }

    public ResponseEntity<Object> update(long userID,
                                         long itemId,
                                         ItemUpdateDto itemUpdateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(userID));
        HttpEntity<ItemUpdateDto> httpEntity = new HttpEntity<>(itemUpdateDto, headers);
        return restTemplate.exchange("items/{itemId}",
                HttpMethod.PATCH,
                httpEntity,
                Object.class,
                Map.of("itemId", itemId));
    }

    public ResponseEntity<Object> delete(Long itemId) {
        return restTemplate.exchange("items/{itemId}",
                HttpMethod.DELETE,
                null,
                Object.class,
                Map.of("itemId", itemId));
    }

    public ResponseEntity<Object> createComment(long userId,
                                                long itemId,
                                                CommentCreateDto commentCreateDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Sharer-User-Id", String.valueOf(userId));
        HttpEntity<CommentCreateDto> entity = new HttpEntity<>(commentCreateDto, headers);
        return restTemplate.postForEntity("/items/{itemId}/comment",
                entity,
                Object.class,
                Map.of("itemId", itemId));
    }
}