package com.branches.service;

import com.branches.mapper.InsumoMapper;
import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.request.InsumoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoService {
    private final InsumoRepository repository;
    private final InsumoMapper MAPPER;


    public List<Insumo> findAll() {
        return repository.findAll();
    }

    public List<Insumo> findByDescription(String description) {
        return repository.findAllByDescricaoContaining(description);
    }

    public Insumo save(InsumoPostRequest insumoPostRequest) {
        Insumo insumo = MAPPER.toInsumo(insumoPostRequest);
        return repository.save(insumo);
    }
}
