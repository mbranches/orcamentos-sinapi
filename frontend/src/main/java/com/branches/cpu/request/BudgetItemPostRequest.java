package com.branches.cpu.request;

import com.branches.cpu.model.BudgetItem;

public record BudgetItemPostRequest(Long supplyId, Integer quantity) {
    public static BudgetItemPostRequest of(BudgetItem budgetItem) {
        return new BudgetItemPostRequest(
                budgetItem.getSupply().getId(),
                budgetItem.getQuantity()
        );
    }
}
