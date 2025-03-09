package com.branches.controller;

import com.branches.model.Insumo;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController("v1/insumos")
public class InsumoController {
    private final InsumoService service;

    @GetMapping
    public List<Insumo> findAll(@RequestParam String description) {
        return description == null ? service.findAll() : service.findByDescription(description);
    }

    @PostMapping
    public Insumo save(@RequestBody Insumo insumo){
        return service.save(insumo);
    }
}
