package com.branches.mapper;

import com.branches.model.Insumo;
import com.branches.model.ItemOrcamento;
import com.branches.request.InsumoPostRequest;
import com.branches.request.ItemOrcamentoPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemOrcamentoMapper {
    ItemOrcamentoMapper INSTANCE = Mappers.getMapper(ItemOrcamentoMapper.class);

    List<ItemOrcamento> toItemOrcamentoList(List<ItemOrcamentoPostRequest> itemOrcamentoPostRequestList);
}
