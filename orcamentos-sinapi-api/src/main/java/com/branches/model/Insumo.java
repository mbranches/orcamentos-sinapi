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
    @Column(length = 30)
    private String codigo;
    @Column(name = "descricao_insumo", length = 300, nullable = false)
    private String descricao;
    @Column(name = "unidade_medida", length = 5, nullable = false)
    private String unidadeMedida;
    @Column(name = "origem_preco", length = 5, nullable = false)
    private String origemPreco;
    private Double preco;
}
