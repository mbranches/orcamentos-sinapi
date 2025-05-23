package com.branches.cpu.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Alerta {
    private static ButtonType btnConfirmar = new ButtonType("Confirmar");
    private static ButtonType btnCancelar = new ButtonType("Cancelar");
    private static boolean resposta;

    public static void informacao(String headerText, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static boolean confirmarExclusao(String tipoExclusao, String msgExclusao) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Exclusão de " + tipoExclusao);
        alert.setContentText("Excluir \"" + msgExclusao +"\" ?");
        alert.getButtonTypes().setAll(btnConfirmar, btnCancelar);
        alert.showAndWait()
                .ifPresent(
                        btn -> {
                            if (btn == btnConfirmar) resposta = true;
                            else resposta = false;
                        }
                );
        return resposta;
    }

    public static void error(String tipoErro, String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(tipoErro);
        alert.setContentText(mensagem);
        alert.show();
    }
}
