package com.branches.cpu.service;

import com.branches.cpu.model.Orcamento;
import org.springframework.web.client.RestTemplate;

public class OrcamentoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/orcamentos";

//    public Orcamento save()
}
