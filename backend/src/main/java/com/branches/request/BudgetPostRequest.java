package com.branches.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BudgetPostRequest {
    private String description;
    private Long clientId;
}