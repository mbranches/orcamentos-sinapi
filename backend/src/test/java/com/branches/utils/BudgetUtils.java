package com.branches.utils;

import com.branches.model.Budget;
import com.branches.model.Client;
import com.branches.model.Supply;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.response.BudgetPostResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BudgetUtils {
    public static List<Budget> newBudgetList() {
        Client client = ClientUtils.newClientList().getFirst();

        Budget budget1 = Budget.builder()
                .id(1L)
                .client(client)
                .description("Orçamento 1")
                .totalValue(1400D)
                .createdAt(LocalDateTime.of(2025, 2, 12, 21, 11, 25))
                .build();

        Budget budget2 = Budget.builder()
                .id(2L)
                .client(client)
                .description("Orçamento 2")
                .totalValue(1400D)
                .createdAt(LocalDateTime.of(2025, 2, 11, 20, 25, 40))
                .build();

        Budget budget3 = Budget.builder()
                .id(3L)
                .client(client)
                .description("Orçamento 3")
                .totalValue(1400D)
                .createdAt(LocalDateTime.of(2025, 2, 10, 10, 25, 40))
                .build();

        return new ArrayList<>(List.of(budget1, budget2, budget3));
    }

    public static List<BudgetGetResponse> newBudgetGetResponseList() {
        BudgetGetResponse.ClientByBudgetGetResponse clientByBudgetGetResponse = ClientUtils.newClientByBudgetGetResponse();


        BudgetGetResponse budget1 = BudgetGetResponse.builder()
                .id(1L)
                .description("Orçamento 1")
                .totalValue(1400D)
                .client(clientByBudgetGetResponse)
                .build();

        BudgetGetResponse budget2 = BudgetGetResponse.builder()
                .id(2L)
                .description("Orçamento 2")
                .totalValue(1400D)
                .client(clientByBudgetGetResponse)
                .build();

        BudgetGetResponse budget3 = BudgetGetResponse.builder()
                .id(3L)
                .description("Orçamento 3")
                .totalValue(1400D)
                .client(clientByBudgetGetResponse)
                .build();

        return new ArrayList<>(List.of(budget1, budget2, budget3));
    }

    public static Budget newBudgetToSave() {
        Client client = ClientUtils.newClientList().getFirst();
        
        return Budget.builder()
                .description("Orçamento 4")
                .totalValue(0D)
                .client(client)
                .build();
    }

    public static Budget newBudgetSaved() {
        return newBudgetToSave().withId(4L).withCreatedAt(LocalDateTime.of(2025, 5, 19, 20, 23, 11));
    }

    public static BudgetPostRequest newBudgetPostRequest() {
        Client client = ClientUtils.newClientList().getFirst();
        Long clientId = client.getId();

        return BudgetPostRequest.builder()
                .description("Orçamento 4")
                .clientId(clientId)
                .build();
    }

    public static BudgetPostResponse newBudgetPostResponse() {
        BudgetPostResponse.ClientByBudgetPostResponse clientByBudgetPostResponse = ClientUtils.newClientByBudgetPostResponse();

        return BudgetPostResponse.builder()
                .id(4L)
                .description("Orçamento 4")
                .client(clientByBudgetPostResponse)
                .totalValue(0D)
                .build();
    }

    public static BudgetPutRequest newBudgetPutRequest() {
        Budget budgetNotUpdated = newBudgetList().getFirst();
        Client client = ClientUtils.newClientList().getFirst();

        return BudgetPutRequest.builder()
                .id(budgetNotUpdated.getId())
                .description("Novo name")
                .clientId(client.getId())
                .build();
    }

    public static Budget newBudgetToUpdate() {
        Budget budgetNotUpdated = newBudgetList().getFirst();
        Client client = ClientUtils.newClientList().getFirst();

        return Budget.builder()
                .id(budgetNotUpdated.getId())
                .description("Novo name")
                .client(client)
                .totalValue(budgetNotUpdated.getTotalValue())
                .createdAt(budgetNotUpdated.getCreatedAt())
                .build();
    }

    public static BudgetItemGetResponse.BudgetByBudgetItemGetResponse newBudgetByBudgetItemGetResponseByBudget(Budget budget) {

        return new BudgetItemGetResponse.BudgetByBudgetItemGetResponse(
                budget.getId(),
                budget.getDescription(),
                budget.getTotalValue()
        );
    }

    public static BudgetItemPostResponse.BudgetByBudgetItemPostResponse newBudgetByBudgetItemPostResponse() {
        Budget budget = newBudgetList().getFirst();

        return new BudgetItemPostResponse.BudgetByBudgetItemPostResponse(
                budget.getId(),
                budget.getDescription(),
                budget.getTotalValue()
        );
    }
}
