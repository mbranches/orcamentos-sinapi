package com.branches.cpu.request;

public class OrcamentoPostRequest {
    private String nome;
    private String nomeCliente;

    OrcamentoPostRequest(String nome, String nomeCliente) {
        this.nome = nome;
        this.nomeCliente = nomeCliente;
    }

    public static OrcamentoPostRequestBuilder builder() {
        return new OrcamentoPostRequestBuilder();
    }

    public String getNome() {
        return this.nome;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public static class OrcamentoPostRequestBuilder {
        private String nome;
        private String nomeCliente;

        OrcamentoPostRequestBuilder() {
        }

        public OrcamentoPostRequestBuilder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public OrcamentoPostRequestBuilder nomeCliente(String nomeCliente) {
            this.nomeCliente = nomeCliente;
            return this;
        }

        public OrcamentoPostRequest build() {
            return new OrcamentoPostRequest(this.nome, this.nomeCliente);
        }

        public String toString() {
            return "OrcamentoPostRequest.OrcamentoPostRequestBuilder(nome=" + this.nome + ", nomeCliente=" + this.nomeCliente + ")";
        }
    }
}
