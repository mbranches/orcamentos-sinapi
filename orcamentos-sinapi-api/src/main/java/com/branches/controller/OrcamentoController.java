package com.branches.controller;

import com.branches.model.Orcamento;
import com.branches.request.OrcamentoPostRequest;
import com.branches.request.OrcamentoPutRequest;
import com.branches.response.OrcamentoGetResponse;
import com.branches.response.OrcamentoPostResponse;
import com.branches.service.OrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/orcamentos")
@RestController
@RequiredArgsConstructor
public class OrcamentoController {
    private final OrcamentoService service;

    @GetMapping
    public ResponseEntity<List<OrcamentoGetResponse>> findAll(@RequestParam(required = false) String name) {
        List<OrcamentoGetResponse> response = service.findAll(name);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<OrcamentoPostResponse> save(@RequestBody OrcamentoPostRequest orcamentoPostRequest) {
        OrcamentoPostResponse response = service.save(orcamentoPostRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateOrcamento(@RequestBody OrcamentoPutRequest orcamentoPutRequest) {
        service.update(orcamentoPutRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idOrcamento}")
    public ResponseEntity<Void> deleteOrcamento(@PathVariable Long idOrcamento) {
        service.delete(idOrcamento);

        return ResponseEntity.noContent().build();
    }
}
