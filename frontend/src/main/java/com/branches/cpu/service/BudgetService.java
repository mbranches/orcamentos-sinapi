package com.branches.cpu.service;

import com.branches.cpu.model.Budget;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.model.Client;
import com.branches.cpu.request.BudgetPutRequest;
import com.branches.cpu.request.OrcamentoPostRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class BudgetService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL = "http://localhost:8090/v1/budgets";

    public Budget save(OrcamentoPostRequest orcamentoPostRequest) {
        return restTemplate.postForObject(URL, orcamentoPostRequest, Budget.class);
    }

    public List<Budget> findAll() {
        ParameterizedTypeReference<List<Budget>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Budget>> response = restTemplate.exchange(URL, HttpMethod.GET, null,typeReference);
        return response.getBody();
    }

    public List<Budget> findAllByName(String budgetDescription) {
        String urlForGetByName = URL + "?description=" + budgetDescription;

        ParameterizedTypeReference<List<Budget>> typeReference = new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Budget>> response = restTemplate.exchange(urlForGetByName, HttpMethod.GET, null,typeReference);
        return response.getBody();
    }

    public List<BudgetItem> findItems(Budget budget) {
        Long orcamentoId = budget.getId();
        String urlForGet = URL + "/" + orcamentoId + "/items";

        ParameterizedTypeReference<List<BudgetItem>> typeReference = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<BudgetItem>> response = restTemplate.exchange(urlForGet, HttpMethod.GET, null, typeReference);

        return response.getBody();
    }

    public List<BudgetItem> findItemsBySupplyDescription(Budget budget, String descricao) {
        Long budgetId = budget.getId();
        String urlForGet = URL + "/" + budgetId + "/items?supplyDescription=" + descricao;

        ParameterizedTypeReference<List<BudgetItem>> typeReference = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<BudgetItem>> response = restTemplate.exchange(urlForGet, HttpMethod.GET, null, typeReference);

        return response.getBody();
    }

    public void update(Budget budgetToUpdate) {
        String urlForPut = URL + "/" + budgetToUpdate.getId();

        BudgetPutRequest putRequest = new BudgetPutRequest();
        putRequest.setId(budgetToUpdate.getId());
        putRequest.setDescription(budgetToUpdate.getDescription());
        Client client = budgetToUpdate.getClient();
        if (client != null) putRequest.setClientId(client.getId());

        restTemplate.put(urlForPut, putRequest);
    }

    public void delete(Budget budget) {
        Long idToBeDeleted = budget.getId();
        String urlForDelete = URL + "/" + idToBeDeleted;

        restTemplate.delete(urlForDelete);
    }

    public Budget findById(Long id) {
        String urlForGet = URL + "/" + id;
        return restTemplate.getForObject(urlForGet, Budget.class);
    }
}
