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
public class BudgetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iditem_orcamento")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "orcamentoid", nullable = false)
    private Budget budget;
    @ManyToOne
    @JoinColumn(name = "insumoid", nullable = false)
    private Supply supply;
    @Column(name = "quantidade", nullable = false)
    private Integer quantity;
    @Column(name = "valor_total", nullable = false)
    private Double totalValue;
}
