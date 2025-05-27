package com.branches.request;

import lombok.*;

@Builder
@Data
@With
public class BudgetItemPostRequest {
    private Long supplyId;
    private Integer quantity;
}