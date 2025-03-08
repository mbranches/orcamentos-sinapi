package com.branches.model;

import jakarta.persistence.*;

@Entity
public class Composicao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    @Column(name = "descricao_composicao")
    private String descricao;
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    @Column(name = "origem_preco")
    private String origemPreco;
    private Double preco;
}
