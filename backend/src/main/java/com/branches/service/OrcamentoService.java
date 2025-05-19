package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import com.branches.request.OrcamentoPutRequest;
import com.branches.response.OrcamentoGetResponse;
import com.branches.response.OrcamentoPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrcamentoService {
    private final OrcamentoMapper mapper;
    private final OrcamentoRepository repository;

    public OrcamentoPostResponse save(OrcamentoPostRequest orcamentoPostRequest) {
        Orcamento orcamento = mapper.toOrcamento(orcamentoPostRequest);
        orcamento.setDataCriacao(LocalDate.now());

        Orcamento response = repository.save(orcamento);

        return mapper.toOrcamentoPostResponse(response);
    }

    public List<OrcamentoGetResponse> findAll(String name) {
        List<Orcamento> response = name == null ? repository.findAll() : repository.findAllByNomeContaining(name);

        return mapper.toOrcamentoGetResponse(response);
    }

    public Orcamento findByIdOrElseThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Orcamento with id '%s' is not found".formatted(id)));
    }

    public void update(OrcamentoPutRequest orcamentoPutRequest) {
        findByIdOrElseThrowNotFoundException(orcamentoPutRequest.getId());

        Orcamento orcamentoToUpdate = mapper.toOrcamento(orcamentoPutRequest);

        repository.save(orcamentoToUpdate);
    }

    public void deleteById(Long orcamentoId) {
        Orcamento orcamentoToDelete = findByIdOrElseThrowNotFoundException(orcamentoId);

        repository.delete(orcamentoToDelete);
    }
}
