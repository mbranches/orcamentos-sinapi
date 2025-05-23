package com.branches.mapper;

import com.branches.model.Supply;
import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyMapper {
    List<Supply> toSupplyList(List<SupplyPostRequest> postRequestList);

    List<SupplyGetResponse> toSupplyGetResponseList(List<Supply> supplyList);

    List<SupplyPostResponse> toSupplyPostResponseList(List<Supply> supplyList);
}
