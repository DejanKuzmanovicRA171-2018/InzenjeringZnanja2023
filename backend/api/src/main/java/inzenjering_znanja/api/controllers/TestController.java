package inzenjering_znanja.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestController {
    @GetMapping("/")
    public String testApi() {
        return "Hello";
    }

}
