module com.branches.cpu {
    requires javafx.fxml;
    requires static lombok;
    requires org.controlsfx.controls;
    requires spring.web;
    requires spring.context;
    requires spring.core;
    requires com.fasterxml.jackson.databind;

    opens com.branches.cpu to javafx.fxml;
    exports com.branches.cpu;
    exports com.branches.cpu.controller;
    opens com.branches.cpu.controller;
    opens com.branches.cpu.model;
    opens com.branches.cpu.request to com.fasterxml.jackson.databind;
}
