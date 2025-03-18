package com.branches.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private String nome;
    @Column(name = "nome_cliente", length = 50, nullable = false)
    private String nomeCliente;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
}
