package com.branches.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "orcamento")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orcamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;
    @Column(length = 50, nullable = false)
    private String nome;
}
