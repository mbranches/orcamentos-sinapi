package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import com.branches.repository.InsumoRepository;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrcamentoService {
    private final OrcamentoMapper MAPPER;
    private final OrcamentoRepository repository;

    public Orcamento save(OrcamentoPostRequest orcamentoPostRequest) {
        Orcamento orcamento = MAPPER.toOrcamento(orcamentoPostRequest);
        orcamento.setDataCriacao(LocalDate.now());
        return repository.save(orcamento);
    }
}
