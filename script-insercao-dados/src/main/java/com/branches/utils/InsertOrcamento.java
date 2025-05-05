package com.branches.utils;

import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.model.Orcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.request.OrcamentoPostRequest;
import com.branches.service.InsumoService;
import com.branches.service.ItemOrcamentoService;
import com.branches.service.OrcamentoService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InsertOrcamento {
    private static final Logger log = LogManager.getLogger(InsertOrcamento.class);
    private final OrcamentoService orcamentoService;
    private final ItemOrcamentoService itemOrcamentoService;
    private final InsumoService insumoService;

    public void insertOrcamento() {
        OrcamentoPostRequest orcamentoPostRequest1 = OrcamentoPostRequest.builder()
                .nome("Hotel Transilvânia")
                .nomeCliente("Drácula")
                .dataCriacao(LocalDate.of(2024, 12, 5)).build();
        Orcamento orcamento1 = orcamentoService.save(orcamentoPostRequest1);

        log.info(orcamento1);

        List<Insumo> insumos = insumoService.findAll();
        Insumo insumo1 = insumos.get(3658);
        Insumo insumo2 = insumos.get(1544);
        Insumo insumo3 = insumos.get(2555);
        Insumo insumo4 = insumos.get(1758);
        Insumo insumo5 = insumos.get(2144);
        Insumo insumo6 = insumos.get(97);

        ItemOrcamentoPostRequest item1 = ItemOrcamentoPostRequest.builder()
                .insumo(insumo1)
                .orcamento(orcamento1)
                .quantidade(6)
                .build();

        ItemOrcamentoPostRequest item2 = ItemOrcamentoPostRequest.builder()
                .insumo(insumo2)
                .orcamento(orcamento1)
                .quantidade(61)
                .build();

        ItemOrcamentoPostRequest item3 = ItemOrcamentoPostRequest.builder()
                .insumo(insumo3)
                .orcamento(orcamento1)
                .quantidade(12)
                .build();

        ItemOrcamentoPostRequest item4 = ItemOrcamentoPostRequest.builder()
                .insumo(insumo4)
                .orcamento(orcamento1)
                .quantidade(9)
                .build();

        ItemOrcamentoPostRequest item5 = ItemOrcamentoPostRequest.builder()
                .insumo(insumo5)
                .orcamento(orcamento1)
                .quantidade(23)
                .build();

        ItemOrcamentoPostRequest item6 = ItemOrcamentoPostRequest.builder()
                .insumo(insumo6)
                .orcamento(orcamento1)
                .quantidade(17)
                .build();

        List<ItemOrcamento> itemsSaved1 = itemOrcamentoService.saveAll(List.of(item1, item2, item3, item4, item5, item6));

        log.info(itemsSaved1);
    }
}
