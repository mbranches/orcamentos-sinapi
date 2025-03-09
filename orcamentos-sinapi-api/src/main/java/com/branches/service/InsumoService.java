package com.branches.service;

import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoService {
    private final InsumoRepository repository;

    public List<Insumo> findAll() {
        return repository.findAll();
    }

    public List<Insumo> findByDescription(String description) {
        return repository.findAllByDescricaoContaining(description);
    }

    public Insumo save(Insumo insumo) {
        return repository.save(insumo);
    }
}
