package com.branches.mapper;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import com.branches.request.InsumoPostRequest;
import com.branches.request.OrcamentoPostRequest;
import com.branches.request.OrcamentoPutRequest;
import com.branches.response.OrcamentoGetResponse;
import com.branches.response.OrcamentoPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrcamentoMapper {
    OrcamentoMapper INSTANCE = Mappers.getMapper(OrcamentoMapper.class);

    Orcamento toOrcamento(OrcamentoPostRequest orcamentoPostRequest);

    Orcamento toOrcamento(OrcamentoPutRequest orcamentoPutRequest);

    List<OrcamentoGetResponse> toOrcamentoGetResponse(List<Orcamento> orcamentoList);

    OrcamentoPostResponse toOrcamentoPostResponse(Orcamento orcamento);
}
