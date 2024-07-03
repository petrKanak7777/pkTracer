package cz.pk.traycer.pktracer;

import cz.pk.traycer.pktracer.engine.Renderer;
import cz.pk.traycer.pktracer.gui.GuiService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HelloApplication extends Application {

    private Timer timer;
    private final long period = 500;

    private Scene sceneFX;
    private Renderer renderer;

    private int width = 1280; //320, 640, 1280
    private int height = 720; //240, 480, 720
    private GuiService guiService;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        renderer = new Renderer(width, height);
        guiService = new GuiService(renderer);

        BorderPane border = new BorderPane();
        border.setTop(guiService.initTopPanel());
        border.setCenter(guiService.initCenterPanel());

        sceneFX = new Scene(border, 1200, 800);
        sceneFX.getStylesheets().add("stylesheet.css");

        renderer.initializeKeyboardAndMouse(sceneFX);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                guiService.getViewportIV().setImage(renderer.render());
                System.out.println("FrameIndex= " + renderer.getFrameIndex());
            }
        };
        timer.scheduleAtFixedRate(task, 0, period);

        stage.setTitle("PK Tracer");
        stage.setScene(sceneFX);
        stage.show();
    }
}