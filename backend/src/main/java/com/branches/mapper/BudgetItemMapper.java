package com.branches.mapper;

import com.branches.model.BudgetItem;
import com.branches.request.BudgetItemPostRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetItemMapper {
    List<BudgetItemGetResponse> toBudgetItemGetResponseList(List<BudgetItem> budgetItemList);

    BudgetItem toBudgetItem(BudgetItemPostRequest postRequest);

    BudgetItemPostResponse toBudgetItemPostResponse(BudgetItem budgetItem);
}
