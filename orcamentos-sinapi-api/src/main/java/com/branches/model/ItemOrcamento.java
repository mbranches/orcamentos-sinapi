package com.branches.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "item_orcamento")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;
    private Double quantidade;
    @ManyToOne
    @JoinColumn(name = "orcamento_id")
    private Orcamento orcamento;
}
