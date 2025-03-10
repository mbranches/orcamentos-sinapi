package com.branches.controller;

import com.branches.model.Insumo;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/insumos")
@RequiredArgsConstructor
@RestController
public class InsumoController {
    private final InsumoService service;

    @GetMapping
    public List<Insumo> findAll(@RequestParam(required = false) String description) {
        return description == null ? service.findAll() : service.findByDescription(description);
    }

    @PostMapping
    public Insumo save(@RequestBody Insumo insumo){
        return service.save(insumo);
    }
}
