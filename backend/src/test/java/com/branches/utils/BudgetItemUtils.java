package com.branches.utils;

import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.model.Supply;
import com.branches.request.BudgetItemPostRequest;
import com.branches.request.BudgetItemPutRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;

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
        BudgetItemGetResponse.BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse1 = BudgetUtils.newBudgetByBudgetItemGetResponseByBudget(budget1);
        Supply supply1 = SupplyUtils.newSupplyList().getFirst();
        BudgetItemGetResponse.SupplyByBudgetItemGetResponse supplyByBudgetItemGetResponse1 = SupplyUtils.newSupplyByBudgetItemGetResponseBySupply(supply1);
        BudgetItemGetResponse budgetItem1 = BudgetItemGetResponse.builder().id(1L).budget(budgetByBudgetItemGetResponse1).supply(supplyByBudgetItemGetResponse1).quantity(quantity).totalValue(supply1.getPrice() * quantity).build();

        Budget budget2 = BudgetUtils.newBudgetList().get(1);
        BudgetItemGetResponse.BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse2 = BudgetUtils.newBudgetByBudgetItemGetResponseByBudget(budget2);
        Supply supply2 = SupplyUtils.newSupplyList().get(1);
        BudgetItemGetResponse.SupplyByBudgetItemGetResponse supplyByBudgetItemGetResponse2 = SupplyUtils.newSupplyByBudgetItemGetResponseBySupply(supply2);
        BudgetItemGetResponse budgetItem2 = BudgetItemGetResponse.builder().id(2L).budget(budgetByBudgetItemGetResponse2).supply(supplyByBudgetItemGetResponse2).quantity(quantity).totalValue(supply2.getPrice() * quantity).build();

        Budget budget3 = BudgetUtils.newBudgetList().getLast();
        BudgetItemGetResponse.BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse3 = BudgetUtils.newBudgetByBudgetItemGetResponseByBudget(budget3);
        Supply supply3 = SupplyUtils.newSupplyList().getLast();
        BudgetItemGetResponse.SupplyByBudgetItemGetResponse supplyByBudgetItemGetResponse3 = SupplyUtils.newSupplyByBudgetItemGetResponseBySupply(supply3);
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
        Budget budget = BudgetUtils.newBudgetList().getFirst();
        Supply supply = SupplyUtils.newSupplyList().getLast();

        int quantity = 5;

        return BudgetItemPostRequest.builder().budgetId(budget.getId()).supplyId(supply.getId()).quantity(quantity).build();
    }

    public static BudgetItemPostResponse newBudgetItemPostResponse() {
        BudgetItemPostResponse.BudgetByBudgetItemPostResponse budget = BudgetUtils.newBudgetByBudgetItemPostResponse();
        BudgetItemPostResponse.SupplyByBudgetItemPostResponse supply = SupplyUtils.newSupplyByBudgetItemPostResponse();

        int quantity = 5;
        double totalValue = supply.price() * quantity;

        return BudgetItemPostResponse.builder().budget(budget).supply(supply).quantity(quantity).totalValue(totalValue).build();
    }

    public static BudgetItem newBudgetItemToUpdate() {
        return newBudgetItemList().getFirst().withQuantity(50).withTotalValue(0D);
    }

    public static BudgetItemPutRequest newBudgetItemPutRequest() {
        BudgetItem budgetItemToUpdate = newBudgetItemList().getFirst();

        return BudgetItemPutRequest.builder()
                .id(budgetItemToUpdate.getId())
                .budgetId(budgetItemToUpdate.getBudget().getId())
                .supplyId(budgetItemToUpdate.getSupply().getId())
                .quantity(50)
                .build();
    }
}
