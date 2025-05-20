package kz.zzhalelov.sharemate.gateway.item;

import kz.zzhalelov.sharemate.server.item.dto.ItemCreateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

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

    public ResponseEntity<Object> create(ItemCreateDto itemCreateDto) {
        return restTemplate.postForEntity("/items", itemCreateDto, Object.class);
    }

    public ResponseEntity<Object> findAll() {
        return restTemplate.getForEntity("items", Object.class);
    }
}