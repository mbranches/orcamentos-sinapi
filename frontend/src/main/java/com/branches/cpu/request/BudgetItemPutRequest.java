package com.branches.cpu.request;

import com.branches.cpu.model.BudgetItem;

public record BudgetItemPutRequest (Long id, Long supplyId, Long budgetId, Integer quantity) {

    public static BudgetItemPutRequest from(BudgetItem budgetItem) {
        return new BudgetItemPutRequest(
                budgetItem.getId(),
                budgetItem.getSupply().getId(),
                budgetItem.getBudget().getId(),
                budgetItem.getQuantity()
        );
    }
}
