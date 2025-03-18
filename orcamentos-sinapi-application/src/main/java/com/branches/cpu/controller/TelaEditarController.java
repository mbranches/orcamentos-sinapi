package com.branches.cpu.controller;

import com.branches.cpu.model.Insumo;
import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.service.InsumoService;
import com.branches.cpu.utils.Alerta;
import com.branches.cpu.utils.TableColumnConfig;
import com.branches.cpu.utils.TableViewProprieties;
import com.branches.cpu.utils.Validador;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.branches.cpu.utils.Monetary.formatarValorBRL;
@Setter
public class TelaEditarController implements Initializable {
    @FXML
    private Button btnSalvarFechar;

    @FXML
    private TextField tfMostrarServico;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<Insumo> tvMostrarServico;

    @FXML
    private Text txtTotal;

    private TelaOrcamentoController telaPrincipal;

    private ItemOrcamento servicoAEditar;

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
            servicoAEditar.setQuantidade(Double.parseDouble(tfQuantidade.getText()));
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
        tfMostrarServico.setText(servicoAEditar.getInsumo().getDescricao());
        tfQuantidade.setText(String.valueOf(servicoAEditar.getQuantidade()));
        setarItemTableView();
        setarValorTotal();
    }

    private void setarValorTotal() {
        double preco = servicoAEditar.getInsumo().getPreco();
        double quantidade = Double.parseDouble(tfQuantidade.getText());
        double valorTotal = preco * quantidade;
        txtTotal.setText(formatarValorBRL(valorTotal));
    }

    private void setarItemTableView() {
        List<Insumo> servicos = service.findByName(tfMostrarServico.getText());

        observableList.addAll(servicos);

        tvMostrarServico.setItems(observableList);
    }

    private void criarColunasTabela() {
        TableColumn<Insumo, Long> colunaCodigo =new TableColumn<>("Cód.");
        TableColumn<Insumo, String> colunaDescricao =new TableColumn<>("Descrição");
        TableColumn<Insumo, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<Insumo, Double> colunaValor = new TableColumn<>("Valor Unitário");


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

        TableColumnConfig.columnFomatoMonetario(colunaValor);
    }
}
