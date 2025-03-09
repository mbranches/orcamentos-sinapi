package com.branches.controller;

import com.branches.model.Composicao;
import com.branches.service.ComposicaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("v1/composicoes")
public class ComposicaoController {
    private final ComposicaoService service;

    @GetMapping
    public List<Composicao> findAll(@RequestParam String description) {
        return description == null ? service.findAll() : service.findByDescription(description);
    }

    @PostMapping
    public Composicao save(@RequestBody Composicao composicao){
        return service.save(composicao);
    }
}
