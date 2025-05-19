package com.branches.mapper;

import com.branches.model.Budget;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetMapper {
    Budget toBudget(BudgetPostRequest postRequest);

    Budget toBudget(BudgetPutRequest putRequest);

    List<BudgetGetResponse> toBudgetGetResponse(List<Budget> budgetList);

    BudgetPostResponse toBudgetPostResponse(Budget budget);
}
