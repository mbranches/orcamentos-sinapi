package com.branches.controller;

import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.service.ItemOrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("v1/items")
@RestController
public class ItemOrcamentoController {
    private final ItemOrcamentoService SERVICE;

    @GetMapping
    public ResponseEntity<List<ItemOrcamento>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(SERVICE.findAll());
    }

    @PostMapping
    public ResponseEntity<List<ItemOrcamento>> saveAll(@RequestBody List<ItemOrcamentoPostRequest> itemOrcamentoPostRequestList) {
        List<ItemOrcamento> response = SERVICE.saveAll(itemOrcamentoPostRequestList);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
