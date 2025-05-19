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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository repository;
    private final OrcamentoService orcamentoService;
    private final InsumoService insumoService;
    private final ItemOrcamentoMapper mapper;

    @Transactional
    public ItemOrcamentoPostResponse save(ItemOrcamentoPostRequest postRequest) {
        Long orcamentoId = postRequest.getIdOrcamento();
        Long insumoId = postRequest.getIdInsumo();

        Orcamento orcamento = orcamentoService.findByIdOrElseThrowNotFoundException(orcamentoId);
        Insumo insumo = insumoService.findByIdOrElseThrowNotFoundException(insumoId);

        Optional<ItemOrcamento> optionalItemOrcamentoAlreadySaved = repository.findByInsumo_IdAndOrcamento_Id(insumoId, orcamentoId);

        ItemOrcamento itemOrcamentoToSave = mapper.toItemOrcamento(postRequest);
        optionalItemOrcamentoAlreadySaved.ifPresent( itemOrcamentoAlreadySaved -> {
            itemOrcamentoToSave.setId(itemOrcamentoAlreadySaved.getId());
            itemOrcamentoToSave.setQuantidade(itemOrcamentoAlreadySaved.getQuantidade() + itemOrcamentoToSave.getQuantidade());
        });
        itemOrcamentoToSave.setOrcamento(orcamento);
        itemOrcamentoToSave.setInsumo(insumo);
        atualizaValorTotal(itemOrcamentoToSave);

        ItemOrcamento response = repository.save(itemOrcamentoToSave);

        orcamentoService.atualizarValorTotal(orcamentoId);

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
