package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetItemPostResponse {
    public record SupplyByBudgetItemPostResponse(Long id, Long code, String description, String unitMeasurement, String originPrice, Double price){}
    public record BudgetByBudgetItemPostResponse(Long id, String description, Double totalValue){}

    private Long id;
    private BudgetByBudgetItemPostResponse budget;
    private SupplyByBudgetItemPostResponse supply;
    private Integer quantity;
    private Double totalValue;
}
