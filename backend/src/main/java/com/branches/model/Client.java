package com.branches.model;

import jakarta.persistence.*;
import lombok.*;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cliente")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false)
    private ClientType clientType;
}
