package com.branches.cpu.service;

import com.branches.cpu.model.ItemOrcamento;
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

    public List<Orcamento> findAllByName(String nomeOrcamento) {
        String urlWithFilter = url + "?name=" + nomeOrcamento;

        Orcamento[] arrayOrcamento = restTemplate.getForObject(urlWithFilter, Orcamento[].class);
        return Arrays.asList(arrayOrcamento);
    }

    public List<ItemOrcamento> findItems(Orcamento orcamento) {
        Long orcamentoId = orcamento.getId();
        String urlForGet = url + "/" + orcamentoId + "/items";
        ItemOrcamento[] response = restTemplate.getForObject(urlForGet, ItemOrcamento[].class);

        return Arrays.asList(response);
    }

    public void update(Orcamento orcamento) {
        restTemplate.put(url, orcamento);
    }

    public void delete(Orcamento orcamento) {
        Long idToBeDeleted = orcamento.getId();
        String urlForDelete = url + "/" + idToBeDeleted;
        restTemplate.delete(urlForDelete);
    }
}
