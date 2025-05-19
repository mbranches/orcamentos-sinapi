package com.branches.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BudgetItemPostRequest {
    private Long supplyId;
    private Integer quantity;
    private Long budgetId;
}