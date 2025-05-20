package com.branches.cpu.service;

import com.branches.cpu.model.Budget;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.request.OrcamentoPostRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class BudgetService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/budgets";

    public Budget save(OrcamentoPostRequest orcamentoPostRequest) {
        return restTemplate.postForObject(url, orcamentoPostRequest, Budget.class);
    }

    public List<Budget> findAll() {
        ParameterizedTypeReference<List<Budget>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Budget>> response = restTemplate.exchange(url, HttpMethod.GET, null,typeReference);
        return response.getBody();
    }

    public List<Budget> findAllByName(String budgetDescription) {
        String urlForGetByName = url + "?description=" + budgetDescription;

        ParameterizedTypeReference<List<Budget>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Budget>> response = restTemplate.exchange(urlForGetByName, HttpMethod.GET, null,typeReference);
        return response.getBody();
    }

    public List<BudgetItem> findItems(Budget budget) {
        Long orcamentoId = budget.getId();
        String urlForGet = url + "/" + orcamentoId + "/items";

        ParameterizedTypeReference<List<BudgetItem>> typeReference = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<BudgetItem>> response = restTemplate.exchange(urlForGet, HttpMethod.GET, null, typeReference);

        return response.getBody();
    }

    public void update(Budget budget) {
        String urlForPut = url + "/" + budget.getId();

        restTemplate.put(urlForPut, budget);
    }

    public void delete(Budget budget) {
        Long idToBeDeleted = budget.getId();
        String urlForDelete = url + "/" + idToBeDeleted;

        restTemplate.delete(urlForDelete);
    }
}
