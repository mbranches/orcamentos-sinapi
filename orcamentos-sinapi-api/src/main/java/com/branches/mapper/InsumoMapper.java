package com.branches.mapper;

import com.branches.model.Insumo;
import com.branches.request.InsumoPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InsumoMapper {
    InsumoMapper INSTANCE = Mappers.getMapper(InsumoMapper.class);

    Insumo toInsumo(InsumoPostRequest insumoPostRequest);

    List<Insumo> toInsumoList(List<InsumoPostRequest> insumoPostRequestList);
}
