package com.branches.cpu.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemOrcamento {
    @EqualsAndHashCode.Include
    private Long id;
    private Insumo insumo;
    private Double quantidade;
    private Orcamento orcamento;
    private Double valorTotal;

    public void setarValorTotal(){
        valorTotal = insumo.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return insumo.getDescricao();
    }
}
