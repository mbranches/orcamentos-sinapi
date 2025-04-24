package com.branches.cpu.model;

public class ItemOrcamento {
    private Long id;
    private Insumo insumo;
    private Integer quantidade;
    private Orcamento orcamento;
    private Double valorTotal;

    public ItemOrcamento() {
    }

    public void setarValorTotal() {
        valorTotal = insumo.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return insumo.getDescricao();
    }

    public Long getId() {
        return this.id;
    }

    public Insumo getInsumo() {
        return this.insumo;
    }

    public Integer getQuantidade() {
        return this.quantidade;
    }

    public Orcamento getOrcamento() {
        return this.orcamento;
    }

    public Double getValorTotal() {
        return this.valorTotal;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ItemOrcamento)) return false;
        final ItemOrcamento other = (ItemOrcamento) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ItemOrcamento;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        return result;
    }
}
