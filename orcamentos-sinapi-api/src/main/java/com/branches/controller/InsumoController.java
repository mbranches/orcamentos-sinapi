package com.branches.controller;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import com.branches.response.InsumoGetResponse;
import com.branches.service.InsumoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/insumos")
@RequiredArgsConstructor
@RestController
public class InsumoController {
    private final InsumoService service;

    @GetMapping
    public ResponseEntity<List<InsumoGetResponse>> findAll(@RequestParam(required = false) String description) {
        List<InsumoGetResponse> response = service.findAll(description);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<List<Insumo>> saveAll(@RequestBody List<InsumoPostRequest> insumoPostRequestList){
        List<Insumo> response = service.saveAll(insumoPostRequestList);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
