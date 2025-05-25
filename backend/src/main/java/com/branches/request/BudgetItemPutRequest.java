package com.branches.request;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@Builder
@With
public class BudgetItemPutRequest {
    private Long id;
    private Long supplyId;
    private Integer quantity;
}
