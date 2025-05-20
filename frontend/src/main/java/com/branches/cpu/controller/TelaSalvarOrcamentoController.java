package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Budget;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.request.OrcamentoPostRequest;
import com.branches.cpu.service.ItemOrcamentoService;
import com.branches.cpu.service.OrcamentoService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class TelaSalvarOrcamentoController {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfNomeOrcamento;

    @FXML
    private TextField tfNomeCliente;

    private TelaOrcamentoController orcamentoController;

    private List<BudgetItem> budgetItemPostRequests;

    private OrcamentoService orcamentoService = new OrcamentoService();

    private ItemOrcamentoService itemOrcamentoService = new ItemOrcamentoService();

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        String nomeOrcamento = tfNomeOrcamento.getText();
        String nomeCliente = tfNomeCliente.getText();

        OrcamentoPostRequest orcamentoPostRequest = OrcamentoPostRequest.builder().nome(nomeOrcamento).nomeCliente(nomeCliente).build();

        Budget budget = orcamentoService.save(orcamentoPostRequest);

        budgetItemPostRequests.forEach(item -> item.setOrcamento(budget));
        orcamentoController.setItemsOrcamento(itemOrcamentoService.saveAll(budgetItemPostRequests));
        orcamentoController.setOrcamento(budget);

        fecharPagina(event);
        Alerta.informacao(tfNomeOrcamento.getText(), "Orçamento salvo com sucesso!");
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setOrcamentoController(TelaOrcamentoController orcamentoController) {
        this.orcamentoController = orcamentoController;
    }

    public void setItemOrcamentoPostRequests(List<BudgetItem> budgetItemPostRequests) {
        this.budgetItemPostRequests = budgetItemPostRequests;
    }
}
