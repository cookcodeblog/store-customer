package com.exmaple.store.controller;


import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class CustomerController {

    private static final String RESPONSE_STRING_FORMAT = "customer => %s\n";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${preferences.api.url}")
    private String remoteURL;

    @Autowired
    private Tracer tracer;

//    @HystrixCommand(fallbackMethod = "defaultGetCustomer",
//            commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> getCustomer(@RequestHeader("User-Agent") String userAgent, @RequestHeader(value = "user-preference", required = false) String userPreference) {
        try {
            /**
             * Set baggage
             */
            tracer.activeSpan().setBaggageItem("user-agent", userAgent);
            if (userPreference != null && !userPreference.isEmpty()) {
                tracer.activeSpan().setBaggageItem("user-preference", userPreference);
            }


            ResponseEntity<String> responseEntity = restTemplate.getForEntity(remoteURL, String.class);
            String response = responseEntity.getBody();
            return ResponseEntity.ok(String.format(RESPONSE_STRING_FORMAT, response.trim()));
        } catch (HttpStatusCodeException ex) {
            log.warn("Exception trying to get the response from preference service.", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format(RESPONSE_STRING_FORMAT,
                            String.format("%d %s", ex.getRawStatusCode(), createHttpErrorResponseString(ex))));
        } catch (RestClientException ex) {
            log.warn("Exception trying to get the response from preference service.", ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(String.format(RESPONSE_STRING_FORMAT, ex.getMessage()));
        }
    }

    private String createHttpErrorResponseString(HttpStatusCodeException ex) {
        String responseBody = ex.getResponseBodyAsString().trim();
        if (responseBody.startsWith("null")) {
            return ex.getStatusCode().getReasonPhrase();
        }
        return responseBody;
    }

    private ResponseEntity<String> defaultGetCustomer(String userAgent, String userPreference) {
        return ResponseEntity.ok("Fallback: Invoke default getCustomer()...");
    }

    private void setBagge() {

    }

}
