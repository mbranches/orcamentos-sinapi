package com.branches.controller;

import com.branches.request.BudgetItemPostRequest;
import com.branches.request.BudgetItemPutRequest;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.response.BudgetPostResponse;
import com.branches.response.BudgetItemGetResponse;
import com.branches.service.BudgetService;
import com.branches.service.BudgetItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/budgets")
@RestController
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService service;
    private final BudgetItemService budgetItemService;

    @GetMapping
    public ResponseEntity<List<BudgetGetResponse>> findAll(@RequestParam(required = false) String description) {
        List<BudgetGetResponse> response = service.findAll(description);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BudgetGetResponse> findById(@PathVariable Long id) {
        BudgetGetResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{budgetId}/items")
    public ResponseEntity<List<BudgetItemGetResponse>> findAllItemsByBudgetId(@PathVariable Long budgetId, @RequestParam(required = false) String supplyDescription) {
        List<BudgetItemGetResponse> response = budgetItemService.findAllByBudgetId(budgetId, supplyDescription);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BudgetPostResponse> save(@RequestBody BudgetPostRequest postRequest) {
        BudgetPostResponse response = service.save(postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{budgetId}/items")
    public ResponseEntity<BudgetItemPostResponse> saveBudgetItem(@PathVariable Long budgetId, @RequestBody BudgetItemPostRequest postRequest) {
        BudgetItemPostResponse response = budgetItemService.save(budgetId, postRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{budgetId}")
    public ResponseEntity<Void> update(@PathVariable Long budgetId, @RequestBody BudgetPutRequest putRequest) {
        service.update(budgetId, putRequest);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{budgetId}/items/{budgetItemId}")
    public ResponseEntity<Void> updateBudgetItem(@PathVariable Long budgetId, @PathVariable Long budgetItemId, @RequestBody BudgetItemPutRequest putRequest) {
        budgetItemService.update(budgetId, budgetItemId, putRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{budgetId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long budgetId) {
        service.deleteById(budgetId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{budgetId}/items/{budgetItemId}")
    public ResponseEntity<Void> deleteBudgetItemById(@PathVariable Long budgetId, @PathVariable Long budgetItemId) {
        budgetItemService.deleteById(budgetId, budgetItemId);

        return ResponseEntity.noContent().build();
    }
}
