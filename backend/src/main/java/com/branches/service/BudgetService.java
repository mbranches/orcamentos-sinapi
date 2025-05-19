package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.BudgetMapper;
import com.branches.model.Budget;
import com.branches.model.Client;
import com.branches.model.BudgetItem;
import com.branches.repository.BudgetRepository;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetMapper mapper;
    private final BudgetRepository repository;
    private final ClientService clientService;

    public BudgetPostResponse save(BudgetPostRequest postRequest) {
        Client client = clientService.findByIdOrThrowsNotFoundException(postRequest.getClientId());

        Budget budget = mapper.toBudget(postRequest);
        budget.setClient(client);
        budget.setTotalValue(0D);

        Budget response = repository.save(budget);

        return mapper.toBudgetPostResponse(response);
    }

    public List<BudgetGetResponse> findAll(String description) {
        Sort sort = Sort.by("createdAt").descending();

        List<Budget> response = description == null ? repository.findAll(sort) : repository.findAllByDescriptionContaining(description, sort);

        return mapper.toBudgetGetResponseList(response);
    }

    public Budget findByIdOrElseThrowsNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Budget with id '%s' is not found".formatted(id)));
    }

    public void update(BudgetPutRequest putRequest) {
        Budget budgetNotUpdated = findByIdOrElseThrowsNotFoundException(putRequest.getId());
        Client client = clientService.findByIdOrThrowsNotFoundException(putRequest.getClientId());

        Budget budgetToUpdate = mapper.toBudget(putRequest);
        budgetToUpdate.setClient(client);
        budgetToUpdate.setTotalValue(budgetNotUpdated.getTotalValue());

        repository.save(budgetToUpdate);
    }

    public void deleteById(Long budgetId) {
        Budget budgetToDelete = findByIdOrElseThrowsNotFoundException(budgetId);

        repository.delete(budgetToDelete);
    }

    public void updatesTotalValue(Long budgetId) {
        Budget budget = findByIdOrElseThrowsNotFoundException(budgetId);

        double budgetTotalValue = budget.getItems().stream().mapToDouble(BudgetItem::getTotalValue).sum();

        budget.setTotalValue(budgetTotalValue);

        repository.save(budget);
    }
}
