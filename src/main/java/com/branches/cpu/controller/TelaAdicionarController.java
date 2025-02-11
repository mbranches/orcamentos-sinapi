/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.branches.cpu.controller;

import com.branches.cpu.Dao.ServicoDao;
import com.branches.cpu.model.Servico;
import com.branches.cpu.model.ServicoAdicionado;
import com.branches.cpu.utils.Monetary;
import com.branches.cpu.utils.TableViewProprieties;
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
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Branches
 */
public class TelaAdicionarController implements Initializable {
    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnAdicionarFechar;

    @FXML
    private TextField tfPesquisarServico;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private Text txtTotal;

    @FXML
    private TableView<Servico> tvMostrarServico;

    private ObservableList observableList = javafx.collections.FXCollections.observableArrayList();
    private Servico servicoSelecionado;
    List<Servico> resultadoBusca = new ArrayList<>();

    private java.util.List<ServicoAdicionado> servicosAdicionados = new ArrayList<>();

    private TelaOrcamentoController telaPrincipal;

    @FXML
    void Fechar(ActionEvent event) {
        fecharPagina(event);
    }

    @FXML
    void PesquisarServico(ActionEvent event) {
        atualizarTabela();
        tfQuantidade.disableProperty().setValue(false);
    }

    @FXML
    void adicionarFechar(ActionEvent event) {
        adicionar();
        fecharPagina(event);
    }

    @FXML
    void adicionarServico(ActionEvent event) {
        adicionar();
        tfQuantidade.setDisable(true);
        limparCampos();
    }



    @FXML
    void setarValorTotal(KeyEvent event) {
        if (tfQuantidade.getText() != null) {
            double valorTotal = resultadoBusca.get(0).getPreco() * Integer.valueOf(tfQuantidade.getText());
            txtTotal.setText(Monetary.formatarValorBRL(valorTotal));
        } else {
            txtTotal.setText(Monetary.formatarValorBRL(0));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        criarColunasTabela();

        tvMostrarServico.setFixedCellSize(95);
        tvMostrarServico.setPlaceholder(new Label("Nenhum serviço selecionado até o momento."));
        tvMostrarServico.getPlaceholder().setStyle("-fx-font-size: 15px");


        AutoCompletionBinding<Servico> autoCompletePesquisar = TextFields.bindAutoCompletion(
                tfPesquisarServico,
                ServicoDao.consultarServicos(tfPesquisarServico.getText())
        );
        autoCompletePesquisar.setMinWidth(600);

        autoCompletePesquisar.setOnAutoCompleted( event -> {
            Servico servicoPesquisado = event.getCompletion();
        });
    }

    private void atualizarTabela() {
        tvMostrarServico.getItems().clear();
        resultadoBusca = ServicoDao.consultarServicos(tfPesquisarServico.getText());
        servicoSelecionado = resultadoBusca.get(0);

        tvMostrarServico.getItems().add(servicoSelecionado);
    }

    private void criarColunasTabela() {
        TableColumn<Servico, Long> colunaCodigo =new TableColumn<>("Cód.");
        TableColumn<Servico, String> colunaDescricao =new TableColumn<>("Descrição");
        TableColumn<Servico, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<Servico, Double> colunaValor = new TableColumn<>("Valor Unitário");


        colunaCodigo.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.08));
        colunaDescricao.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.67));
        colunaUnidade.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.1));
        colunaValor.prefWidthProperty().bind(tvMostrarServico.widthProperty().multiply(0.13));

        tvMostrarServico.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaValor);
        TableViewProprieties.noEditableColumns(tvMostrarServico);

        colunaCodigo.setCellValueFactory(new PropertyValueFactory("codigo"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colunaUnidade.setCellValueFactory(new PropertyValueFactory("unidadeMedida"));
        colunaValor.setCellValueFactory(new PropertyValueFactory("preco"));

        colunaValor.setCellFactory(col -> {
            return new TableCell<Servico, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(Monetary.formatarValorBRL(item));
                    }
                }
            };
        });
    }

    private void limparCampos() {
        tfPesquisarServico.clear();
        tfQuantidade.clear();
        txtTotal.setText("R$ 0,00");
        tvMostrarServico.getItems().clear();
    }

    public void setTelaPrincipal(TelaOrcamentoController telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    private void adicionar() {
        ServicoAdicionado servicoAdicionado = new ServicoAdicionado();
        servicoAdicionado.setCodigo(servicoSelecionado.getCodigo());
        servicoAdicionado.setDescricao(servicoSelecionado.getDescricao());
        servicoAdicionado.setUnidadeMedida(servicoSelecionado.getUnidadeMedida());
        servicoAdicionado.setPreco(servicoSelecionado.getPreco());
        servicoAdicionado.setOrigemPreco(servicoSelecionado.getOrigemPreco());
        servicoAdicionado.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
        servicoAdicionado.setarValorTotal();

        telaPrincipal.adicionarServico(servicoAdicionado);
    }

    private void fecharPagina(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
