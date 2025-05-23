package com.branches.cpu.controller;

import com.branches.cpu.utils.AbrirFxmlUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class TelaInicialController {
    private final AbrirFxmlUtils abrirFxmlUtils = new AbrirFxmlUtils();
    @FXML
    void abrirTelaCriarOrcamento(ActionEvent event) {
        abrirFxmlUtils.abrirTelaCriarOrcamento("Criar Orçamento");
    }

    @FXML
    void abrirTelaVisualizarBudgets(ActionEvent event) {
        abrirFxmlUtils.abrirTelaVisualizarOrcamentos("Visualizar Orçamentos");
    }

    @FXML
    void abrirTelaVisualizarClients(ActionEvent event) {
        abrirFxmlUtils.abrirTelaVisualizarClientes("Visualizar Clientes");
    }

    @FXML
    void abrirTelaCriarClient(ActionEvent event) {
        abrirFxmlUtils.abrirTelaCriarCliente("Cadastrar Cliente");
    }
}
