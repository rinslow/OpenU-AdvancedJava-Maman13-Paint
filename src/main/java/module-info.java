module com.example.advancedjavacoursemaman13 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.example.advancedjavacoursemaman13 to javafx.fxml;
    exports com.example.advancedjavacoursemaman13;
}