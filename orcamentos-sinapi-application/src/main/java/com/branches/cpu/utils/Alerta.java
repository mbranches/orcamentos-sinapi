package com.branches.cpu.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Alerta {
    private static ButtonType btnConfirmar = new ButtonType("Confirmar");
    private static ButtonType btnCancelar = new ButtonType("Cancelar");
    private static boolean resposta;

    public static void salvo(String objetoSalvo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(objetoSalvo);
        alert.setContentText(mensagem);
        alert.show();
    }

    public static boolean confirmarExclusão(String tipoExclusao, String msgExclusao) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
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
}
