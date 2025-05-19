package com.branches.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemOrcamentoPostRequest {
    private Long idInsumo;
    private Integer quantidade;
    private Long idOrcamento;
}