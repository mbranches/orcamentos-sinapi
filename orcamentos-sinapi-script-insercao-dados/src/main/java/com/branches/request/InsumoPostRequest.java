package com.branches.request;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InsumoPostRequest {
    private Long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private Double preco;
}
