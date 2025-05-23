package com.branches.cpu.request;

public class BudgetPutRequest {
    private Long id;
    private String description;
    private Long clientId;

    public BudgetPutRequest(Long id, String description, Long clientId) {
        this.id = id;
        this.description = description;
        this.clientId = clientId;
    }

    public BudgetPutRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
