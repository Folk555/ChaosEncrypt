module com.chaoticencoder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.swing;

    opens com.chaoticencoder to javafx.fxml;
    exports com.chaoticencoder;
    exports test;
    opens test to javafx.fxml;
}