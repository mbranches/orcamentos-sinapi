package com.branches.service;

import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final RestTemplate restTemplate;

    public List<ItemOrcamento> saveAll(List<ItemOrcamentoPostRequest> postRequestList) {
        ItemOrcamento[] response = restTemplate.postForObject("http://localhost:8090/v1/items", postRequestList, ItemOrcamento[].class);

        return Arrays.asList(response);
    }
}
