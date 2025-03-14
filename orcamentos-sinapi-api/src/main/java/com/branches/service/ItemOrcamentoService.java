package com.branches.service;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.ItemOrcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository REPOSITORY;
    private final ItemOrcamentoMapper MAPPER;

    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemOrcamentoList) {
        return REPOSITORY.saveAll(itemOrcamentoList);
    }

    public List<ItemOrcamento> findAll() {
        return REPOSITORY.findAll();
    }
}
