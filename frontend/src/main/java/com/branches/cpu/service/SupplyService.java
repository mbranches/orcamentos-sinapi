package com.branches.cpu.service;

import com.branches.cpu.model.Supply;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SupplyService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/supplies";

    public List<Supply> findAll() {
        ParameterizedTypeReference<List<Supply>> typeReference = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<Supply>> response = restTemplate.exchange(url,HttpMethod.GET, null, typeReference);
        return response.getBody();
    }

    public List<Supply> findByName(String description) {
        String urlForGetByName = url + "?description=" + description;

        ParameterizedTypeReference<List<Supply>> typeReference = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<Supply>> response = restTemplate.exchange(urlForGetByName, HttpMethod.GET, null, typeReference);

        return response.getBody();
    }
}
