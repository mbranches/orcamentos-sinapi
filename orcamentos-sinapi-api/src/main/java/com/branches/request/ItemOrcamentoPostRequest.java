package com.branches.request;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemOrcamentoPostRequest {
    private Long id;
    private Insumo insumo;
    private Double quantidade;
    private Orcamento orcamento;
}