package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrcamentoService {
    private final OrcamentoMapper MAPPER;
    private final OrcamentoRepository REPOSITORY;
    private final ItemOrcamentoService ITEM_SERVICE;

    public Orcamento save(OrcamentoPostRequest orcamentoPostRequest) {
        Orcamento orcamento = MAPPER.toOrcamento(orcamentoPostRequest);
        if (orcamento.getDataCriacao() == null) orcamento.setDataCriacao(LocalDate.now());
        return REPOSITORY.save(orcamento);
    }

    public List<Orcamento> findAll() {
        return REPOSITORY.findAll();
    }

    public Orcamento findByIdOrElseThrowNotFoundException(Long id) {
        return REPOSITORY.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orcamento Not Found"));
    }

    public void update(Orcamento orcamento) {
        findByIdOrElseThrowNotFoundException(orcamento.getId());
        REPOSITORY.save(orcamento);
    }

    @Transactional
    public void delete(Long orcamentoId) {
        findByIdOrElseThrowNotFoundException(orcamentoId);
        ITEM_SERVICE.deleteByOrcamentoId(orcamentoId);
        REPOSITORY.deleteById(orcamentoId);
    }

    public List<Orcamento> findAllByName(String nameOrcamento) {
        return REPOSITORY.findAllByNomeContaining(nameOrcamento);
    }
}
