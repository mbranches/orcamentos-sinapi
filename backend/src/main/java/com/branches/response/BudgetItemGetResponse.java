package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BudgetItemGetResponse {
    public record SupplyByBudgetItemGetResponse(Long id, Long code, String description, String unitMeasurement, String originPrice, Double price){}
    public record BudgetByBudgetItemGetResponse(Long id, String description, Double totalValue){}

    private Long id;
    private SupplyByBudgetItemGetResponse supply;
    private Integer quantity;
    private BudgetByBudgetItemGetResponse budget;
    private Double totalValue;
}
