package com.branches.model;

import jakarta.persistence.*;

@Entity(name = "item_orcamento")
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
