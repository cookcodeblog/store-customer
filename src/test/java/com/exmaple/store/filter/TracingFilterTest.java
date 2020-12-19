package com.exmaple.store.filter;

import org.junit.jupiter.api.Test;

import java.util.Optional;

class TracingFilterTest {

    @Test
    public void test_header_null() {
        String isPreviewTest = null;
        Optional.ofNullable(isPreviewTest).ifPresent(x -> System.out.println(x));
    }

    @Test
    public void test_header_true() {
        String isPreviewTest = "true";
        Optional.ofNullable(isPreviewTest).ifPresent(x -> System.out.println(x));
    }
}