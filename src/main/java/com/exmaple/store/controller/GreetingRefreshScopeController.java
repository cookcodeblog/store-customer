package com.exmaple.store.controller;

import com.exmaple.store.vo.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RefreshScope
@RestController
public class GreetingRefreshScopeController {

    @Value("${greeting.message}")
    private String greetingMessage;

    @GetMapping("/greeting")
    public Message greeting(@RequestParam(value = "name", defaultValue = "Apple") String name) {
        Objects.requireNonNull(greetingMessage, "Greeting message was not set in the properties");

        String message = String.format(greetingMessage, name);
        return new Message(message);
    }
}
