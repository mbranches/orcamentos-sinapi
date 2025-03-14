package com.branches.controller;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/insumos")
@RequiredArgsConstructor
@RestController
public class InsumoController {
    private final InsumoService SERVICE;

    @GetMapping
    public List<Insumo> findAll(@RequestParam(required = false) String description) {
        return description == null ? SERVICE.findAll() : SERVICE.findByDescription(description);
    }

    @PostMapping
    public Insumo save(@RequestBody InsumoPostRequest insumoPostRequest){
        return SERVICE.save(insumoPostRequest);
    }
}
