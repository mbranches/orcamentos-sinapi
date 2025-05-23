package com.branches.response;

import com.branches.model.ClientType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetPostResponse {
    public record ClientByBudgetPostResponse(Long id, String name, ClientType clientType){}

    private Long id;
    private String description;
    private ClientByBudgetPostResponse client;
    private Double totalValue;
}
