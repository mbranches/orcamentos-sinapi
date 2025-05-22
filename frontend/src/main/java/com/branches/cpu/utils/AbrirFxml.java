package com.branches.cpu.utils;

import com.branches.cpu.controller.*;
import com.branches.cpu.model.BudgetItem;
import com.branches.cpu.model.Budget;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AbrirFxml {
    private final String PATH = "/com/branches/cpu/fxml/";

    private void abrirFxml(Parent root, String title, Integer minWidth, Integer minHeight, Boolean resizable) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setMinWidth(minWidth);
            stage.setMinHeight(minHeight);
            stage.centerOnScreen();
            stage.setResizable(resizable);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            System.out.println("Não foi possível abrir a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaOrcamento(String title, Budget budget, TelaVisualizarOrcamentosController telaOrcamentos){
        String fileName = "tela-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            if (budget != null) {
                TelaOrcamentoController controller = loader.getController();
                controller.setOrcamento(budget);
                controller.carregarTodosBudgetItems();
                controller.setTelaOrcamentos(telaOrcamentos);
            }

            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaVisualizarOrcamentos(String title){
        String fileName = "tela-visualizar-orcamentos";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));

            Parent root = loader.load();
            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }


    public void abrirTelaAdicionar(String titulo, TelaOrcamentoController telaOrcamentoController) {
        String fileName = "tela-adicionar-insumo";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaAdicionarInsumoController telaAdicionarInsumoController = loader.getController();
            telaAdicionarInsumoController.setTelaPrincipal(telaOrcamentoController);

            abrirFxml(root, titulo, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditar(String titulo, TelaOrcamentoController telaOrcamentoController, BudgetItem itemAEditar) {
        String fileName = "tela-editar-insumo";
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarInsumoController telaEditarInsumoController = loader.getController();
            telaEditarInsumoController.setTelaPrincipal(telaOrcamentoController);
            telaEditarInsumoController.setServicoAEditar(itemAEditar);
            telaEditarInsumoController.atualizarCampos();

            abrirFxml(root, titulo, 720, 400, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaEditarOrcamento(String titulo, Budget budgetAEditar, TelaVisualizarOrcamentosController telaVisualizarOrcamentosController) {
        String fileName = "tela-editar-orcamento";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));
            Parent root = loader.load();

            TelaEditarOrcamentoController controller = loader.getController();
            controller.setOrcamento(budgetAEditar);
            controller.setTelaOrcamentosController(telaVisualizarOrcamentosController);

            abrirFxml(root, titulo, 660, 320, false);
        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }

    public void abrirTelaVisualizarClientes(String title){
        String fileName = "tela-visualizar-clientes";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(PATH + fileName + ".fxml"));

            Parent root = loader.load();
            abrirFxml(root, title, 900, 600, true);

        } catch (Exception e) {
            System.out.println("Não foi possível carregar a tela.");
            throw new RuntimeException(e);
        }
    }
}
