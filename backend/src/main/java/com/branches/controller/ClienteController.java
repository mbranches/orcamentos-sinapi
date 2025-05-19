package com.branches.controller;

import com.branches.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/clientes")
@RestController
@RequiredArgsConstructor
public class ClienteController {
    public final ClienteService service;

    @GetMapping
    public ResponseEntity<List<ClienteGetResponse>> findAll() {
        List<ClienteGetResponse> response = service.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteGetResponse> findById(@PathVariable Long id) {
        ClienteGetResponse response = service.findById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ClientePostResponse> save(@RequestBody ClientePostRequest postRequest) {
        ClientePostResponse response = service.save(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}