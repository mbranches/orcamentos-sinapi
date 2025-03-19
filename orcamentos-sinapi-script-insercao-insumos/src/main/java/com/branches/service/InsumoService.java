package com.branches.service;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Service
@RequiredArgsConstructor
public class InsumoService {
    private final RestTemplate restTemplate;

    public Insumo save(InsumoPostRequest insumoPostRequest) {
        return restTemplate.postForObject("http://localhost:8090/v1/insumos", insumoPostRequest, Insumo.class);
    }
}
