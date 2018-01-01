package com.example.service1;

import org.reactivestreams.Publisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import rx.Observable;

@RestController
public class MyController {

    @GetMapping("/test")
    Mono<String> getHello() {
        System.out.println("I received a connection request");
        return Mono.just("Hello world");
    }
}
