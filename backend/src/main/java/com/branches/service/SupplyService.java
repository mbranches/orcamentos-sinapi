package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.SupplyMapper;
import com.branches.model.Supply;
import com.branches.repository.SupplyRepository;
import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {
    private final SupplyRepository repository;
    private final SupplyMapper mapper;


    public List<SupplyGetResponse> findAll(String description) {
        List<Supply> response = description == null ? repository.findAll() : repository.findAllByDescriptionContaining(description);

        return mapper.toSupplyGetResponseList(response);
    }

    public List<SupplyPostResponse> saveAll(List<SupplyPostRequest> supplyPostRequestList) {
        List<Supply> suppliesToSave = mapper.toSupplyList(supplyPostRequestList);

        List<Supply> response = repository.saveAll(suppliesToSave);

        return mapper.toSupplyPostResponseList(response);
    }

    public Supply findByIdOrElseThrowNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Supply with id '%s' is not found".formatted(id)));
    }
}
