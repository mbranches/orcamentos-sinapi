package com.branches.service;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class InsumoService {
    private final RestTemplate restTemplate;

    public List<Insumo> saveAll(List<InsumoPostRequest> insumoPostRequests) {
        Insumo[] response = restTemplate.postForObject("http://localhost:8090/v1/insumos", insumoPostRequests, Insumo[].class);

        return Arrays.asList(response);
    }
}
