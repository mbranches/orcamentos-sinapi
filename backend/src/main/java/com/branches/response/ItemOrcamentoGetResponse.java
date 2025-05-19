package com.branches.response;

import com.branches.model.Insumo;
import com.branches.model.Orcamento;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemOrcamentoGetResponse {
    public record InsumoByItemOrcamentoGetResponse(Long id, Long codigo, String descricao, String unidadeMedida, String origemPreco, Double preco){}
    public record OrcamentoByItemOrcamentoGetResponse(Long id, String descricao, Double valorTotal){}

    private Long id;
    private InsumoByItemOrcamentoGetResponse insumo;
    private Integer quantidade;
    private OrcamentoByItemOrcamentoGetResponse orcamento;
    private Double valorTotal;
}
