package com.exmaple.store.controller;

import com.exmaple.store.config.MessageProperties;
import com.exmaple.store.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class GreetingConfigurationPropertiesController {

    @Autowired
    private MessageProperties properties;

    @GetMapping("/greeting2")
    public Message greeting(@RequestParam(value = "name", defaultValue = "Apple") String name) {
        Objects.requireNonNull(properties.getMessage(), "Greeting message was not set in the properties");

        String message = String.format(properties.getMessage(), name);
        return new Message(message);
    }
}
