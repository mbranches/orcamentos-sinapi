package com.branches.cpu.service;

import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.model.Orcamento;
import com.branches.cpu.request.ItemOrcamentoPostRequest;
import lombok.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrcamentoService {
    private String url = "http://localhost:8090/v1/items";
    private RestTemplate restTemplate = new RestTemplate();

    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemsOrcamento) {
        ItemOrcamento[] response = restTemplate.postForObject(url, itemsOrcamento, ItemOrcamento[].class);

        return Arrays.asList(response);
    }

    public List<ItemOrcamento> findByOrcamento(Orcamento orcamento) {
        Long orcamentoId = orcamento.getId();
        String urlForGet = url + "/" + orcamentoId;
        ItemOrcamento[] response = restTemplate.getForObject(url, ItemOrcamento[].class);

        return Arrays.asList(response);
    }
}
