package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Cliente;
import com.branches.model.ItemOrcamento;
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
    private final ClienteService clienteService;

    public OrcamentoPostResponse save(OrcamentoPostRequest orcamentoPostRequest) {
        Cliente cliente = clienteService.findByIdOrThrowsNotFoundException(orcamentoPostRequest.getIdCliente());

        Orcamento orcamento = mapper.toOrcamento(orcamentoPostRequest);
        orcamento.setCliente(cliente);
        orcamento.setValorTotal(0D);

        Orcamento response = repository.save(orcamento);

        return mapper.toOrcamentoPostResponse(response);
    }

    public List<OrcamentoGetResponse> findAll(String name) {
        List<Orcamento> response = name == null ? repository.findAll() : repository.findAllByDescricaoContaining(name);

        return mapper.toOrcamentoGetResponse(response);
    }

    public Orcamento findByIdOrElseThrowNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Orcamento with id '%s' is not found".formatted(id)));
    }

    public void update(OrcamentoPutRequest orcamentoPutRequest) {
        Orcamento orcamentoNotUpdated = findByIdOrElseThrowNotFoundException(orcamentoPutRequest.getId());
        Cliente cliente = clienteService.findByIdOrThrowsNotFoundException(orcamentoPutRequest.getIdCliente());

        Orcamento orcamentoToUpdate = mapper.toOrcamento(orcamentoPutRequest);
        orcamentoToUpdate.setCliente(cliente);
        orcamentoToUpdate.setValorTotal(orcamentoNotUpdated.getValorTotal());

        repository.save(orcamentoToUpdate);
    }

    public void deleteById(Long orcamentoId) {
        Orcamento orcamentoToDelete = findByIdOrElseThrowNotFoundException(orcamentoId);

        repository.delete(orcamentoToDelete);
    }

    public void atualizarValorTotal(Long orcamentoId) {
        Orcamento orcamento = findByIdOrElseThrowNotFoundException(orcamentoId);

        double valorTotalOrcamento = orcamento.getItems().stream().mapToDouble(ItemOrcamento::getValorTotal).sum();

        orcamento.setValorTotal(valorTotalOrcamento);

        repository.save(orcamento);
    }
}
