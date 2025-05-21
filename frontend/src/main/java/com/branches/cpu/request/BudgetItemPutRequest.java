package com.branches.cpu.request;

import com.branches.cpu.model.BudgetItem;

public class BudgetItemPutRequest {
    private Long id;
    private Long supplyId;
    private Long budgetId;
    private Integer quantity;

    public BudgetItemPutRequest(Long id, Long supplyId, Long budgetId, Integer quantity) {
        this.id = id;
        this.supplyId = supplyId;
        this.budgetId = budgetId;
        this.quantity = quantity;
    }

    public static BudgetItemPutRequest from(BudgetItem budgetItem) {
        return new BudgetItemPutRequest(
                budgetItem.getId(),
                budgetItem.getSupply().getId(),
                budgetItem.getBudget().getId(),
                budgetItem.getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
