package com.branches.service;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.ItemOrcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.response.ItemOrcamentoGetResponse;
import com.branches.response.ItemOrcamentoPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemOrcamentoService {
    private final ItemOrcamentoRepository repository;
    private final OrcamentoService orcamentoService;
    private final ItemOrcamentoMapper mapper;

    public List<ItemOrcamentoPostResponse> saveAll(List<ItemOrcamentoPostRequest> itemPostRequestList) {
        List<ItemOrcamento> itemsToSave = mapper.toItemOrcamentoList(itemPostRequestList);

        List<ItemOrcamento> response = repository.saveAll(itemsToSave);

        return mapper.toItemOrcamentoPostResponseList(response);
    }

    public List<ItemOrcamentoGetResponse> findAll() {
        List<ItemOrcamento> response = repository.findAll();

        return mapper.toItemOrcamentoGetResponseList(response);
    }

    public ItemOrcamento findByIdOrElseThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item Not Found"));
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
