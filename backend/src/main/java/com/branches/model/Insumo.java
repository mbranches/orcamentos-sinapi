package com.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "insumo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinsumo")
    private Long id;
    private Long codigo;
    @Column(name = "descricao_insumo", length = 500, nullable = false)
    private String descricao;
    @Column(name = "unidade_medida", length = 10, nullable = false)
    private String unidadeMedida;
    @Column(name = "origem_preco", length = 10, nullable = false)
    private String origemPreco;
    private Double preco;
}
