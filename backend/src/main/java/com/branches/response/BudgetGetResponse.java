package com.branches.response;

import com.branches.model.ClientType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetGetResponse {
    public record ClientByBudgetGetResponse(Long id, String name, ClientType clientType){}

    private Long id;
    private String description;
    private ClientByBudgetGetResponse client;
    private Double totalValue;
}
