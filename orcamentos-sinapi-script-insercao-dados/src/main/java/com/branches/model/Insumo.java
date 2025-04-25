package com.branches.model;

import lombok.Data;

@Data
public class Insumo {
    private Long id;
    private Long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private Double preco;
}
