package com.branches.utils;

import com.branches.model.Supply;
import com.branches.request.SupplyPostRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;

import java.util.ArrayList;
import java.util.List;

public class SupplyUtils {
    public static List<Supply> newSupplyList() {
        Supply supply1 = Supply.builder().id(1L).code(11L).description("Descrição 1").unitMeasurement("m2").originPrice("XX").price(25D).build();
        Supply supply2 = Supply.builder().id(2L).code(22L).description("Descrição 2").unitMeasurement("m2").originPrice("XX").price(50D).build();
        Supply supply3 = Supply.builder().id(3L).code(33L).description("Descrição 3").unitMeasurement("un").originPrice("YY").price(80D).build();

        return new ArrayList<>(List.of(supply1, supply2, supply3));
    }

    public static List<SupplyGetResponse> newSupplyGetResponseList() {
        SupplyGetResponse supply1 = SupplyGetResponse.builder().id(1L).code(11L).description("Descrição 1").unitMeasurement("m2").originPrice("XX").price(25D).build();
        SupplyGetResponse supply2 = SupplyGetResponse.builder().id(2L).code(22L).description("Descrição 2").unitMeasurement("m2").originPrice("XX").price(50D).build();
        SupplyGetResponse supply3 = SupplyGetResponse.builder().id(3L).code(33L).description("Descrição 3").unitMeasurement("un").originPrice("YY").price(80D).build();

        return new ArrayList<>(List.of(supply1, supply2, supply3));
    }

    public static Supply newSupplyToSave() {
        return Supply.builder()
                .code(44L)
                .description("Areia")
                .unitMeasurement("Kg")
                .originPrice("XX")
                .price(20D)
                .build();
    }

    public static Supply newSupplySaved() {
        return newSupplyToSave().withId(4L);
    }

    public static SupplyPostRequest newSupplyPostRequest() {
        return SupplyPostRequest.builder()
                .code(44L)
                .description("Areia")
                .unitMeasurement("Kg")
                .originPrice("XX")
                .price(20D)
                .build();
    }

    public static SupplyPostResponse newSupplyPostResponse() {
        return SupplyPostResponse.builder()
                .id(4L)
                .code(44L)
                .description("Areia")
                .unitMeasurement("Kg")
                .originPrice("XX")
                .price(20D)
                .build();
    }

    public static BudgetItemGetResponse.SupplyByBudgetItemGetResponse newSupplyByBudgetItemGetResponseBySupply(Supply supply) {
        return new BudgetItemGetResponse.SupplyByBudgetItemGetResponse(
                supply.getId(),
                supply.getCode(),
                supply.getDescription(),
                supply.getUnitMeasurement(),
                supply.getOriginPrice(),
                supply.getPrice()
        );
    }

    public static BudgetItemPostResponse.SupplyByBudgetItemPostResponse newSupplyByBudgetItemPostResponse() {
        Supply supply = newSupplyList().getLast();
        return new BudgetItemPostResponse.SupplyByBudgetItemPostResponse(
                supply.getId(),
                supply.getCode(),
                supply.getDescription(),
                supply.getUnitMeasurement(),
                supply.getOriginPrice(),
                supply.getPrice()
        );
    }
}
