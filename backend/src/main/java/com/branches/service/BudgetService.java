package com.branches.service;

import com.branches.exception.BadRequestException;
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

        Budget budget = mapper.toBudget(postRequest);
        if (postRequest.getClientId() != null) {
            Client client = clientService.findByIdOrThrowsNotFoundException(postRequest.getClientId());
            budget.setClient(client);
        }
        budget.setTotalValue(0D);

        Budget response = repository.save(budget);

        return mapper.toBudgetPostResponse(response);
    }

    public List<BudgetGetResponse> findAll(String description) {
        Sort sort = Sort.by("createdAt").descending();

        List<Budget> response = description == null ? repository.findAll(sort) : repository.findAllByDescriptionContaining(description, sort);

        return mapper.toBudgetGetResponseList(response);
    }

    public BudgetGetResponse findById(Long id) {
        Budget foundBudget = findByIdOrElseThrowsNotFoundException(id);

        return mapper.toBudgetGetResponse(foundBudget);
    }

    public Budget findByIdOrElseThrowsNotFoundException(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Budget with id '%s' is not found".formatted(id)));
    }

    public void update(Long budgetId, BudgetPutRequest putRequest) {
        if (!budgetId.equals(putRequest.getId())) throw new BadRequestException("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(budgetId, putRequest.getId()));

        Budget budgetNotUpdated = findByIdOrElseThrowsNotFoundException(budgetId);

        Budget budgetToUpdate = mapper.toBudget(putRequest);
        if (putRequest.getClientId() != null) {
            Client client = clientService.findByIdOrThrowsNotFoundException(putRequest.getClientId());
            budgetToUpdate.setClient(client);
        }
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
