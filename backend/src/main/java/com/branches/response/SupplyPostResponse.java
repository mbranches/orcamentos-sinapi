package com.branches.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SupplyPostResponse {
    private Long id;
    private Long code;
    private String description;
    private String unitMeasurement;
    private String originPrice;
    private Double price;
}
