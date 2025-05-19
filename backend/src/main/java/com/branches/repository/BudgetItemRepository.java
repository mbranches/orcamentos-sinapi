package com.branches.repository;

import com.branches.model.BudgetItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetItemRepository extends JpaRepository<BudgetItem, Long> {

    List<BudgetItem> findAllByBudgetId(Long budgetId);

    Optional<BudgetItem> findBySupply_IdAndBudget_Id(Long supplyId, Long budgetId);
}
