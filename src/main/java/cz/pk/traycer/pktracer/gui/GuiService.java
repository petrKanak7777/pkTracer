package cz.pk.traycer.pktracer.gui;

import cz.pk.traycer.pktracer.engine.Renderer;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class GuiService {

    private int width = 1280; //320, 640, 1280
    private int height = 720; //240, 480, 720

    public static final int TOP_PANEL_HEIGHT = 44;
    public static final Insets VIEWPORT_PADDING = new Insets(2, 2, 2, 2);

    private ImageView viewportIV = new ImageView();

    private Renderer renderer;

    public GuiService(Renderer renderer) {
        this.renderer = renderer;
    }

    public HBox initTopPanel() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 5, 10, 5));
        hBox.setSpacing(10);
        hBox.getStyleClass().add("dark-grey-background");

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);
        buttonCurrent.getStyleClass().add("dark-gray-button");

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        buttonProjected.getStyleClass().add("dark-gray-button");

        hBox.getChildren().addAll(buttonCurrent, buttonProjected);

        return hBox;
    }

    public SplitPane initCenterPanel() {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.setDividerPositions(0.75);

        splitPane.getItems().addAll(initViewportPanel(), initRightPanel());

        return splitPane;
    }

    private HBox initViewportPanel() {
        renderer.initialize();
        renderer.onResize(renderer.getViewportWidth(), renderer.getViewportHeight());
        viewportIV.setImage(renderer.render());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(2, 2, 2, 2));
        hBox.getChildren().addAll(viewportIV);
        hBox.getStyleClass().add("dark-grey-background");

        return hBox;
    }

    //todo: https://edencoding.com/responsive-layouts/
    private ScrollPane initRightPanel() {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 5, 10, 5));
        vBox.setSpacing(10);
        vBox.getStyleClass().add("dark-grey-background");

        Label title = new Label("Model");
        title.getStyleClass().add("title-text");

        vBox.getChildren().add(title);

        List<VBox> attributes = Arrays.asList(
                initVector3Panel("pos", "Position"),
                initVector3Panel("rot", "Rotation"),
                initVector3Panel("sc", "Scale"),
                initVector3Panel("tr", "Translation"),
                initVector3Panel("lPos", "LightPos"),
                initVector3Panel("col", "Color"),
                initVector3Panel("tr", "Translation"),

                initVector3Panel("lPos1", "LightPos1"),
                initVector3Panel("tr1", "Translation1"),
                initVector3Panel("col1", "Color1"),
                initVector3Panel("lPos2", "LightPos2"),
                initVector3Panel("tr2", "Translation2"),
                initVector3Panel("col2", "Color2"),
                initVector3Panel("lPos3", "LightPos3")
        );

        for (VBox attribute : attributes) {
            VBox.setMargin(attribute, new Insets(0, 0, 0, 8));
            vBox.getChildren().add(attribute);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.fitToWidthProperty().set(true);

        return scrollPane;
    }

    private VBox initVector3Panel(String id, String title) {
        Label labelTitle = new Label(title);
        labelTitle.setMinWidth(50);
        labelTitle.setMaxHeight(50);
        labelTitle.getStyleClass().add("attribute-text");

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.TOP_LEFT);
        flowPane.getChildren().addAll(
                initAttribute("-x", "  X"), //todo: fix text padding by better
                initAttribute("-y", "  Y"), //todo: fix text padding by better
                initAttribute("-z", "  Z")); //todo: fix text padding by better

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.getChildren().addAll(labelTitle, flowPane);

        return vBox;
    }

    private HBox initAttribute(String id, String title) {
        Label label = new Label(title);
        label.getStyleClass().add("text");
        label.setId(id + "-lz");
        label.setMinWidth(15); //todo: fix text padding by better
        label.setMaxWidth(15); //todo: fix text padding by better

        TextField textField = new TextField();
        textField.setId(id + "-lz");
        textField.setMinWidth(5);
        textField.setMinWidth(40);

        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(label, textField);

        return hBox;
    }
}
