package com.branches.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

@Builder
@Getter
@Setter
@With
public class BudgetItemPostRequest {
    private Long supplyId;
    private Integer quantity;
}