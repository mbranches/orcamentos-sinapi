package com.branches.cpu.controller;

import com.branches.cpu.model.Budget;
import com.branches.cpu.service.OrcamentoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TelaEditarOrcamentoController {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfNomeCliente;

    @FXML
    private TextField tfNomeOrcamento;

    private Budget budgetAEditar;

    private TelaOrcamentosController telaOrcamentosController;

    private final OrcamentoService ORCAMENTO_SERVICE = new OrcamentoService();

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        budgetAEditar.setDescription(tfNomeOrcamento.getText());
        budgetAEditar.setNomeCliente(tfNomeCliente.getText());

        ORCAMENTO_SERVICE.update(budgetAEditar);

        telaOrcamentosController.atualizarTabela();

        fecharPagina(event);
    }

    public void setOrcamento(Budget budget) {
        this.budgetAEditar = budget;

        atualizarCampos();
    }

    private void atualizarCampos() {
        tfNomeOrcamento.setText(budgetAEditar.getDescription());
        tfNomeCliente.setText(budgetAEditar.getNomeCliente());
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setTelaOrcamentosController(TelaOrcamentosController telaOrcamentosController) {
        this.telaOrcamentosController = telaOrcamentosController;
    }
}
