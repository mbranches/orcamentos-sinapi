package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Budget;
import com.branches.cpu.model.Client;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.service.ClientService;
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

public class TelaEditarOrcamentoController implements Initializable {
    @FXML
    private Button btnSalvarFechar;
    @FXML
    private ComboBox<Client> cbClients;
    @FXML
    private TextField tfBudgetDescription;
    @FXML
    private Button btnRemoveClient;
    private Budget budgetToEdit;
    private TelaVisualizarOrcamentosController telaVisualizarOrcamentosController;
    private final BudgetService budgetService = new BudgetService();
    private final ClientService clientService = new ClientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarClients();
    }

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        if (!fieldsAreValid()) return;

        budgetToEdit.setDescription(tfBudgetDescription.getText());

        Client selectedClient = cbClients.getSelectionModel().getSelectedItem();
        budgetToEdit.setClient(selectedClient);

        budgetService.update(budgetToEdit);

        telaVisualizarOrcamentosController.carregarOrcamentos();

        fecharPagina(event);
    }

    @FXML
    void removeClient(ActionEvent event) {
        cbClients.getSelectionModel().select(null);
    }

    private boolean fieldsAreValid() {
        if (tfBudgetDescription.getText().isBlank()) {
            Alerta.error("Campo Obrigatório", "O campo \"descrição\" é obrigatório!");
            return false;
        }

        return true;
    }

    private void carregarClients() {
        List<Client> clients = clientService.findAll();

        cbClients.getItems().setAll(clients);
    }

    public void setBudget(Budget budget) {
        this.budgetToEdit = budget;

        atualizarCampos();
    }

    private void atualizarCampos() {
        tfBudgetDescription.setText(budgetToEdit.getDescription());
        cbClients.getSelectionModel().select(budgetToEdit.getClient());
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setTelaVisualizarOrcamentosController(TelaVisualizarOrcamentosController telaVisualizarOrcamentosController) {
        this.telaVisualizarOrcamentosController = telaVisualizarOrcamentosController;
    }
}
