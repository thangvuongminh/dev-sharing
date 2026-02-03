package com.example.contentservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class HelloController {
    @GetMapping("test")
    public ResponseEntity<String> hello() {
        log.info("hello");
        return ResponseEntity.ok("Hello World");
    }
}
