package com.branches.utils;

import com.branches.model.Orcamento;
import com.branches.request.OrcamentoPostRequest;
import com.branches.request.OrcamentoPutRequest;
import com.branches.response.OrcamentoGetResponse;

import java.util.ArrayList;
import java.util.List;

public class OrcamentoUtils {
    public static List<Orcamento> newOrcamentoList() {
        Orcamento orcamento1 = Orcamento.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();

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

        return new ArrayList<>(List.of(orcamento1, orcamento2, orcamento3));
    }

    public static List<OrcamentoGetResponse> newOrcamentoGetResponseList() {
        OrcamentoGetResponse orcamento1 = OrcamentoGetResponse.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();

        OrcamentoGetResponse orcamento2 = OrcamentoGetResponse.builder()
                .id(2L)
                .nome("Orcamento 2")
                .nomeCliente("Cliente 2")
                .build();

        OrcamentoGetResponse orcamento3 = OrcamentoGetResponse.builder()
                .id(3L)
                .nome("Orcamento 3")
                .nomeCliente("Cliente 3")
                .build();

        return new ArrayList<>(List.of(orcamento1, orcamento2, orcamento3));
    }

    public static Orcamento newOrcamentoSaved() {
        return Orcamento.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();
    }

    public static Orcamento newOrcamentoToSaved() {
        return Orcamento.builder()
                .id(4L)
                .nome("Orçamento 4")
                .nomeCliente("Cliente 4")
                .build();
    }

    public static OrcamentoPostRequest newOrcamentoPostRequest() {
        return OrcamentoPostRequest.builder()
                .nome("Orçamento 4")
                .nomeCliente("Cliente 4")
                .build();
    }

    public static OrcamentoPutRequest newOrcamentoPutRequest() {
        return OrcamentoPutRequest.builder()
                .id(1L)
                .nome("Novo nome")
                .nomeCliente("Cliente 1")
                .build();
    }
}
