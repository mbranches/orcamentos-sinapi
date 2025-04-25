package com.branches.model;

import lombok.Data;

@Data
public class ItemOrcamento {
    private Long id;
    private Insumo insumo;
    private Integer quantidade;
    private Orcamento orcamento;
}
