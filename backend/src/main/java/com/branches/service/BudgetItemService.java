package com.branches.service;

import com.branches.exception.BadRequestException;
import com.branches.exception.NotFoundException;
import com.branches.mapper.BudgetItemMapper;
import com.branches.model.BudgetItem;
import com.branches.model.Supply;
import com.branches.model.Budget;
import com.branches.repository.BudgetItemRepository;
import com.branches.request.BudgetItemPostRequest;
import com.branches.request.BudgetItemPutRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetItemService {
    private final BudgetItemRepository repository;
    private final BudgetService budgetService;
    private final SupplyService supplyService;
    private final BudgetItemMapper mapper;

    public List<BudgetItemGetResponse> findAll() {
        List<BudgetItem> response = repository.findAll();

        return mapper.toBudgetItemGetResponseList(response);
    }

    public BudgetItem findByIdOrElseThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id '%s' is not found".formatted(id)));
    }

    public List<BudgetItemGetResponse> findAllByBudgetId(Long budgetId, String supplyDescription) {
        budgetService.findByIdOrElseThrowsNotFoundException(budgetId);

        List<BudgetItem> response = supplyDescription == null ? repository.findAllByBudgetId(budgetId) : repository.findAllByBudget_IdAndSupply_DescriptionContaining(budgetId, supplyDescription);

        return mapper.toBudgetItemGetResponseList(response);
    }

    @Transactional
    public BudgetItemPostResponse save(Long budgetId, BudgetItemPostRequest postRequest) {
        Long supplyId = postRequest.getSupplyId();

        Budget budget = budgetService.findByIdOrElseThrowsNotFoundException(budgetId);
        Supply supply = supplyService.findByIdOrElseThrowNotFoundException(supplyId);

        Optional<BudgetItem> optionalBudgetItemAlreadySaved = repository.findBySupply_IdAndBudget_Id(supplyId, budgetId);

        BudgetItem budgetItemToSave = mapper.toBudgetItem(postRequest);
        optionalBudgetItemAlreadySaved.ifPresent( budgetItemAlreadySaved -> {
            budgetItemToSave.setId(budgetItemAlreadySaved.getId());
            budgetItemToSave.setQuantity(budgetItemAlreadySaved.getQuantity() + budgetItemToSave.getQuantity());
        });
        budgetItemToSave.setBudget(budget);
        budgetItemToSave.setSupply(supply);
        updatesTotalValue(budgetItemToSave);

        BudgetItem response = repository.save(budgetItemToSave);

        budgetService.updatesTotalValue(budgetId);

        return mapper.toBudgetItemPostResponse(response);
    }

    @Transactional
    public void update(Long budgetId, Long budgetItemId, BudgetItemPutRequest putRequest) {
        if (!budgetItemId.equals(putRequest.getId())) throw new BadRequestException("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(budgetItemId, putRequest.getId()));

        Budget budget = budgetService.findByIdOrElseThrowsNotFoundException(budgetId);

        findByIdOrElseThrowsNotFoundException(budgetItemId);

        Supply supply = supplyService.findByIdOrElseThrowNotFoundException(putRequest.getSupplyId());

        BudgetItem budgetItemToUpdate = mapper.toBudgetItem(putRequest);
        budgetItemToUpdate.setBudget(budget);
        budgetItemToUpdate.setSupply(supply);
        updatesTotalValue(budgetItemToUpdate);

        repository.save(budgetItemToUpdate);

        budgetService.updatesTotalValue(budgetItemToUpdate.getBudget().getId());
    }

    private void updatesTotalValue(BudgetItem budgetItem) {
        Double supplyPrice = budgetItem.getSupply().getPrice();
        Integer quantity = budgetItem.getQuantity();

        budgetItem.setTotalValue(supplyPrice * quantity);
    }

    public void deleteById(Long budgetId, Long id) {
        Budget budget = budgetService.findByIdOrElseThrowsNotFoundException(budgetId);

        BudgetItem budgetItemToDelete = findByIdOrElseThrowsNotFoundException(id);
        
        repository.delete(budgetItemToDelete);

        budgetService.updatesTotalValue(budget.getId());
    }
}
