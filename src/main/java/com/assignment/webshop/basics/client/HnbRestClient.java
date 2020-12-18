package com.assignment.webshop.basics.client;

import com.assignment.webshop.basics.config.RestClientConfig;
import com.assignment.webshop.basics.model.HnbDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class HnbRestClient {

    @Autowired
    RestClientConfig restClientConfig;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<HnbDTO[]> fetchCurrencyInformation() {

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<List<HnbDTO>> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<HnbDTO[]> response = restTemplate.exchange(restClientConfig.getBaseUri(), HttpMethod.GET, entity, HnbDTO[].class);
            log.info("fetch from hnb api: {}", response.getBody());
            return response;
        } catch (RestClientException ex) {
            log.error("failed to contact HNB API: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
