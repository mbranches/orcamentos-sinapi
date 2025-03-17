package com.branches.cpu.service;

import com.branches.cpu.model.Orcamento;
import com.branches.cpu.request.OrcamentoPostRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class OrcamentoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/orcamentos";

    public Orcamento save(OrcamentoPostRequest orcamentoPostRequest) {
        return restTemplate.postForObject(url, orcamentoPostRequest, Orcamento.class);
    }

    public List<Orcamento> findAll() {
        Orcamento[] arrayOrcamento = restTemplate.getForObject(url, Orcamento[].class);
        return Arrays.asList(arrayOrcamento);
    }

    public void update(Orcamento orcamento) {
        restTemplate.put(url, orcamento);
    }
}
