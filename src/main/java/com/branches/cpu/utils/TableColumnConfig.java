package com.branches.cpu.utils;

import com.branches.cpu.model.Servico;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import static com.branches.cpu.utils.Monetary.formatarValorBRL;

public class TableColumnConfig {
    public static void columnFomatoMonetario (TableColumn column) {
        column.setCellFactory(col -> {
            return new TableCell<Servico, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(formatarValorBRL(item));
                    }
                }
            };
        });
    }
}
