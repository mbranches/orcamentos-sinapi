package com.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "item_orcamento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iditem_orcamento")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "orcamentoid")
    private Orcamento orcamento;
    @ManyToOne
    @JoinColumn(name = "insumoid")
    private Insumo insumo;
    private Integer quantidade;
    @Column(name = "valor_totao")
    private Double valorTotal;
}
