package com.branches.request;

import lombok.*;

@Builder
@Data
@With
public class BudgetPostRequest {
    private String description;
    private Long clientId;
}