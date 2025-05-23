package com.branches.controller;

import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;
import com.branches.service.SupplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/supplies")
@RequiredArgsConstructor
@RestController
public class SupplyController {
    private final SupplyService service;

    @GetMapping
    public ResponseEntity<List<SupplyGetResponse>> findAll(@RequestParam(required = false) String description) {
        List<SupplyGetResponse> response = service.findAll(description);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<List<SupplyPostResponse>> saveAll(@RequestBody List<SupplyPostRequest> postRequestList){
        List<SupplyPostResponse> response = service.saveAll(postRequestList);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
