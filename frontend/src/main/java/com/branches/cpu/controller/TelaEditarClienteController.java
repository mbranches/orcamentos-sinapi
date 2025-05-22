package com.branches.cpu.controller;

import com.branches.cpu.model.Client;
import com.branches.cpu.model.ClientType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void Fechar(ActionEvent event) {

    }

    @FXML
    void salvarFechar(ActionEvent event) {

    }

    public void atualizarCampos() {

    }

    public void setClientToEdit(Client clientToEdit) {
        this.clientToEdit = clientToEdit;
    }

    public void setTelaVisualizarClientesController(TelaVisualizarClientesController telaVisualizarClientesController) {
        this.telaVisualizarClientesController = telaVisualizarClientesController;
    }
}
