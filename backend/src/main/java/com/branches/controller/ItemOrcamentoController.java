package com.branches.controller;

import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.response.ItemOrcamentoGetResponse;
import com.branches.response.ItemOrcamentoPostResponse;
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
    private final ItemOrcamentoService service;

    @GetMapping
    public ResponseEntity<List<ItemOrcamentoGetResponse>> findAll(){
        List<ItemOrcamentoGetResponse> response = service.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<List<ItemOrcamentoPostResponse>> saveAll(@RequestBody List<ItemOrcamentoPostRequest> itemPostRequestList) {
        List<ItemOrcamentoPostResponse> response = service.saveAll(itemPostRequestList);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
