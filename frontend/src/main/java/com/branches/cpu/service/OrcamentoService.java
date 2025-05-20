package com.branches.cpu.service;

import com.branches.cpu.model.Budget;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.request.OrcamentoPostRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class OrcamentoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/orcamentos";

    public Budget save(OrcamentoPostRequest orcamentoPostRequest) {
        return restTemplate.postForObject(url, orcamentoPostRequest, Budget.class);
    }

    public List<Budget> findAll() {
        Budget[] arrayBudget = restTemplate.getForObject(url, Budget[].class);
        return Arrays.asList(arrayBudget);
    }

    public List<Budget> findAllByName(String nomeOrcamento) {
        String urlWithFilter = url + "?name=" + nomeOrcamento;

        Budget[] arrayBudget = restTemplate.getForObject(urlWithFilter, Budget[].class);
        return Arrays.asList(arrayBudget);
    }

    public List<BudgetItem> findItems(Budget budget) {
        Long orcamentoId = budget.getId();
        String urlForGet = url + "/" + orcamentoId + "/items";
        BudgetItem[] response = restTemplate.getForObject(urlForGet, BudgetItem[].class);

        return Arrays.asList(response);
    }

    public void update(Budget budget) {
        restTemplate.put(url, budget);
    }

    public void delete(Budget budget) {
        Long idToBeDeleted = budget.getId();
        String urlForDelete = url + "/" + idToBeDeleted;
        restTemplate.delete(urlForDelete);
    }
}
