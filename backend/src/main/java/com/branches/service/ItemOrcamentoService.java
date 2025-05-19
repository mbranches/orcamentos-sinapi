package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.model.Orcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.response.ItemOrcamentoGetResponse;
import com.branches.response.ItemOrcamentoPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository repository;
    private final OrcamentoService orcamentoService;
    private final InsumoService insumoService;
    private final ItemOrcamentoMapper mapper;

    @Transactional
    public ItemOrcamentoPostResponse save(ItemOrcamentoPostRequest postRequest) {
        Long idOrcamento = postRequest.getIdOrcamento();

        Orcamento orcamento = orcamentoService.findByIdOrElseThrowNotFoundException(idOrcamento);
        Insumo insumo = insumoService.findByIdOrElseThrowNotFoundException(postRequest.getIdInsumo());

        ItemOrcamento itemOrcamento = mapper.toItemOrcamento(postRequest);
        itemOrcamento.setOrcamento(orcamento);
        itemOrcamento.setInsumo(insumo);
        atualizaValorTotal(itemOrcamento);

        ItemOrcamento response = repository.save(itemOrcamento);

        orcamentoService.atualizarValorTotal(idOrcamento);

        return mapper.toItemOrcamentoPostResponse(response);
    }

    private void atualizaValorTotal(ItemOrcamento itemOrcamento) {
        Double precoInsumo = itemOrcamento.getInsumo().getPreco();
        Integer quantidade = itemOrcamento.getQuantidade();

        itemOrcamento.setValorTotal(precoInsumo * quantidade);
    }

    public List<ItemOrcamentoGetResponse> findAll() {
        List<ItemOrcamento> response = repository.findAll();

        return mapper.toItemOrcamentoGetResponseList(response);
    }

    public ItemOrcamento findByIdOrElseThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id '%s' is not found".formatted(id)));
    }

    public List<ItemOrcamentoGetResponse> findByOrcamento(Long orcamentoId) {
        orcamentoService.findByIdOrElseThrowNotFoundException(orcamentoId);

        List<ItemOrcamento> response = repository.findAllByOrcamentoId(orcamentoId);

        return mapper.toItemOrcamentoGetResponseList(response);
    }

    public void delete(Long id) {
        repository.delete(findByIdOrElseThrowsNotFoundException(id));
    }
}
