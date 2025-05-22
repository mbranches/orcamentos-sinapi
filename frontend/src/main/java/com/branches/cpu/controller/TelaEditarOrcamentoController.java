package com.branches.cpu.controller;

import com.branches.cpu.model.Budget;
import com.branches.cpu.service.BudgetService;
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

    private TelaVisualizarOrcamentosController telaVisualizarOrcamentosController;

    private final BudgetService ORCAMENTO_SERVICE = new BudgetService();

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        budgetAEditar.setDescription(tfNomeOrcamento.getText());

        ORCAMENTO_SERVICE.update(budgetAEditar);

        telaVisualizarOrcamentosController.atualizarTabela();

        fecharPagina(event);
    }

    public void setOrcamento(Budget budget) {
        this.budgetAEditar = budget;

        atualizarCampos();
    }

    private void atualizarCampos() {
        tfNomeOrcamento.setText(budgetAEditar.getDescription());
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setTelaOrcamentosController(TelaVisualizarOrcamentosController telaVisualizarOrcamentosController) {
        this.telaVisualizarOrcamentosController = telaVisualizarOrcamentosController;
    }
}
