package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrcamentoService {
    private OrcamentoMapper mapper;
    private OrcamentoRepository repository;
    private ItemOrcamentoService itemService;

    public OrcamentoService(OrcamentoMapper mapper, OrcamentoRepository repository, @Lazy ItemOrcamentoService itemService) {
        this.mapper = mapper;
        this.repository = repository;
        this.itemService = itemService;
    }

    public Orcamento save(OrcamentoPostRequest orcamentoPostRequest) {
        Orcamento orcamento = mapper.toOrcamento(orcamentoPostRequest);
        orcamento.setDataCriacao(LocalDate.now());
        return repository.save(orcamento);
    }

    public List<Orcamento> findAll() {
        return repository.findAll();
    }

    public Orcamento findByIdOrElseThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orcamento Not Found"));
    }

    public void update(Orcamento orcamento) {
        findByIdOrElseThrowNotFoundException(orcamento.getId());
        repository.save(orcamento);
    }

    @Transactional
    public void delete(Long orcamentoId) {
        findByIdOrElseThrowNotFoundException(orcamentoId);
        itemService.deleteByOrcamentoId(orcamentoId);
        repository.deleteById(orcamentoId);
    }

    public List<Orcamento> findAllByName(String nameOrcamento) {
        return repository.findAllByNomeContaining(nameOrcamento);
    }
}
