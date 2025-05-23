package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Budget;
import com.branches.cpu.model.Client;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.service.ClientService;
import com.branches.cpu.utils.AbrirFxmlUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaCriarOrcamentoController implements Initializable {
    @FXML
    private Button btnRemoveClient;
    @FXML
    private Button btnSalvarFechar;
    @FXML
    private ComboBox<Client> cbClients;
    @FXML
    private TextField tfBudgetDescription;
    private final ClientService clientService = new ClientService();
    private final BudgetService budgetService = new BudgetService();
    private final AbrirFxmlUtils abrirFxmlUtils = new AbrirFxmlUtils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarClients();
    }

    @FXML
    void removeClient(ActionEvent event) {
        cbClients.getSelectionModel().clearSelection();
    }

    @FXML
    void criarBudget(ActionEvent event) {
        if (!fieldsAreValid()) return;

        Budget budgetToSave = new Budget();
        budgetToSave.setDescription(tfBudgetDescription.getText());
        budgetToSave.setClient(cbClients.getSelectionModel().getSelectedItem());

        Budget savedBudget = budgetService.save(budgetToSave);

        abrirFxmlUtils.abrirTelaOrcamento(savedBudget.getDescription(), savedBudget);

        fecharPagina(event);
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void carregarClients() {
        List<Client> clients = clientService.findAll();

        cbClients.getItems().setAll(clients);
    }

    private boolean fieldsAreValid() {
        if (tfBudgetDescription.getText().isBlank()) {
            Alerta.error("Campo Obrigatório", "O campo \"descrição\" é obrigatório!");

            return false;
        }

        return true;
    }
}
