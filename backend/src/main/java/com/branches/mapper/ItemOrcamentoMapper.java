package com.branches.mapper;

import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.response.ItemOrcamentoGetResponse;
import com.branches.response.ItemOrcamentoPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemOrcamentoMapper {
    List<ItemOrcamentoGetResponse> toItemOrcamentoGetResponseList(List<ItemOrcamento> itemOrcamentoList);

    ItemOrcamento toItemOrcamento(ItemOrcamentoPostRequest postRequest);

    ItemOrcamentoPostResponse toItemOrcamentoPostResponse(ItemOrcamento itemOrcamento);
}
