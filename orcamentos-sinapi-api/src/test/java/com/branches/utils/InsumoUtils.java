package com.branches.utils;

import com.branches.model.Insumo;

public class InsumoUtils {
    public static Insumo createsInsumo() {
        return Insumo.builder()
                .id(1L)
                .codigo(1L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }
}
