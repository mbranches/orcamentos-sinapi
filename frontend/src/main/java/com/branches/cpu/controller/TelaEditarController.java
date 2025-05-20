package com.branches.cpu.controller;

import com.branches.cpu.components.Alerta;
import com.branches.cpu.model.Supply;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.service.InsumoService;
import com.branches.cpu.utils.TableColumnConfig;
import com.branches.cpu.utils.TableViewProprieties;
import com.branches.cpu.utils.Validador;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.branches.cpu.utils.Monetary.formatarValorBRL;

public class TelaEditarController implements Initializable {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfMostrarServico;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<Supply> tvMostrarServico;

    @FXML
    private Text txtTotal;

    private TelaOrcamentoController telaPrincipal;

    private BudgetItem servicoAEditar;

    private InsumoService service = new InsumoService();

    private ObservableList observableList = javafx.collections.FXCollections.observableArrayList();

    @FXML
    void Fechar(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarFechar(ActionEvent event) {
        String quantidadeString = tfQuantidade.getText();
        if (!Validador.isValidNumber(quantidadeString) || Double.parseDouble(quantidadeString) == 0) {
            Alerta.error("Quantidade inválida!", "Digite uma quantidade válida");
            return;
        }
        telaPrincipal.atualizarServico(servicoAEditar);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void editarValorTotal(KeyEvent event) {
        if (Validador.isValidNumber(tfQuantidade.getText())) {
            setarValorTotal();
            servicoAEditar.setQuantity(Integer.parseInt(tfQuantidade.getText()));
            servicoAEditar.setarValorTotal();
        } else {
            txtTotal.setText(formatarValorBRL(0));
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        criarColunasTabela();
        tvMostrarServico.setFixedCellSize(95);
    }

    public void atualizarCampos() {
        tfMostrarServico.setText(servicoAEditar.getInsumo().getDescription());
        tfQuantidade.setText(String.valueOf(servicoAEditar.getQuantity()));
        setarItemTableView();
        setarValorTotal();
    }

    private void setarValorTotal() {
        double preco = servicoAEditar.getInsumo().getPrice();
        double quantidade = Double.parseDouble(tfQuantidade.getText());
        double valorTotal = preco * quantidade;
        txtTotal.setText(formatarValorBRL(valorTotal));
    }

    private void setarItemTableView() {
        List<Supply> servicos = service.findByName(tfMostrarServico.getText());

        observableList.addAll(servicos);

        tvMostrarServico.setItems(observableList);
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

        colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colunaUnidade.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumnConfig.columnFormatoMonetario(colunaValor);
    }

    public void setTelaPrincipal(TelaOrcamentoController telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    public void setServicoAEditar(BudgetItem servicoAEditar) {
        this.servicoAEditar = servicoAEditar;
    }
}
