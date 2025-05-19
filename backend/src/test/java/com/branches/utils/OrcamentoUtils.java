package com.branches.utils;

import com.branches.model.Budget;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetPostResponse;

import java.util.ArrayList;
import java.util.List;

public class OrcamentoUtils {
    public static List<Budget> newOrcamentoList() {
        Budget budget1 = Budget.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();

        Budget budget2 = Budget.builder()
                .id(2L)
                .nome("Orcamento 2")
                .nomeCliente("Cliente 2")
                .build();

        Budget budget3 = Budget.builder()
                .id(3L)
                .nome("Orcamento 3")
                .nomeCliente("Cliente 3")
                .build();

        return new ArrayList<>(List.of(budget1, budget2, budget3));
    }

    public static List<BudgetGetResponse> newOrcamentoGetResponseList() {
        BudgetGetResponse orcamento1 = BudgetGetResponse.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();

        BudgetGetResponse orcamento2 = BudgetGetResponse.builder()
                .id(2L)
                .nome("Orcamento 2")
                .nomeCliente("Cliente 2")
                .build();

        BudgetGetResponse orcamento3 = BudgetGetResponse.builder()
                .id(3L)
                .nome("Orcamento 3")
                .nomeCliente("Cliente 3")
                .build();

        return new ArrayList<>(List.of(orcamento1, orcamento2, orcamento3));
    }

    public static Budget newOrcamentoSaved() {
        return Budget.builder()
                .id(1L)
                .nome("Orçamento 1")
                .nomeCliente("Cliente 1")
                .build();
    }

    public static Budget newOrcamentoToSaved() {
        return Budget.builder()
                .id(4L)
                .nome("Orçamento 4")
                .nomeCliente("Cliente 4")
                .build();
    }

    public static BudgetPostRequest newOrcamentoPostRequest() {
        return BudgetPostRequest.builder()
                .nome("Orçamento 4")
                .nomeCliente("Cliente 4")
                .build();
    }

    public static BudgetPutRequest newOrcamentoPutRequest() {
        return BudgetPutRequest.builder()
                .id(1L)
                .nome("Novo name")
                .nomeCliente("Cliente 1")
                .build();
    }

    public static BudgetPostResponse newOrcamentoPostResponse() {
        return BudgetPostResponse.builder()
                .id(4L)
                .nome("Orçamento 4")
                .nomeCliente("Cliente 4")
                .build();
    }
}
