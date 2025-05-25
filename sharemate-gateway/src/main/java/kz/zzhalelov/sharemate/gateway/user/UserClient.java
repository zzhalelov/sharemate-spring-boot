package kz.zzhalelov.sharemate.gateway.user;

import kz.zzhalelov.sharemate.server.user.dto.UserCreateDto;
import kz.zzhalelov.sharemate.server.user.dto.UserUpdateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Map;

@Component
public class UserClient {
    private final RestTemplate restTemplate;

    public UserClient(@Value("${sharemate-server.server.url}") String url,
                      RestTemplateBuilder builder) {
        this.restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                .build();
    }

    public ResponseEntity<Object> findById(long userId) {
        return restTemplate.getForEntity("/users/{id}", Object.class,
                Map.of("id", userId));
    }

    public ResponseEntity<Object> create(UserCreateDto userCreateDto) {
        return restTemplate.postForEntity("/users", userCreateDto, Object.class);
    }

    public ResponseEntity<Object> findAll() {
        return restTemplate.getForEntity("/users", Object.class);
    }

    public ResponseEntity<Object> update(long userId, UserUpdateDto userUpdateDto) {
        HttpEntity<UserUpdateDto> httpEntity = new HttpEntity<>(userUpdateDto);
        return restTemplate.exchange("users/{userId}", HttpMethod.PATCH, httpEntity, Object.class, Map.of("userId", userId));
    }

    public ResponseEntity<Object> delete(long userId) {
        return restTemplate.exchange("users/{userId}", HttpMethod.DELETE, null, Object.class, Map.of("userId", userId));
    }
}