package com.branches.cpu.request;

import com.branches.cpu.model.BudgetItem;

public record BudgetItemPutRequest (Long id, Long supplyId, Integer quantity) {

    public static BudgetItemPutRequest of(BudgetItem budgetItem) {
        return new BudgetItemPutRequest(
                budgetItem.getId(),
                budgetItem.getSupply().getId(),
                budgetItem.getQuantity()
        );
    }
}
