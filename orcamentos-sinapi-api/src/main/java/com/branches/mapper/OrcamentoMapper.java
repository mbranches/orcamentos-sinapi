package com.branches.mapper;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import com.branches.request.InsumoPostRequest;
import com.branches.request.OrcamentoPostRequest;
import com.branches.request.OrcamentoPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrcamentoMapper {
    OrcamentoMapper INSTANCE = Mappers.getMapper(OrcamentoMapper.class);

    Orcamento toOrcamento(OrcamentoPostRequest orcamentoPostRequest);

    Orcamento toOrcamento(OrcamentoPutRequest orcamentoPutRequest);
}
