package com.branches.cpu.request;

import com.branches.cpu.model.BudgetItem;

public record BudgetItemPostRequest(Long budgetId, Long supplyId, Integer quantity) {
    public static BudgetItemPostRequest of(BudgetItem budgetItem) {
        return new BudgetItemPostRequest(
                budgetItem.getBudget().getId(),
                budgetItem.getSupply().getId(),
                budgetItem.getQuantity()
        );
    }
}
