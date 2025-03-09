package com.branches.model;

import jakarta.persistence.*;
import org.hibernate.mapping.ToOne;

@Entity(name = "item_orcamento")
public class ItemOrcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_composicao")
    private Composicao composicao;
    private Integer quantidade;
    @ManyToOne
    @JoinColumn(name = "id_orcamento")
    private Orcamento orcamento;
}
