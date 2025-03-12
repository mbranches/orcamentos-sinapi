package com.branches.service;

import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.repository.InsumoRepository;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository repository;

    public List<ItemOrcamento> saveAll(List<ItemOrcamento> itemsOrcamento) {
        return repository.saveAll(itemsOrcamento);
    }
}
