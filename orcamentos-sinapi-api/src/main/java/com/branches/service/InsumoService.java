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
    private final InsumoRepository REPOSITORY;
    private final InsumoMapper MAPPER;


    public List<Insumo> findAll() {
        return REPOSITORY.findAll();
    }

    public List<Insumo> findByDescription(String description) {
        return REPOSITORY.findAllByDescricaoContaining(description);
    }

    public List<Insumo> saveAll(List<InsumoPostRequest> insumoPostRequestList) {
        List<Insumo> insumosToSave = MAPPER.toInsumoList(insumoPostRequestList);
        return REPOSITORY.saveAll(insumosToSave);
    }
}
