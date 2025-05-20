package com.branches.cpu.service;

import com.branches.cpu.model.BudgetItem;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ItemOrcamentoService {
    private String url = "http://localhost:8090/v1/items";
    private RestTemplate restTemplate = new RestTemplate();

    public ItemOrcamentoService(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    public ItemOrcamentoService() {
    }

    public static ItemOrcamentoServiceBuilder builder() {
        return new ItemOrcamentoServiceBuilder();
    }

    public List<BudgetItem> saveAll(List<BudgetItem> itemsOrcamento) {
        BudgetItem[] response = restTemplate.postForObject(url, itemsOrcamento, BudgetItem[].class);

        return Arrays.asList(response);
    }

    public void delete(BudgetItem itemToBeDeleted) {
        Long itemToBeDeletedId = itemToBeDeleted.getId();
        String urlForDelete = url + "/" + itemToBeDeletedId;
        restTemplate.delete(urlForDelete);
    }

    public void deleteAll(List<BudgetItem> itemsToBeDeleted) {
        itemsToBeDeleted.forEach(this::delete);
    }

    public String getUrl() {
        return this.url;
    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static class ItemOrcamentoServiceBuilder {
        private String url;
        private RestTemplate restTemplate;

        ItemOrcamentoServiceBuilder() {
        }

        public ItemOrcamentoServiceBuilder url(String url) {
            this.url = url;
            return this;
        }

        public ItemOrcamentoServiceBuilder restTemplate(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
            return this;
        }

        public ItemOrcamentoService build() {
            return new ItemOrcamentoService(this.url, this.restTemplate);
        }

        public String toString() {
            return "ItemOrcamentoService.ItemOrcamentoServiceBuilder(url=" + this.url + ", restTemplate=" + this.restTemplate + ")";
        }
    }
}
