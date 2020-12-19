package com.exmaple.store.filter;


import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@Component
public class CustomizedTracingFilter implements Filter {
    @Autowired
    private Tracer tracer;

    private final static String TRACING_HEADER_IS_PREVIEW_TEST = "is-preview-test";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Set baggage
//        Optional.ofNullable(request.getHeader(TRACING_HEADER_IS_PREVIEW_TEST)).ifPresent(x -> tracer.activeSpan().setBaggageItem(TRACING_HEADER_IS_PREVIEW_TEST, x));

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
