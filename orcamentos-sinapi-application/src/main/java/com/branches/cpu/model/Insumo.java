package com.branches.cpu.model;

public class Insumo {
    private long id;
    private long codigo;
    private String descricao;
    private String unidadeMedida;
    private String origemPreco;
    private double preco;

    public Insumo(long id, long codigo, String descricao, String unidadeMedida, String origemPreco, double preco) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.unidadeMedida = unidadeMedida;
        this.origemPreco = origemPreco;
        this.preco = preco;
    }

    public Insumo() {
    }

    public static InsumoBuilder builder() {
        return new InsumoBuilder();
    }

    @Override
    public String toString() {
        return descricao;
    }

    public long getId() {
        return this.id;
    }

    public long getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String getUnidadeMedida() {
        return this.unidadeMedida;
    }

    public String getOrigemPreco() {
        return this.origemPreco;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public void setOrigemPreco(String origemPreco) {
        this.origemPreco = origemPreco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public static class InsumoBuilder {
        private long id;
        private long codigo;
        private String descricao;
        private String unidadeMedida;
        private String origemPreco;
        private double preco;

        InsumoBuilder() {
        }

        public InsumoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public InsumoBuilder codigo(long codigo) {
            this.codigo = codigo;
            return this;
        }

        public InsumoBuilder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public InsumoBuilder unidadeMedida(String unidadeMedida) {
            this.unidadeMedida = unidadeMedida;
            return this;
        }

        public InsumoBuilder origemPreco(String origemPreco) {
            this.origemPreco = origemPreco;
            return this;
        }

        public InsumoBuilder preco(double preco) {
            this.preco = preco;
            return this;
        }

        public Insumo build() {
            return new Insumo(this.id, this.codigo, this.descricao, this.unidadeMedida, this.origemPreco, this.preco);
        }

        public String toString() {
            return "Insumo.InsumoBuilder(id=" + this.id + ", codigo=" + this.codigo + ", descricao=" + this.descricao + ", unidadeMedida=" + this.unidadeMedida + ", origemPreco=" + this.origemPreco + ", preco=" + this.preco + ")";
        }
    }
}
