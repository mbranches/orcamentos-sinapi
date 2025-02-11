module com.branches.cpu {
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires org.controlsfx.controls;

    opens com.branches.cpu to javafx.fxml;
    exports com.branches.cpu;
    exports com.branches.cpu.controller;
    opens com.branches.cpu.controller;
    opens com.branches.cpu.model;
}
