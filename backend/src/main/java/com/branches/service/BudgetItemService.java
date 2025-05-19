package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.BudgetItemMapper;
import com.branches.model.BudgetItem;
import com.branches.model.Supply;
import com.branches.model.Budget;
import com.branches.repository.BudgetItemRepository;
import com.branches.request.BudgetItemPostRequest;
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

    @Transactional
    public BudgetItemPostResponse save(BudgetItemPostRequest postRequest) {
        Long budgetId = postRequest.getBudgetId();
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

    private void updatesTotalValue(BudgetItem budgetItem) {
        Double supplyPrice = budgetItem.getSupply().getPrice();
        Integer quantity = budgetItem.getQuantity();

        budgetItem.setTotalValue(supplyPrice * quantity);
    }

    public List<BudgetItemGetResponse> findAll() {
        List<BudgetItem> response = repository.findAll();

        return mapper.toBudgetItemGetResponseList(response);
    }

    public BudgetItem findByIdOrElseThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id '%s' is not found".formatted(id)));
    }

    public List<BudgetItemGetResponse> findByBudgetId(Long budgetId) {
        budgetService.findByIdOrElseThrowsNotFoundException(budgetId);

        List<BudgetItem> response = repository.findAllByBudgetId(budgetId);

        return mapper.toBudgetItemGetResponseList(response);
    }

    public void deleteById(Long id) {
        repository.delete(findByIdOrElseThrowsNotFoundException(id));
    }
}
