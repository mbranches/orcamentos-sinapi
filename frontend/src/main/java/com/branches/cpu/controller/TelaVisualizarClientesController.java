package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Client;
import com.branches.cpu.service.ClientService;
import com.branches.cpu.utils.AbrirFxmlUtils;
import com.branches.cpu.utils.TableViewUtils;
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

public class TelaVisualizarClientesController implements Initializable {
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
    private final ClientService clientService = new ClientService();
    private final AbrirFxmlUtils abrirFxmlUtils = new AbrirFxmlUtils();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tvClientes.setFixedCellSize(60);
        tvClientes.setEditable(false);

        criarColunasTabela();

        carregarTodosClients();
    }

    @FXML
    void abrirTelaEditarCliente(ActionEvent event) {
        abrirFxmlUtils.abrirTelaEditarCliente("Editar Cliente", this, selectedClient);

        removerSelecao();
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        carregarClientsPesquisados();
    }

    @FXML
    void excluirCliente(ActionEvent event) {
        if (Alerta.confirmarExclusao("Cliente", selectedClient.getName())) {
            clientService.delete(selectedClient);
            Alerta.informacao("Sucesso!", "cliente excluído com sucesso.");
            carregarTodosClients();
            desativarBotoes();
        }

        removerSelecao();
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
        colunaClientType.prefWidthProperty().bind(tvClientes.widthProperty().multiply(0.27));

        tvClientes.getColumns().addAll(colunaName, colunaClientType);
        TableViewUtils.noEditableColumns(tvClientes);

        colunaName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colunaClientType.setCellValueFactory(new PropertyValueFactory<>("clientType"));
    }

    public void carregarTodosClients() {
        List<Client> clients = clientService.findAll();
        if (clients.isEmpty()) addPlaceholderNotCreatedClients();

        atualizarTabela(clients);

        limparBarraPesquisa();
    }

    private void carregarClientsPesquisados() {
        String nameToSearch = tfPesquisar.getText();
        List<Client> clients = clientService.findAllByName(nameToSearch);
        if (clients.isEmpty()) addPlaceholderNotFoundClients();

        atualizarTabela(clients);
    }

    private void atualizarTabela(List<Client> clients) {
        tvClientes.getItems().setAll(clients);
    }

    private void limparBarraPesquisa() {
        tfPesquisar.clear();
    }


    void addPlaceholderNotCreatedClients() {
        tvClientes.setPlaceholder(new Label("Nenhum cliente cadastrado até o momento."));
        tvClientes.getPlaceholder().setStyle("-fx-font-size: 15px");
    }

    void addPlaceholderNotFoundClients() {
        tvClientes.setPlaceholder(new Label("Nenhum cliente encontrado."));
        tvClientes.getPlaceholder().setStyle("-fx-font-size: 15px");
    }

    private void ativarBotoes() {
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    private void removerSelecao() {
        tvClientes.getSelectionModel().clearSelection();
    }
}
