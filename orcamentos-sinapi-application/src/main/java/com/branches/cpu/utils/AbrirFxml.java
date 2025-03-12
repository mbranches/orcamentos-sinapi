package com.branches.cpu.utils;

import com.branches.cpu.controller.TelaInicialController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AbrirFxml {
    public void abrirFxml(String fileName, String title, Integer width, Integer minHeight, Boolean resizable) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/branches/cpu/fxml/" + fileName + ".fxml"));
            Parent root = loader.load();

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
