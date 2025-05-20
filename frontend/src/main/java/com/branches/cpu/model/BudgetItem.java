package com.branches.cpu.model;

public class BudgetItem {
    private Long id;
    private Supply supply;
    private Integer quantity;
    private Budget budget;
    private Double totalValue;

    public BudgetItem() {
    }

    public void setarValorTotal() {
        totalValue = supply.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return supply.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Budget getOrcamento() {
        return budget;
    }

    public void setOrcamento(Budget budget) {
        this.budget = budget;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
}
