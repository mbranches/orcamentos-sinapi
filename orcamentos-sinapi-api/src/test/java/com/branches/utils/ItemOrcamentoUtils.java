package com.branches.utils;

import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.model.Orcamento;
import com.branches.request.ItemOrcamentoPostRequest;

import java.util.ArrayList;
import java.util.List;

public class ItemOrcamentoUtils {
    public static List<ItemOrcamento> newItemOrcamentoList() {
        Orcamento orcamento = OrcamentoUtils.newOrcamentoSaved();
        Insumo insumo = InsumoUtils.newInsumoSaved();

        ItemOrcamento item1 = ItemOrcamento.builder()
                .id(1L)
                .insumo(insumo)
                .quantidade(1)
                .orcamento(orcamento)
                .build();

        ItemOrcamento item2 = ItemOrcamento.builder()
                .id(2L)
                .insumo(insumo)
                .quantidade(3)
                .orcamento(orcamento)
                .build();

        ItemOrcamento item3 = ItemOrcamento.builder()
                .id(3L)
                .insumo(insumo)
                .quantidade(89)
                .orcamento(orcamento)
                .build();

        return new ArrayList<>(List.of(item1, item2, item3));
    }

    public static List<ItemOrcamentoPostRequest> newItemPostRequestList() {
        Orcamento orcamento = OrcamentoUtils.newOrcamentoSaved();

        ItemOrcamentoPostRequest item1 = ItemOrcamentoPostRequest.builder()
                .id(1L)
                .insumo(InsumoUtils.newInsumoSaved())
                .quantidade(1)
                .orcamento(orcamento)
                .build();

        ItemOrcamentoPostRequest item2 = ItemOrcamentoPostRequest.builder()
                .id(2L)
                .insumo(InsumoUtils.newInsumoSaved())
                .quantidade(3)
                .orcamento(orcamento)
                .build();

        ItemOrcamentoPostRequest item3 = ItemOrcamentoPostRequest.builder()
                .id(3L)
                .insumo(InsumoUtils.newInsumoSaved())
                .quantidade(89)
                .orcamento(orcamento)
                .build();

        return new ArrayList<>(List.of(item1, item2, item3));
    }
}
