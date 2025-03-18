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
    private final OrcamentoService ORCAMENTO_SERVICE;

    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemOrcamentoList) {
        return REPOSITORY.saveAll(itemOrcamentoList);
    }

    public List<ItemOrcamento> findAll() {
        return REPOSITORY.findAll();
    }

    public List<ItemOrcamento> findByOrcamento(Long orcamentoId) {
        ORCAMENTO_SERVICE.findByIdOrElseThrowNotFoundException(orcamentoId);
        return REPOSITORY.findAllByOrcamentoId(orcamentoId);
    }

    public void deleteByOrcamentoId(Long orcamentoId) {
        ORCAMENTO_SERVICE.findByIdOrElseThrowNotFoundException(orcamentoId);
        REPOSITORY.deleteByOrcamentoId(orcamentoId);
    }
}
