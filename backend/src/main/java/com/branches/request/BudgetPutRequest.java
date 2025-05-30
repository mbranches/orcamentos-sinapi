package com.branches.request;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@With
@Data
@Builder
public class BudgetPutRequest {
    private Long id;
    private String description;
    private Long clientId;
}
