package com.branches.controller;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.service.ItemOrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("v1/items")
@RestController
public class ItemOrcamentoController {
    private final ItemOrcamentoService service;
    @PostMapping
    public List<ItemOrcamento> saveAll(@RequestBody List<ItemOrcamentoPostRequest> itemOrcamentoPostRequestList) {
        return service.saveAll(itemOrcamentoPostRequestList);
    }
}
