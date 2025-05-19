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
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idinsumo")
    private Long id;
    @Column(name = "codigo", nullable = false)
    private Long code;
    @Column(name = "descricao_insumo", length = 500, nullable = false)
    private String description;
    @Column(name = "unidade_medida", length = 10, nullable = false)
    private String unitMeasurement;
    @Column(name = "origem_preco", length = 10, nullable = false)
    private String originPrice;
    @Column(name = "preco", nullable = false)
    private Double price;
}
