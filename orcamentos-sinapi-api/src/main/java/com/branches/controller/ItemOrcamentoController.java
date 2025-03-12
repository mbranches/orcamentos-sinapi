package com.branches.controller;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.service.ItemOrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("v1/items")
public class ItemOrcamentoController {
    private final ItemOrcamentoService service;
    private final ItemOrcamentoMapper MAPPER;
    @PostMapping
    public List<ItemOrcamento> saveAll(@RequestBody List<ItemOrcamentoPostRequest> itemOrcamentoPostRequestList) {
        List<ItemOrcamento> itemOrcamentoList = MAPPER.toItemOrcamentoList(itemOrcamentoPostRequestList);
        return service.saveAll(itemOrcamentoList);
    }
}
