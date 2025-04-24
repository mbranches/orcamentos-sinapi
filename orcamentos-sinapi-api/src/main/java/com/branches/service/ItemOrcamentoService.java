package com.branches.service;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.ItemOrcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository REPOSITORY;
    private final OrcamentoService ORCAMENTO_SERVICE;
    private final ItemOrcamentoMapper mapper;

    public List<ItemOrcamento> saveAll(List<ItemOrcamentoPostRequest> itemPostRequestList) {
        List<ItemOrcamento> itemsToSave = mapper.toItemOrcamentoList(itemPostRequestList);
        return REPOSITORY.saveAll(itemsToSave);
    }

    public List<ItemOrcamento> findAll() {
        return REPOSITORY.findAll();
    }

    public ItemOrcamento findByIdOrElseThrowsNotFoundException(Long id) {
        return REPOSITORY.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found"));
    }

    public List<ItemOrcamento> findByOrcamento(Long orcamentoId) {
        ORCAMENTO_SERVICE.findByIdOrElseThrowNotFoundException(orcamentoId);
        return REPOSITORY.findAllByOrcamentoId(orcamentoId);
    }

    public void deleteByOrcamentoId(Long orcamentoId) {
        ORCAMENTO_SERVICE.findByIdOrElseThrowNotFoundException(orcamentoId);
        REPOSITORY.deleteByOrcamentoId(orcamentoId);
    }

    public void delete(Long id) {
        REPOSITORY.delete(findByIdOrElseThrowsNotFoundException(id));
    }
}
