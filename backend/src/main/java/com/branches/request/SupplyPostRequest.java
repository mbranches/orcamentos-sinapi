package com.branches.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class SupplyPostRequest {
    private Long code;
    private String description;
    private String unitMeasurement;
    private String originPrice;
    private Double price;
}