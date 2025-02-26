package kz.zzhalelov.sharematespringboot.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public void endpointWithHeader(@RequestHeader("X-Sharer-User-Id") int userId) {
        System.out.println("value = " + userId);
    }
}