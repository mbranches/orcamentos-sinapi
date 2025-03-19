package com.branches.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class InsumoPostRequest {
    private Long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private Double preco;
}
