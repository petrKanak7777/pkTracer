module cz.pk.traycer.pktracer {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires dsiutils;


    opens cz.pk.traycer.pktracer to javafx.fxml;
    exports cz.pk.traycer.pktracer;
}