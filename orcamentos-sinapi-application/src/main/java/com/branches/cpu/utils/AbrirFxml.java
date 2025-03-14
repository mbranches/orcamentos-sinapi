package com.branches.cpu.utils;

import com.branches.cpu.controller.TelaInicialController;
import com.branches.cpu.controller.TelaOrcamentoController;
import com.branches.cpu.controller.TelaSalvarOrcamentoController;
import com.branches.cpu.model.ItemOrcamento;
import com.branches.cpu.request.ItemOrcamentoPostRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class AbrirFxml {
    public void abrirFxml(String fileName, String title, Integer width, Integer minHeight, Boolean resizable, List<ItemOrcamento> items, TelaOrcamentoController telaOrcamentoController) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/branches/cpu/fxml/" + fileName + ".fxml"));
            Parent root = loader.load();

            if (fileName.equals("tela-salvar-orcamento")) {
                TelaSalvarOrcamentoController controller = loader.getController();
                controller.setOrcamentoController(telaOrcamentoController);
                controller.setItemOrcamentoPostRequests(items);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.setMinWidth(width);
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
}
