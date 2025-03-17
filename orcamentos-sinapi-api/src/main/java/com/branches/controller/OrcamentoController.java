package com.branches.controller;

import com.branches.model.Orcamento;
import com.branches.request.OrcamentoPostRequest;
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
    private final OrcamentoService SERVICE;

    @GetMapping
    public List<Orcamento> findAll() {
        return SERVICE.findAll();
    }

    @PostMapping
    public ResponseEntity<Orcamento> save(@RequestBody OrcamentoPostRequest orcamentoPostRequest) {
        Orcamento response = SERVICE.save(orcamentoPostRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<Void> updateOrcamento(@RequestBody Orcamento orcamento) {
        SERVICE.update(orcamento);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idOrcamento}")
    public ResponseEntity<Orcamento> deleteOrcamento(@PathVariable Long idOrcamento) {
        SERVICE.delete(idOrcamento);

        return ResponseEntity.noContent().build();
    }
}
