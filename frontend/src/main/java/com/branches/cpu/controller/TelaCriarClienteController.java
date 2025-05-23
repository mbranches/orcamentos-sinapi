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

public class TelaCriarClienteController implements Initializable {

    @FXML
    private Button btnCriarCliente;
    @FXML
    private ComboBox<ClientType> cbClientType;
    @FXML
    private TextField tfClientName;
    private final ClientService clientService = new ClientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carregarClientTypes();
    }

    @FXML
    void criarCliente(ActionEvent event) {
        if (fieldsAreValid()) {
            Client clientToSave = getClientToSave();

            Client savedCliente = clientService.save(clientToSave);

            if (savedCliente != null) {
                Alerta.informacao(
                        "Cliente cadastrado",
                        "O cliente %s foi cadastrado com sucesso!".formatted(clientToSave.getName())
                ).ifPresent(btn -> fecharPagina(event));
            }
        }
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void carregarClientTypes() {
        cbClientType.getItems().setAll(ClientType.values());
    }

    private Client getClientToSave() {
        Client clientToSave = new Client();
        clientToSave.setName(tfClientName.getText());
        clientToSave.setClientType(cbClientType.getSelectionModel().getSelectedItem());

        return clientToSave;
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
}
