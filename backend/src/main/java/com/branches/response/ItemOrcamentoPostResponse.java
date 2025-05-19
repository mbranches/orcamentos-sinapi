package com.branches.response;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemOrcamentoPostResponse {
    public record InsumoByItemOrcamentoPostResponse(Long id, Long codigo, String descricao, String unidadeMedida, String origemPreco, Double preco){}
    public record OrcamentoByItemOrcamentoPostResponse(Long id, String descricao, Double valorTotal){}

    private Long id;
    private InsumoByItemOrcamentoPostResponse insumo;
    private Integer quantidade;
    private OrcamentoByItemOrcamentoPostResponse orcamento;
    private Double valorTotal;
}
