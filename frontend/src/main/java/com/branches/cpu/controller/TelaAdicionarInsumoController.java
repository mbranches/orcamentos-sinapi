package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.components.AutoCompleteTextField;
import com.branches.cpu.model.Supply;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.service.BudgetItemService;
import com.branches.cpu.service.SupplyService;
import com.branches.cpu.utils.Monetary;
import com.branches.cpu.utils.TableColumnConfig;
import com.branches.cpu.utils.TableViewProprieties;
import com.branches.cpu.utils.Validador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaAdicionarInsumoController implements Initializable {
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnAdicionarFechar;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private Text txtTotal;
    @FXML
    private TableView<Supply> tvMostrarServico;
    @FXML
    private VBox sugestions;
    private AutoCompleteTextField autoCompletePesquisar = new AutoCompleteTextField();
    private Supply servicoSelecionado;
    private TelaOrcamentoController telaPrincipal;
    private SupplyService service = new SupplyService();
    private BudgetItemService budgetItemService = new BudgetItemService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criarColunasTabela();

        tvMostrarServico.setFixedCellSize(95);
        tvMostrarServico.setPlaceholder(new Label("Nenhum serviço selecionado até o momento."));
        tvMostrarServico.getPlaceholder().setStyle("-fx-font-size: 15px");

        autoCompletePesquisar.getEntries().addAll(
                service.findAll()
                        .stream()
                        .map(Supply::getDescription)
                        .toList()
        );
        autoCompletePesquisar.setPrefHeight(30);
        autoCompletePesquisar.setOnAction(this::PesquisarServico);
        sugestions.getChildren().add(autoCompletePesquisar);
    }

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void PesquisarServico(ActionEvent event) {
        carregarItemProcurado();
        tfQuantidade.disableProperty().setValue(false);
    }

    @FXML
    void adicionarFechar(ActionEvent event) {
        if (!validarCampoQuantidade()) return;
        salvarBudgetItem();
        fecharPagina(event);
    }

    @FXML
    void adicionarServico(ActionEvent event) {
        if (!validarCampoQuantidade()) return;
        salvarBudgetItem();
        tfQuantidade.setDisable(true);
        limparCampos();
    }


    @FXML
    void setarValorTotal(KeyEvent event) {
        String quantidadeString = tfQuantidade.getText().trim();

        if (!quantidadeString.isEmpty() && Validador.isValidNumber(quantidadeString)) {
            ativarBotoes();
            double preco = tvMostrarServico.getItems().get(0).getPrice();
            double quantidade = Double.parseDouble(quantidadeString);
            double valorTotal = preco * quantidade;
            txtTotal.setText(Monetary.formatarValorBRL(valorTotal));
        } else {
            desativarBotoes();
            txtTotal.setText(Monetary.formatarValorBRL(0));
        }
    }

    private void criarColunasTabela() {
        TableColumn<Supply, Long> colunaCodigo = new TableColumn<>("Cód.");
        TableColumn<Supply, String> colunaDescricao = new TableColumn<>("Descrição");
        TableColumn<Supply, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<Supply, Double> colunaValor = new TableColumn<>("Valor Unitário");


        colunaCodigo.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.08));
        colunaDescricao.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.67));
        colunaUnidade.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.1));
        colunaValor.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.13));

        tvMostrarServico.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaValor);
        TableViewProprieties.noEditableColumns(tvMostrarServico);

        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("code"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
        colunaUnidade.setCellValueFactory(new PropertyValueFactory<>("unitMeasurement"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumnConfig.columnFormatoMonetario(colunaValor);
    }

    private void carregarItemProcurado() {
        List<Supply> foundSupplies = service.findByName(autoCompletePesquisar.getText());
        tvMostrarServico.getItems().setAll(foundSupplies);

        servicoSelecionado = foundSupplies.get(0);

        tvMostrarServico.getItems().setAll(servicoSelecionado);
    }

    private void limparCampos() {
        autoCompletePesquisar.clear();
        tfQuantidade.clear();
        txtTotal.setText("R$ 0,00");
        tvMostrarServico.getItems().clear();
    }

    private void salvarBudgetItem() {
        BudgetItem budgetItemToSave = new BudgetItem();
        budgetItemToSave.setBudget(telaPrincipal.getBudget());
        budgetItemToSave.setSupply(servicoSelecionado);
        budgetItemToSave.setQuantity(Integer.parseInt(tfQuantidade.getText().trim()));

        budgetItemService.save(budgetItemToSave);

        telaPrincipal.carregarTodosBudgetItems();
    }

    private boolean validarCampoQuantidade() {
        String quantidade = tfQuantidade.getText();
        if (Double.parseDouble(quantidade) == 0) {
            Alerta.error("Quantidade inválida!", "Digite uma quantidade válida.");
            return false;
        }
        return true;
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void ativarBotoes() {
        btnAdicionar.setDisable(false);
        btnAdicionarFechar.setDisable(false);
    }

    private void desativarBotoes() {
        btnAdicionar.setDisable(true);
        btnAdicionarFechar.setDisable(true);
    }

    public void setTelaPrincipal(TelaOrcamentoController telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

}
