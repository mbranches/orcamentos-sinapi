package com.branches.cpu.request;


import com.branches.cpu.model.Budget;

public class BudgetPostRequest {
    private String description;
    private Long clientId;

    public BudgetPostRequest() {
    }

    public static BudgetPostRequest of(Budget budget) {
        BudgetPostRequest postRequest = new BudgetPostRequest();
        postRequest.setDescription(budget.getDescription());
        if (budget.getClient() != null) postRequest.setClientId(budget.getClient().getId());

        return postRequest;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
