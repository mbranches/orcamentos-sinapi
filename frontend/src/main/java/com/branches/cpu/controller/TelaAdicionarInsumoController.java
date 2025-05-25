package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.components.AutoCompleteTextField;
import com.branches.cpu.model.Supply;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.service.BudgetService;
import com.branches.cpu.service.SupplyService;
import com.branches.cpu.utils.NumberUtils;
import com.branches.cpu.utils.TableColumnUtils;
import com.branches.cpu.utils.TableViewUtils;
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
    private TableView<Supply> tvMostrarInsumo;
    @FXML
    private VBox sugestions;
    private AutoCompleteTextField autoCompletePesquisar = new AutoCompleteTextField();
    private Supply insumoSelecionado;
    private TelaOrcamentoController telaPrincipal;
    private SupplyService service = new SupplyService();
    private final BudgetService budgetService = new BudgetService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criarColunasTabela();

        tvMostrarInsumo.setFixedCellSize(95);
        tvMostrarInsumo.setPlaceholder(new Label("Nenhum insumo selecionado até o momento."));
        tvMostrarInsumo.getPlaceholder().setStyle("-fx-font-size: 15px");

        autoCompletePesquisar.getEntries().addAll(
                service.findAll()
                        .stream()
                        .map(Supply::getDescription)
                        .toList()
        );
        autoCompletePesquisar.setPrefHeight(30);
        autoCompletePesquisar.setOnAction(this::PesquisarInsumo);
        sugestions.getChildren().add(autoCompletePesquisar);
    }

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void PesquisarInsumo(ActionEvent event) {
        carregarItemProcurado();
        tfQuantidade.disableProperty().setValue(false);
    }

    @FXML
    void adicionarFechar(ActionEvent event) {
        if (!fieldQuantityIsValid()) return;
        salvarBudgetItem();
        fecharPagina(event);
    }

    @FXML
    void adicionarInsumo(ActionEvent event) {
        if (!fieldQuantityIsValid()) return;
        salvarBudgetItem();
        tfQuantidade.setDisable(true);
        limparCampos();
    }


    @FXML
    void setarValorTotal(KeyEvent event) {
        String quantidadeString = tfQuantidade.getText().trim();

        if (!quantidadeString.isEmpty() && NumberUtils.isValidNumber(quantidadeString)) {
            ativarBotoes();
            double preco = tvMostrarInsumo.getItems().get(0).getPrice();
            double quantidade = Double.parseDouble(quantidadeString);
            double valorTotal = preco * quantidade;
            txtTotal.setText(NumberUtils.formatarValorBRL(valorTotal));
        } else {
            desativarBotoes();
            txtTotal.setText(NumberUtils.formatarValorBRL(0));
        }
    }

    private void criarColunasTabela() {
        TableColumn<Supply, Long> colunaCodigo = new TableColumn<>("Cód.");
        TableColumn<Supply, String> colunaDescricao = new TableColumn<>("Descrição");
        TableColumn<Supply, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<Supply, Double> colunaValor = new TableColumn<>("Valor Unitário");


        colunaCodigo.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.08));
        colunaDescricao.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.67));
        colunaUnidade.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.1));
        colunaValor.prefWidthProperty().bind(tvMostrarInsumo.widthProperty().multiply(0.13));

        tvMostrarInsumo.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaValor);
        TableViewUtils.noEditableColumns(tvMostrarInsumo);

        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("code"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("description"));
        colunaUnidade.setCellValueFactory(new PropertyValueFactory<>("unitMeasurement"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumnUtils.columnFormatoMonetario(colunaValor);
    }

    private void carregarItemProcurado() {
        List<Supply> foundSupplies = service.findByName(autoCompletePesquisar.getText());
        tvMostrarInsumo.getItems().setAll(foundSupplies);

        insumoSelecionado = foundSupplies.get(0);

        tvMostrarInsumo.getItems().setAll(insumoSelecionado);
    }

    private void limparCampos() {
        autoCompletePesquisar.clear();
        tfQuantidade.clear();
        txtTotal.setText("R$ 0,00");
        tvMostrarInsumo.getItems().clear();
    }

    private void salvarBudgetItem() {
        BudgetItem budgetItemToSave = new BudgetItem();
        budgetItemToSave.setBudget(telaPrincipal.getBudget());
        budgetItemToSave.setSupply(insumoSelecionado);
        budgetItemToSave.setQuantity(Integer.parseInt(tfQuantidade.getText().trim()));

        budgetService.saveBudgetItem(budgetItemToSave);

        telaPrincipal.carregarTodosBudgetItems();
    }

    private boolean fieldQuantityIsValid() {
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
