package com.exmaple.store.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "preference")
public interface PreferenceFeignClient {

    @GetMapping("/")
    ResponseEntity<String> getPreferences();
}
