package io.haedoang.querydsl.controller;

/**
 * packageName : io.haedoang.querydsl.controller
 * fileName : HelloController
 * author : haedoang
 * date : 2022-05-03
 * description :
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }
}
