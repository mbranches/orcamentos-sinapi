package com.branches.utils;

import com.branches.model.Orcamento;

public class OrcamentoCreator {
    public static Orcamento createsOrcamento() {
        return Orcamento.builder()
                .id(1L)
                .nome("Orçamento 1")
                .build();
    }
}
