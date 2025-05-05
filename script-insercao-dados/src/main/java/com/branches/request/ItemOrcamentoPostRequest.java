package com.branches.request;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemOrcamentoPostRequest {
    private Insumo insumo;
    private Integer quantidade;
    private Orcamento orcamento;
}
