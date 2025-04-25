package com.branches.service;

import com.branches.model.Orcamento;
import com.branches.request.OrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OrcamentoService {
    private final RestTemplate restTemplate;

    public Orcamento save(OrcamentoPostRequest orcamentoPostRequest) {
        return restTemplate.postForObject("http://localhost:8090/v1/orcamentos", orcamentoPostRequest, Orcamento.class);
    }
}
