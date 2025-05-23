package com.branches.cpu.request;


import com.branches.cpu.model.Budget;
import com.branches.cpu.model.Client;

import java.util.Optional;

public record BudgetPostRequest (String description, Long clientId) {

    public static BudgetPostRequest of(Budget budget) {
        Client client = budget.getClient();
        Long clientId = Optional.ofNullable(client)
                .map(Client::getId)
                .orElse(null);

        return new BudgetPostRequest(budget.getDescription(), clientId);
    }
}
