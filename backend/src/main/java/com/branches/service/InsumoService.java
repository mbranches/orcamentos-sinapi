package com.branches.service;

import com.branches.mapper.InsumoMapper;
import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.request.InsumoPostRequest;
import com.branches.response.InsumoGetResponse;
import com.branches.response.InsumoPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoService {
    private final InsumoRepository repository;
    private final InsumoMapper mapper;


    public List<InsumoGetResponse> findAll(String description) {
        List<Insumo> response = description == null ? repository.findAll() : repository.findAllByDescricaoContaining(description);

        return mapper.toInsumoGetResponseList(response);
    }

    public List<InsumoPostResponse> saveAll(List<InsumoPostRequest> insumoPostRequestList) {
        List<Insumo> insumosToSave = mapper.toInsumoList(insumoPostRequestList);

        List<Insumo> response = repository.saveAll(insumosToSave);

        return mapper.toInsumoPostResponseList(response);
    }
}
