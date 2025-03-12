package com.branches.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "item_orcamento")
@Getter
@Setter
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_insumo")
    private Insumo insumo;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "id_orcamento")
    private Orcamento orcamento;
}
