package com.branches.controller;

import com.branches.request.BudgetItemPostRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.service.BudgetItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("v1/items")
@RestController
public class BudgetItemController {
    private final BudgetItemService service;

    @GetMapping
    public ResponseEntity<List<BudgetItemGetResponse>> findAll(){
        List<BudgetItemGetResponse> response = service.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<BudgetItemPostResponse> save(@RequestBody BudgetItemPostRequest postRequest) {
        BudgetItemPostResponse response = service.save(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
