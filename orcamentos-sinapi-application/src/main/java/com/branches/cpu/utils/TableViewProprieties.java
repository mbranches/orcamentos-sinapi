package com.branches.cpu.utils;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TableViewProprieties {
    public static void noEditableColumns(TableView<?> tableView) {
        for (TableColumn column : tableView.getColumns()) {
            column.setResizable(false);
            column.setReorderable(false);
        }
    }
}
