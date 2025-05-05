package com.branches.utils;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import com.branches.response.InsumoGetResponse;
import com.branches.response.InsumoPostResponse;

import java.util.ArrayList;
import java.util.List;

public class InsumoUtils {
    public static List<Insumo> newInsumoList() {
        Insumo insumo1 = Insumo.builder().id(1L).codigo(11L).descricao("Descrição 1").unidadeMedida("m2").origemPreco("XX").preco(25D).build();
        Insumo insumo2 = Insumo.builder().id(2L).codigo(22L).descricao("Descrição 2").unidadeMedida("m2").origemPreco("XX").preco(50D).build();
        Insumo insumo3 = Insumo.builder().id(3L).codigo(33L).descricao("Descrição 3").unidadeMedida("un").origemPreco("YY").preco(80D).build();

        return new ArrayList<>(List.of(insumo1, insumo2, insumo3));
    }

    public static List<InsumoGetResponse> newInsumoGetResponseList() {
        InsumoGetResponse insumo1 = InsumoGetResponse.builder().id(1L).codigo(11L).descricao("Descrição 1").unidadeMedida("m2").origemPreco("XX").preco(25D).build();
        InsumoGetResponse insumo2 = InsumoGetResponse.builder().id(2L).codigo(22L).descricao("Descrição 2").unidadeMedida("m2").origemPreco("XX").preco(50D).build();
        InsumoGetResponse insumo3 = InsumoGetResponse.builder().id(3L).codigo(33L).descricao("Descrição 3").unidadeMedida("un").origemPreco("YY").preco(80D).build();

        return new ArrayList<>(List.of(insumo1, insumo2, insumo3));
    }

    public static Insumo newInsumoSaved() {
        return newInsumoList().get(0);
    }

    public static InsumoPostRequest newInsumoPostRequest() {
        return InsumoPostRequest.builder()
                .codigo(4L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }

    public static Insumo newInsumoToSave() {
        return Insumo.builder()
                .codigo(4L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }

    public static InsumoPostResponse newInsumoPostResponse() {
        return InsumoPostResponse.builder()
                .id(4L)
                .codigo(4L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }
}
