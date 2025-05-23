package com.branches.cpu.service;

import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.request.BudgetItemPostRequest;
import com.branches.cpu.request.BudgetItemPutRequest;
import org.springframework.web.client.RestTemplate;

public class BudgetItemService {
    private final String URL = "http://localhost:8090/v1/items";
    private final RestTemplate restTemplate = new RestTemplate();

    public BudgetItem save(BudgetItem budgetItemToSave) {
        BudgetItemPostRequest postRequest = BudgetItemPostRequest.of(budgetItemToSave);

        return restTemplate.postForObject(URL, postRequest, BudgetItem.class);
    }

    public void delete(BudgetItem itemToBeDeleted) {
        Long itemToBeDeletedId = itemToBeDeleted.getId();
        String urlForDelete = URL + "/" + itemToBeDeletedId;
        restTemplate.delete(urlForDelete);
    }

    public void update(BudgetItem budgetItemToUpdate) {
        String urlForPut = URL + "/" + budgetItemToUpdate.getId();

        BudgetItemPutRequest budgetItemPutRequest = BudgetItemPutRequest.from(budgetItemToUpdate);
        restTemplate.put(urlForPut, budgetItemPutRequest);
    }

}
