package com.branches.cpu.service;

import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.request.BudgetItemPutRequest;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class BudgetItemService {
    private final String URL = "http://localhost:8090/v1/items";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<BudgetItem> saveAll(List<BudgetItem> itemsOrcamento) {
        BudgetItem[] response = restTemplate.postForObject(URL, itemsOrcamento, BudgetItem[].class);

        return Arrays.asList(response);
    }

    public void delete(BudgetItem itemToBeDeleted) {
        Long itemToBeDeletedId = itemToBeDeleted.getId();
        String urlForDelete = URL + "/" + itemToBeDeletedId;
        restTemplate.delete(urlForDelete);
    }

    public void deleteAll(List<BudgetItem> itemsToBeDeleted) {
        itemsToBeDeleted.forEach(this::delete);
    }

    public void update(BudgetItem budgetItemToUpdate) {
        String urlForPut = URL + "/" + budgetItemToUpdate.getId();

        BudgetItemPutRequest budgetItemPutRequest = BudgetItemPutRequest.from(budgetItemToUpdate);
        restTemplate.put(urlForPut, budgetItemPutRequest);
    }

}
