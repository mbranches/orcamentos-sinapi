package com.branches.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orcamento")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
    @OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL)
    private List<ItemOrcamento> items;
    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;
    @Column(name = "data_criacao")
    @CreationTimestamp(source = SourceType.DB)
    private LocalDateTime dataCriacao;
}
