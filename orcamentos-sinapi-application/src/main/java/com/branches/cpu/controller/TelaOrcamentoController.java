/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.branches.cpu.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.model.Orcamento;
import com.branches.cpu.service.ItemOrcamentoService;
import com.branches.cpu.service.OrcamentoService;
import com.branches.cpu.utils.*;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Setter;

/**
 *
 * @author Branches
 */
@Setter
public class TelaOrcamentoController implements Initializable{

    @FXML
    private ImageView imageEditar;
    @FXML
    private TextField tfPesquisar;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TableView<ItemOrcamento> tvServicosAdiconados;
    @FXML
    private Text txtTotal;

    private List<ItemOrcamento> resultadoBusca = new ArrayList<>();

    private List<ItemOrcamento> servicosAdicionados = new ArrayList<>();

    private AbrirFxml abrirFxml = new AbrirFxml();

    private double valorTotal = 0;

    private Orcamento orcamento;

    private ItemOrcamento servicoSelecionado = null;

    @FXML
    void abrirTelaAdicionar(ActionEvent event) {
        abrirFxml("tela-adicionar", "Adicionar Serviço");
    }

    @FXML
    void autoComplementarTabela(KeyEvent event) {
        atualizarTabela();
    }

    @FXML
    void salvarOrçamento(ActionEvent event) {
        OrcamentoService orcamentoService = new OrcamentoService();
        ItemOrcamentoService itemOrcamentoService = new ItemOrcamentoService();

        if (orcamento == null) abrirFxml.abrirFxml("tela-salvar-orcamento", "Salvar Orçamento", 660, 320, false, servicosAdicionados, this);
        else {
            itemOrcamentoService.saveAll(servicosAdicionados);
        }
    }
    
    public void abrirFxml(String nomeFile, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/branches/cpu/fxml/" + nomeFile + ".fxml"));
            Parent root = loader.load();

            if (nomeFile.equals( "tela-adicionar" ) ) {
                TelaAdicionarController telaAdicionarController = loader.getController();
                telaAdicionarController.setTelaPrincipal(this);
            } else if (nomeFile.equals( "tela-editar" )) {
                TelaEditarController telaEditarController = loader.getController();
                telaEditarController.setTelaPrincipal(this);
                telaEditarController.setServicoAEditar(this.servicoSelecionado);
                telaEditarController.atualizarCampos();
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.setMinWidth(720);
            stage.setMinHeight(400);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void abrirTelaEditar(ActionEvent event) {
        abrirFxml("tela-editar", "Editar Serviço");
        desativarBotoes();
    }

    @FXML
    private void excluirServico(ActionEvent event) {
        servicosAdicionados.remove(servicoSelecionado);

        limparBarraPesquisa();

        atualizarTabela();
        atualizarValorTotal();

        desativarBotoes();
    }

    @FXML
    void selecionarServicoAdicionado(MouseEvent event) {
        servicoSelecionado = tvServicosAdiconados.getSelectionModel().getSelectedItem();
        if (servicoSelecionado != null) ativarBotoes();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tvServicosAdiconados.setPlaceholder(new Label("Nenhum serviço adicionado até o momento."));
        tvServicosAdiconados.getPlaceholder().setStyle("-fx-font-size: 15px");

        tvServicosAdiconados.setFixedCellSize(80);
        tvServicosAdiconados.setEditable(false);

        criarColunasTabela();

        atualizarTabela();
    }

    private void atualizarTabela() {
        tvServicosAdiconados.getItems().clear();
        resultadoBusca.clear();
        String descricao = tfPesquisar.getText();

        if (descricao.isEmpty()) {
            for (ItemOrcamento servicosAdicionado : servicosAdicionados) {
                tvServicosAdiconados.getItems().add(servicosAdicionado);
            }

        } else {
            resultadoBusca = consultarEmServicosAdicionados(descricao);

            if (resultadoBusca.isEmpty()) {
                tvServicosAdiconados.getItems().clear();
            } else {
                tvServicosAdiconados.getItems().setAll(resultadoBusca);
            }
        }
    }

    private List<ItemOrcamento> consultarEmServicosAdicionados(String descricao) {
        List<ItemOrcamento> servicosPesquisados= Lists.containsInList(servicosAdicionados, s -> s.getInsumo().getDescricao().toLowerCase().contains(descricao.toLowerCase()));

        return servicosPesquisados;
    }

    private void atualizarValorTotal() {
        valorTotal = 0;
        for (ItemOrcamento itemOrcamento : tvServicosAdiconados.getItems()) {
            valorTotal += itemOrcamento.getValorTotal();
        }

        txtTotal.setText(Monetary.formatarValorBRL(valorTotal));
    }

    private void criarColunasTabela() {
        TableColumn<ItemOrcamento, Long> colunaCodigo =new TableColumn<>("Cód.");
        TableColumn<ItemOrcamento, String> colunaDescricao =new TableColumn<>("Descrição");
        TableColumn<ItemOrcamento, String> colunaUnidade = new TableColumn<>("Unidade");
        TableColumn<ItemOrcamento, Integer> colunaQtd = new TableColumn<>("Qtd.");
        TableColumn<ItemOrcamento, Double> colunaTotal = new TableColumn<>("Total");

        colunaCodigo.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.05));
        colunaDescricao.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.685));
        colunaUnidade.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.07));
        colunaQtd.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.06));
        colunaTotal.prefWidthProperty().bind(tvServicosAdiconados.widthProperty().multiply(0.11));

        tvServicosAdiconados.getColumns().addAll(colunaCodigo, colunaDescricao, colunaUnidade, colunaQtd, colunaTotal);
        TableViewProprieties.noEditableColumns(tvServicosAdiconados);

        colunaCodigo.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getInsumo().getCodigo()).asObject());

        colunaDescricao.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInsumo().getDescricao()));

        colunaUnidade.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getInsumo().getUnidadeMedida()));

        colunaQtd.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaTotal.setCellValueFactory(new PropertyValueFactory("valorTotal"));

        TableColumnConfig.columnFomatoMonetario(colunaTotal);
    }

    public void adicionarServico(ItemOrcamento itemOrcamento) {
        itemOrcamento.setOrcamento(orcamento);
        servicosAdicionados.add(itemOrcamento);

        limparBarraPesquisa();
        atualizarTabela();
        atualizarValorTotal();
    }

    private void ativarBotoes() {
        btnEditar.setDisable(false);
        btnExcluir.setDisable(false);
    }

    private void desativarBotoes() {
        tvServicosAdiconados.getSelectionModel().clearSelection();

        btnEditar.setDisable(true);
        btnExcluir.setDisable(true);
    }

    public void atualizarServico(ItemOrcamento servicoNovo) {
        for (ItemOrcamento servico : servicosAdicionados) {
            if (servico.equals(servicoNovo)) {
                servico.setQuantidade(servicoNovo.getQuantidade());
                servico.setarValorTotal();
            }
        }

        limparBarraPesquisa();
        atualizarTabela();
        atualizarValorTotal();
    }

    public void limparBarraPesquisa() {
        if (!tfPesquisar.getText().isEmpty()) tfPesquisar.clear();
    }


    public void setServicosAdicionados(List<ItemOrcamento> itemOrcamentos) {
        servicosAdicionados.clear();
        servicosAdicionados.addAll(itemOrcamentos);

        servicosAdicionados.forEach(ItemOrcamento::setarValorTotal);

        atualizarTabela();
    }
}
