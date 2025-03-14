package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrcamentoService {
    private final OrcamentoMapper MAPPER;
    private final OrcamentoRepository REPOSITORY;

    public Orcamento save(OrcamentoPostRequest orcamentoPostRequest) {
        Orcamento orcamento = MAPPER.toOrcamento(orcamentoPostRequest);
        orcamento.setDataCriacao(LocalDate.now());
        return REPOSITORY.save(orcamento);
    }
}
