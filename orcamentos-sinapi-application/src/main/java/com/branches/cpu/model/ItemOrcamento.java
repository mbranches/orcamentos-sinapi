package com.branches.cpu.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ItemOrcamento {
    private Long id;
    private Insumo insumo;
    private Integer quantidade;
    private Orcamento orcamento;
    private Double valorTotal;

    public void setarValorTotal(){
        valorTotal = insumo.getPreco() * quantidade;
    }
}
