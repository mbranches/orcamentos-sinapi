package com.branches.utils;

import com.branches.model.Insumo;

public class CreatesInsumo {
    public static Insumo createsInsumo() {
        return Insumo.builder()
                .codigo(1L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();
    }
}
