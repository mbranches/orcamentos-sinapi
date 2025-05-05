package com.branches.response;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemOrcamentoGetResponse {
    private Long id;
    private Insumo insumo;
    private Integer quantidade;
    private Orcamento orcamento;
}
