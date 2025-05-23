package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Client;
import com.branches.cpu.model.ClientType;
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
import java.util.ResourceBundle;

public class TelaEditarClienteController implements Initializable {
    @FXML
    private Button btnSalvarFechar;
    @FXML
    private ComboBox<ClientType> cbClientType;
    @FXML
    private TextField tfClientName;
    private TelaVisualizarClientesController telaVisualizarClientesController;
    private Client clientToEdit;
    private final ClientService clientService = new ClientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void salvarFechar(ActionEvent event) {
        if (!fieldsAreValid()) {
            return;
        }
        clientToEdit.setClientType(cbClientType.getSelectionModel().getSelectedItem());
        clientToEdit.setName(tfClientName.getText());

        clientService.update(clientToEdit);

        telaVisualizarClientesController.carregarTodosClients();

        Alerta.informacao(
                "Cliente editado",
                "Cliente editado com sucesso!"
        );

        fecharPagina(event);
    }

    public void atualizarCampos() {
        tfClientName.setText(clientToEdit.getName());
        cbClientType.getItems().setAll(ClientType.values());
        cbClientType.getSelectionModel().select(clientToEdit.getClientType());
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private boolean fieldsAreValid() {
        if (tfClientName.getText().isEmpty()) {
            Alerta.error("Campo Obrigatório", "O campo \"nome\" é obrigatório!");
            return false;
        }

        if (cbClientType.getSelectionModel().getSelectedItem() == null) {
            Alerta.error("Campo Obrigatório", "\"Tipo do cliente\" é obrigatório!");
            return false;
        }

        return true;
    }


    public void setClientToEdit(Client clientToEdit) {
        this.clientToEdit = clientToEdit;
    }

    public void setTelaVisualizarClientesController(TelaVisualizarClientesController telaVisualizarClientesController) {
        this.telaVisualizarClientesController = telaVisualizarClientesController;
    }
}
