package com.branches.utils;

import com.branches.model.Supply;
import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;

import java.util.ArrayList;
import java.util.List;

public class InsumoUtils {
    public static List<Supply> newInsumoList() {
        Supply supply1 = Supply.builder().id(1L).codigo(11L).descricao("Descrição 1").unidadeMedida("m2").origemPreco("XX").preco(25D).build();
        Supply supply2 = Supply.builder().id(2L).codigo(22L).descricao("Descrição 2").unidadeMedida("m2").origemPreco("XX").preco(50D).build();
        Supply supply3 = Supply.builder().id(3L).codigo(33L).descricao("Descrição 3").unidadeMedida("un").origemPreco("YY").preco(80D).build();

        return new ArrayList<>(List.of(supply1, supply2, supply3));
    }

    public static List<SupplyGetResponse> newInsumoGetResponseList() {
        SupplyGetResponse insumo1 = SupplyGetResponse.builder().id(1L).code(11L).description("Descrição 1").unitMeasurement("m2").originPrice("XX").price(25D).build();
        SupplyGetResponse insumo2 = SupplyGetResponse.builder().id(2L).code(22L).description("Descrição 2").unitMeasurement("m2").originPrice("XX").price(50D).build();
        SupplyGetResponse insumo3 = SupplyGetResponse.builder().id(3L).code(33L).description("Descrição 3").unitMeasurement("un").originPrice("YY").price(80D).build();

        return new ArrayList<>(List.of(insumo1, insumo2, insumo3));
    }

    public static Supply newInsumoSaved() {
        return newInsumoList().get(0);
    }

    public static SupplyPostRequest newInsumoPostRequest() {
        return SupplyPostRequest.builder()
                .code(4L)
                .description("Areia")
                .unitMeasurement("Kg")
                .originPrice("XX")
                .price(20D)
                .build();
    }

    public static Supply newInsumoToSave() {
        return Supply.builder()
                .codigo(4L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }

    public static SupplyPostResponse newInsumoPostResponse() {
        return SupplyPostResponse.builder()
                .id(4L)
                .codigo(4L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }
}
