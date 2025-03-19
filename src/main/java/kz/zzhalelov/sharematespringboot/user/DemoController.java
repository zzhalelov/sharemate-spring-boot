package kz.zzhalelov.sharematespringboot.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private static final String HEADER = "X-Sharer-User-Id";

    @GetMapping("/demo")
    public void endpointWithHeader(@RequestHeader(HEADER) int userId) {
        System.out.println("value = " + userId);
    }
}