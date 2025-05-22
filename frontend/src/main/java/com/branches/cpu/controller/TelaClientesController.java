package com.branches.cpu.controller;

import com.branches.cpu.model.Budget;
import com.branches.cpu.model.Client;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.service.ClientService;
import com.branches.cpu.utils.TableColumnConfig;
import com.branches.cpu.utils.TableViewProprieties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaClientesController implements Initializable {
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private ImageView imageEditar;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private TableView<Client> tvClientes;
    private Client selectedClient;
    private ClientService clientService = new ClientService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvClientes.setPlaceholder(new Label("Nenhum cliente adicionado até o momento."));
        tvClientes.getPlaceholder().setStyle("-fx-font-size: 15px");

        tvClientes.setFixedCellSize(60);
        tvClientes.setEditable(false);

        criarColunasTabela();

        carregarTodosClients();
    }

    @FXML
    void abrirTelaEditarCliente(ActionEvent event) {
        limparBarraPesquisa();
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        carregarClientsPesquisados();
    }

    @FXML
    void excluirCliente(ActionEvent event) {
        clientService.delete(selectedClient);

        carregarTodosClients();

        desativarBotoes();
    }

    @FXML
    void selecionarCliente(MouseEvent event) {
        selectedClient = tvClientes.getSelectionModel().getSelectedItem();
        if (selectedClient != null) ativarBotoes();
    }

    private void criarColunasTabela() {
        TableColumn<Client, String> colunaName = new TableColumn<>("Cliente");
        TableColumn<Client, Client> colunaClientType = new TableColumn<>("Tipo do Cliente");

        colunaName.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.7));
        colunaClientType.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.29));

        tvClientes.getColumns().addAll(colunaName, colunaClientType);
        TableViewProprieties.noEditableColumns(tvClientes);

        colunaName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colunaClientType.setCellValueFactory(new PropertyValueFactory<>("clientType"));
    }

    private void carregarTodosClients() {
        List<Client> clients = clientService.findAll();

        atualizarTabela(clients);

        limparBarraPesquisa();
    }

    private void carregarClientsPesquisados() {
        String nameToSearch = tfPesquisar.getText();
        List<Client> clients = clientService.findAllByName(nameToSearch);

        atualizarTabela(clients);
    }

    private void atualizarTabela(List<Client> clients) {
        tvClientes.getItems().setAll(clients);
    }

    private void limparBarraPesquisa() {
        tfPesquisar.clear();
    }

    private void ativarBotoes() {
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }
}
