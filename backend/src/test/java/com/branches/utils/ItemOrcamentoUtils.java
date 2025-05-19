package com.branches.utils;

import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.model.Supply;
import com.branches.request.BudgetItemPostRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;

import java.util.ArrayList;
import java.util.List;

public class ItemOrcamentoUtils {
    public static List<BudgetItem> newItemOrcamentoList() {
        Budget budget = OrcamentoUtils.newOrcamentoSaved();
        Supply supply = InsumoUtils.newInsumoSaved();

        BudgetItem item1 = BudgetItem.builder()
                .id(1L)
                .supply(supply)
                .quantidade(1)
                .budget(budget)
                .build();

        BudgetItem item2 = BudgetItem.builder()
                .id(2L)
                .supply(supply)
                .quantidade(3)
                .budget(budget)
                .build();

        BudgetItem item3 = BudgetItem.builder()
                .id(3L)
                .supply(supply)
                .quantidade(89)
                .budget(budget)
                .build();

        return new ArrayList<>(List.of(item1, item2, item3));
    }

    public static List<BudgetItemGetResponse> newItemGetResponseList() {
        Budget budget = OrcamentoUtils.newOrcamentoSaved();
        Supply supply = InsumoUtils.newInsumoSaved();

        BudgetItemGetResponse item1 = BudgetItemGetResponse.builder()
                .id(1L)
                .supply(supply)
                .quantity(1)
                .budget(budget)
                .build();

        BudgetItemGetResponse item2 = BudgetItemGetResponse.builder()
                .id(2L)
                .supply(supply)
                .quantity(3)
                .budget(budget)
                .build();

        BudgetItemGetResponse item3 = BudgetItemGetResponse.builder()
                .id(3L)
                .supply(supply)
                .quantity(89)
                .budget(budget)
                .build();

        return new ArrayList<>(List.of(item1, item2, item3));
    }

    public static List<BudgetItemPostRequest> newItemPostRequestList() {
        Budget budget = OrcamentoUtils.newOrcamentoSaved();

        BudgetItemPostRequest item1 = BudgetItemPostRequest.builder()
                .id(1L)
                .insumo(InsumoUtils.newInsumoSaved())
                .quantidade(1)
                .orcamento(budget)
                .build();

        BudgetItemPostRequest item2 = BudgetItemPostRequest.builder()
                .id(2L)
                .insumo(InsumoUtils.newInsumoSaved())
                .quantidade(3)
                .orcamento(budget)
                .build();

        BudgetItemPostRequest item3 = BudgetItemPostRequest.builder()
                .id(3L)
                .insumo(InsumoUtils.newInsumoSaved())
                .quantidade(89)
                .orcamento(budget)
                .build();

        return new ArrayList<>(List.of(item1, item2, item3));
    }

    public static List<BudgetItemPostResponse> newItemPostResponseList() {
        Budget budget = OrcamentoUtils.newOrcamentoSaved();

        BudgetItemPostResponse item1 = BudgetItemPostResponse.builder()
                .id(1L)
                .supply(InsumoUtils.newInsumoSaved())
                .quantity(1)
                .budget(budget)
                .build();

        BudgetItemPostResponse item2 = BudgetItemPostResponse.builder()
                .id(2L)
                .supply(InsumoUtils.newInsumoSaved())
                .quantity(3)
                .budget(budget)
                .build();

        BudgetItemPostResponse item3 = BudgetItemPostResponse.builder()
                .id(3L)
                .supply(InsumoUtils.newInsumoSaved())
                .quantity(89)
                .budget(budget)
                .build();

        return new ArrayList<>(List.of(item1, item2, item3));
    }
}
