package com.branches.controller;

import com.branches.mapper.InsumoMapper;
import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/insumos")
@RequiredArgsConstructor
@RestController
public class InsumoController {
    private static final Logger log = LogManager.getLogger(InsumoController.class);
    private final InsumoService service;
    private final InsumoMapper MAPPER;

    @GetMapping
    public List<Insumo> findAll(@RequestParam(required = false) String description) {
        return description == null ? service.findAll() : service.findByDescription(description);
    }

    @PostMapping
    public Insumo save(@RequestBody InsumoPostRequest insumoPostRequest){
        Insumo insumo = MAPPER.toInsumo(insumoPostRequest);
        log.info(insumo.getDescricao());
        return service.save(insumo);
    }
}
