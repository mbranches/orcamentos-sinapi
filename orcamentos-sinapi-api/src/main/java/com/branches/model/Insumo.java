package com.branches.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "insumo")
@Getter
@Setter
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long codigo;
    @Column(name = "descricao_insumo", length = 500, nullable = false)
    private String descricao;
    @Column(name = "unidade_medida", length = 10, nullable = false)
    private String unidadeMedida;
    @Column(name = "origem_preco", length = 10, nullable = false)
    private String origemPreco;
    private Double preco;
}
