package com.branches.service;

import com.branches.model.ItemOrcamento;
import com.branches.repository.ItemOrcamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository REPOSITORY;

    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemOrcamentoList) {
        return REPOSITORY.saveAll(itemOrcamentoList);
    }

    public List<ItemOrcamento> findAll() {
        return REPOSITORY.findAll();
    }

    public List<ItemOrcamento> findByOrcamento(Long orcamentoId) {
        return REPOSITORY.findAllByOrcamentoId(orcamentoId);
    }

    public void deleteByOrcamentoId(Long orcamentoId) {
        REPOSITORY.deleteByOrcamentoId(orcamentoId);
    }
}
