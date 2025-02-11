package com.branches.cpu.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ServicoAdicionado {
    private int id;
    private long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private double preco;
    private long quantidade;
    private double valorTotal;

    public void setarValorTotal() {
        this.valorTotal = this.quantidade * this.preco;
    }
}
