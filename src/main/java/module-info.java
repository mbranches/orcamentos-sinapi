module com.branches.cpu {
    requires javafx.fxml;
    requires static lombok;
    requires org.controlsfx.controls;
    requires jakarta.persistence;

    opens com.branches.cpu to javafx.fxml;
    exports com.branches.cpu;
    exports com.branches.cpu.controller;
    opens com.branches.cpu.controller;
    opens com.branches.cpu.model;
}
