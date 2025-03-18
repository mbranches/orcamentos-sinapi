package com.branches.cpu.service;

import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.model.Orcamento;
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
        ItemOrcamento[] response = restTemplate.getForObject(urlForGet, ItemOrcamento[].class);

        return Arrays.asList(response);
    }

    public void delete(ItemOrcamento itemToBeDeleted) {
        Long itemToBeDeletedId = itemToBeDeleted.getId();
        String urlForDelete = url + "/" + itemToBeDeletedId;
        restTemplate.delete(urlForDelete);
    }

    public void deleteAll(List<ItemOrcamento> itemsToBeDeleted) {
        itemsToBeDeleted.forEach(this::delete);
    }
}
