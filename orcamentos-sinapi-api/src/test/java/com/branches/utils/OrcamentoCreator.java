package com.branches.utils;

import com.branches.model.Orcamento;

import java.util.List;

public class OrcamentoCreator {
    public static Orcamento createsOrcamento() {
        return Orcamento.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();
    }

    public static List<Orcamento> createsOrcamentoList() {
        Orcamento orcamento2 = Orcamento.builder()
                .id(2L)
                .nome("Orcamento 2")
                .nomeCliente("Cliente 2")
                .build();

        Orcamento orcamento3 = Orcamento.builder()
                .id(3L)
                .nome("Orcamento 3")
                .nomeCliente("Cliente 3")
                .build();

        return List.of(createsOrcamento(), orcamento2, orcamento3);
    }
}
