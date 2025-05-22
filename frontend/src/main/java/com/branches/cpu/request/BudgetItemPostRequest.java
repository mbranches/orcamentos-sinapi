package com.branches.cpu.request;

import com.branches.cpu.model.BudgetItem;

public class BudgetItemPostRequest {
    private Long budgetId;
    private Long supplyId;
    private Integer quantity;

    public BudgetItemPostRequest(Long budgetId, Long supplyId, Integer quantity) {
        this.budgetId = budgetId;
        this.supplyId = supplyId;
        this.quantity = quantity;
    }

    public static BudgetItemPostRequest of(BudgetItem budgetItem) {
        return new BudgetItemPostRequest(
                budgetItem.getBudget().getId(),
                budgetItem.getSupply().getId(),
                budgetItem.getQuantity()
        );
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Long supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
