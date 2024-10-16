module codecounter.fxframe {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.desktop;

    opens codecounter.fxframe to javafx.fxml;
    opens codecounter.counterapp to javafx.fxml;

    exports codecounter.counterapp;
}
