package com.branches.mapper;

import com.branches.model.ItemOrcamento;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.response.ItemOrcamentoGetResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemOrcamentoMapper {
    List<ItemOrcamento> toItemOrcamentoList(List<ItemOrcamentoPostRequest> itemPostRequestList);

    List<ItemOrcamentoGetResponse> toItemOrcamentoGetResponseList(List<ItemOrcamento> itemOrcamentoList);
}
