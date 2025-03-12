package com.branches.controller;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.request.InsumoPostRequest;
import com.branches.request.OrcamentoPostRequest;
import com.branches.service.OrcamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestMapping("v1/orcamentos")
@RestController
@RequiredArgsConstructor
public class OrcamentoController {
    private final OrcamentoService service;

    @PostMapping
    public Orcamento save(@RequestBody OrcamentoPostRequest orcamentoPostRequest) {
        return service.save(orcamentoPostRequest);
    }
}
