package com.branches.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orcamento")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descricao", length = 50, nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "fk_cliente_orcamento")
    private Client client;
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<BudgetItem> items;
    @Column(name = "valor_total", nullable = false)
    private Double totalValue;
    @Column(name = "data_criacao")
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime createdAt;
}
