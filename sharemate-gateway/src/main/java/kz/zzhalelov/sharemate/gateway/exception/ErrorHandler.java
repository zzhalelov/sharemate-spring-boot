package kz.zzhalelov.sharemate.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    public ResponseEntity<Object> handleHttpClientError(HttpClientErrorException e) {
        return new ResponseEntity<>(
                e.getResponseBodyAsString(),
                e.getStatusCode()
        );
    }
}