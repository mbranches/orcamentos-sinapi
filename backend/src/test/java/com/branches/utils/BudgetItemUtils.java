package com.branches.utils;

import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.model.Supply;
import com.branches.request.BudgetItemPostRequest;
import com.branches.request.BudgetItemPutRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemGetResponse.BudgetByBudgetItemGetResponse;
import com.branches.response.BudgetItemGetResponse.SupplyByBudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.response.BudgetItemPostResponse.BudgetByBudgetItemPostResponse;
import com.branches.response.BudgetItemPostResponse.SupplyByBudgetItemPostResponse;

import java.util.ArrayList;
import java.util.List;

public class BudgetItemUtils {
    public static List<BudgetItem> newBudgetItemList() {
        int quantity = 5;

        Budget budget1 = BudgetUtils.newBudgetList().getFirst();
        Supply supply1 = SupplyUtils.newSupplyList().getFirst();
        BudgetItem budgetItem1 = BudgetItem.builder().id(1L).budget(budget1).supply(supply1).quantity(quantity).totalValue(supply1.getPrice() * quantity).build();

        Budget budget2 = BudgetUtils.newBudgetList().get(1);
        Supply supply2 = SupplyUtils.newSupplyList().get(1);
        BudgetItem budgetItem2 = BudgetItem.builder().id(2L).budget(budget2).supply(supply2).quantity(quantity).totalValue(supply2.getPrice() * quantity).build();

        Budget budget3 = BudgetUtils.newBudgetList().getLast();
        Supply supply3 = SupplyUtils.newSupplyList().getLast();
        BudgetItem budgetItem3 = BudgetItem.builder().id(2L).budget(budget3).supply(supply3).quantity(quantity).totalValue(supply3.getPrice() * quantity).build();

        return new ArrayList<>(List.of(budgetItem1, budgetItem2, budgetItem3));
    }

    public static List<BudgetItemGetResponse> newBudgetItemGetResponseList() {
        int quantity = 5;

        Budget budget1 = BudgetUtils.newBudgetList().getFirst();
        BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse1 = BudgetUtils.newBudgetByBudgetItemGetResponseByBudget(budget1);
        Supply supply1 = SupplyUtils.newSupplyList().getFirst();
        SupplyByBudgetItemGetResponse supplyByBudgetItemGetResponse1 = SupplyUtils.newSupplyByBudgetItemGetResponseBySupply(supply1);
        BudgetItemGetResponse budgetItem1 = BudgetItemGetResponse.builder().id(1L).budget(budgetByBudgetItemGetResponse1).supply(supplyByBudgetItemGetResponse1).quantity(quantity).totalValue(supply1.getPrice() * quantity).build();

        Budget budget2 = BudgetUtils.newBudgetList().get(1);
        BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse2 = BudgetUtils.newBudgetByBudgetItemGetResponseByBudget(budget2);
        Supply supply2 = SupplyUtils.newSupplyList().get(1);
        SupplyByBudgetItemGetResponse supplyByBudgetItemGetResponse2 = SupplyUtils.newSupplyByBudgetItemGetResponseBySupply(supply2);
        BudgetItemGetResponse budgetItem2 = BudgetItemGetResponse.builder().id(2L).budget(budgetByBudgetItemGetResponse2).supply(supplyByBudgetItemGetResponse2).quantity(quantity).totalValue(supply2.getPrice() * quantity).build();

        Budget budget3 = BudgetUtils.newBudgetList().getLast();
        BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse3 = BudgetUtils.newBudgetByBudgetItemGetResponseByBudget(budget3);
        Supply supply3 = SupplyUtils.newSupplyList().getLast();
        SupplyByBudgetItemGetResponse supplyByBudgetItemGetResponse3 = SupplyUtils.newSupplyByBudgetItemGetResponseBySupply(supply3);
        BudgetItemGetResponse budgetItem3 = BudgetItemGetResponse.builder().id(2L).budget(budgetByBudgetItemGetResponse3).supply(supplyByBudgetItemGetResponse3).quantity(quantity).totalValue(supply3.getPrice() * quantity).build();

        return new ArrayList<>(List.of(budgetItem1, budgetItem2, budgetItem3));
    }

    public static BudgetItem newBudgetItemToSave() {
        Budget budget = BudgetUtils.newBudgetList().getFirst();
        Supply supply = SupplyUtils.newSupplyList().getLast();

        int quantity = 5;

        return BudgetItem.builder().budget(budget).supply(supply).quantity(quantity).build();
    }

    public static BudgetItem newBudgetItemSaved() {
        BudgetItem budgetItemToSave = newBudgetItemToSave();

        return budgetItemToSave.withId(4L).withTotalValue(budgetItemToSave.getSupply().getPrice() * budgetItemToSave.getQuantity());
    }

    public static BudgetItemPostRequest newBudgetItemPostRequest() {
        Supply supply = SupplyUtils.newSupplyList().getLast();

        int quantity = 5;

        return BudgetItemPostRequest.builder().supplyId(supply.getId()).quantity(quantity).build();
    }

    public static BudgetItemPostResponse newBudgetItemPostResponse() {
        BudgetByBudgetItemPostResponse budget = BudgetUtils.newBudgetByBudgetItemPostResponse();
        SupplyByBudgetItemPostResponse supply = SupplyUtils.newSupplyByBudgetItemPostResponse();

        int quantity = 5;
        double totalValue = supply.price() * quantity;

        return BudgetItemPostResponse.builder().id(4L).budget(budget).supply(supply).quantity(quantity).totalValue(totalValue).build();
    }

    public static BudgetItem newBudgetItemToUpdate() {
        return newBudgetItemList().getFirst().withQuantity(50).withTotalValue(0D);
    }

    public static BudgetItemPutRequest newBudgetItemPutRequest() {
        BudgetItem budgetItemToUpdate = newBudgetItemList().getFirst();

        return BudgetItemPutRequest.builder()
                .id(budgetItemToUpdate.getId())
                .supplyId(budgetItemToUpdate.getSupply().getId())
                .quantity(50)
                .build();
    }

    public static BudgetItemPostRequest newBudgetItemAlreadyRegisteredPostRequest() {
        BudgetItem budgetItemAlreadyRegistered = newBudgetItemList().getFirst();
        Supply supply = budgetItemAlreadyRegistered.getSupply();
        Long supplyId = supply.getId();

        return BudgetItemPostRequest.builder()
                .supplyId(supplyId)
                .quantity(5)
                .build();
    }

    public static BudgetItemPostResponse newBudgetItemAlreadyRegisteredPostResponse() {
        BudgetItem budgetItemAlreadyRegistered = newBudgetItemList().getFirst();

        Budget budget = budgetItemAlreadyRegistered.getBudget();
        Supply supply = budgetItemAlreadyRegistered.getSupply();

        BudgetByBudgetItemPostResponse budgetByBudgetItemPostResponse = new BudgetByBudgetItemPostResponse(
                budget.getId(),
                budget.getDescription(),
                budget.getTotalValue()
        );

        SupplyByBudgetItemPostResponse supplyByBudgetItemPostResponse = new SupplyByBudgetItemPostResponse(
                supply.getId(),
                supply.getCode(),
                supply.getDescription(),
                supply.getUnitMeasurement(),
                supply.getOriginPrice(),
                supply.getPrice()
        );

        int quantity = newBudgetItemAlreadyRegisteredPostRequest().getQuantity() + budgetItemAlreadyRegistered.getQuantity();
        double totalValue = quantity * supply.getPrice();

        return BudgetItemPostResponse.builder()
                .budget(budgetByBudgetItemPostResponse)
                .supply(supplyByBudgetItemPostResponse)
                .quantity(quantity)
                .totalValue(totalValue)
                .build();
    }
}
