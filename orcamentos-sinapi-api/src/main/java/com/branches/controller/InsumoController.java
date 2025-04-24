package com.branches.controller;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/insumos")
@RequiredArgsConstructor
@RestController
public class InsumoController {
    private final InsumoService SERVICE;

    @GetMapping
    public ResponseEntity<List<Insumo>> findAll(@RequestParam(required = false) String description) {
        return description == null ? ResponseEntity.ok(SERVICE.findAll()) : ResponseEntity.ok(SERVICE.findByDescription(description));
    }

    @PostMapping
    public ResponseEntity<List<Insumo>> saveAll(@RequestBody List<InsumoPostRequest> insumoPostRequestList){
        List<Insumo> response = SERVICE.saveAll(insumoPostRequestList);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
