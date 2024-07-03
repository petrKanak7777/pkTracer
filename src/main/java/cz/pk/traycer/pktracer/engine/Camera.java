package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.math.Matrix4X4D;
import cz.pk.traycer.pktracer.engine.math.Vector2D;
import cz.pk.traycer.pktracer.engine.math.Vector3D;
import cz.pk.traycer.pktracer.engine.math.Vector4D;
import cz.pk.traycer.pktracer.gui.GuiService;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import static cz.pk.traycer.pktracer.gui.GuiService.TOP_PANEL_HEIGHT;

@Data
@AllArgsConstructor
public class Camera {
    private Matrix4X4D projection;
    private Matrix4X4D view;
    private Matrix4X4D inverseProjection;
    private Matrix4X4D inverseView;

    private boolean moved;
    private boolean upIsPressed;
    private boolean downIsPressed;
    private boolean leftIsPressed;
    private boolean rightIsPressed;

    private Vector3D[] rayDirections;
    private Vector2D lastMousePosition;
    private Vector2D currentMousePosition;
    private Vector3D position;
    private Vector3D forwardDirection;

    private int viewportWidth;
    private int viewportHeight;
    private double verticalFOV;
    private double nearClip;
    private double farClip;

    private javafx.scene.Scene sceneFX;

    public Camera(int viewportWidth, int viewportHeight) {
        position = new Vector3D(0.0, 0.0, 2.0);
        forwardDirection = new Vector3D(0.0, 0.0, -1.0);
        verticalFOV = 45.0;
        nearClip = 0.1;
        farClip = 100.0;

        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        moved = false;
        upIsPressed = false;
        downIsPressed = false;
        leftIsPressed = false;
        rightIsPressed = false;
        lastMousePosition = new Vector2D(
                this.viewportWidth / 2.0,
                this.viewportHeight / 2.0);
        currentMousePosition = lastMousePosition;

        rayDirections = new Vector3D[viewportHeight * viewportWidth];

        projection = new Matrix4X4D();
        projection.setIdentity();
        view = new Matrix4X4D();
        view.setIdentity();
        inverseProjection = new Matrix4X4D();
        inverseProjection.setIdentity();
        inverseView = new Matrix4X4D();
        inverseView.setIdentity();
    }

    boolean onUpdate(float ts) {

        Vector2D delta = currentMousePosition.sub(lastMousePosition);
        lastMousePosition = currentMousePosition;



        recalculateView();
        recalculateRayDirections();

        return true;
    }

    void onResize(final int width, final int height) {
//        if (viewportWidth == width && viewportHeight == height) {
//            return;
//        }

        viewportWidth = width;
        viewportHeight = height;

        recalculateProjection();
        recalculateRayDirections();
    }

    public void recalculateProjection() {
        projection = Matrix4X4D.perspectiveFov(
                Math.toRadians(verticalFOV),
                (double)viewportWidth / (double)viewportHeight,
                nearClip, farClip);
        inverseProjection = Matrix4X4D.inverse(projection);
    }

    public void recalculateView() {
        view = Matrix4X4D.lookAt(
                position,
                position.add(forwardDirection),
                new Vector3D(0.0, 1.0, 0.0));
        inverseView = Matrix4X4D.inverse(view);
    }

    public void recalculateRayDirections() {
        rayDirections = new Vector3D[viewportWidth * viewportHeight];

        for (int y = 0; y < viewportHeight; y++) {
            for (int x = 0; x < viewportWidth; x++) {
                Vector2D coord = new Vector2D(
                        (double) x / (double)viewportWidth,
                        (double) y / (double)viewportHeight);
                coord = coord.mul(2.0);
                coord = coord.sub(1.0);

                Vector4D target = inverseProjection.mul(coord.getX(), coord.getY(), 1.0, 1.0);
                Vector3D rayDirection = new Vector3D(inverseView.mul(
                        new Vector4D(
                                Vector3D.normalize(
                                        new Vector3D(target).div(target.getW())),
                                0.0)));
                rayDirections[x + y * viewportWidth] = rayDirection;
            }
        }
    }

    public void registerKeysPressedEvent(javafx.scene.Scene scene) {
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                upIsPressed = true;
                System.out.println("The 'W' key was pressed");
            }

            if (e.getCode() == KeyCode.S) {
                downIsPressed = true;
                System.out.println("The 'S' key was pressed");
            }

            if (e.getCode() == KeyCode.A) {
                leftIsPressed = true;
                System.out.println("The 'A' key was pressed");
            }

            if (e.getCode() == KeyCode.D) {
                rightIsPressed = true;
                System.out.println("The 'D' key was pressed");
            }

            if (e.getCode() == KeyCode.UP) {
                upIsPressed = true;
                System.out.println("The 'Up' key was pressed");
            }

            if (e.getCode() == KeyCode.DOWN) {
                downIsPressed = true;
                System.out.println("The 'Down' key was pressed");
            }

            if (e.getCode() == KeyCode.LEFT) {
                leftIsPressed = true;
                System.out.println("The 'Left' key was pressed");
            }

            if (e.getCode() == KeyCode.RIGHT) {
                rightIsPressed = true;
                System.out.println("The 'Right' key was pressed");
            }
        });
    }

    public void registerMouseKeysPressedEvent(javafx.scene.Scene scene) {
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                currentMousePosition.setX(mouseEvent.getX() - GuiService.VIEWPORT_PADDING.getLeft());
                currentMousePosition.setY(mouseEvent.getY() - TOP_PANEL_HEIGHT - GuiService.VIEWPORT_PADDING.getTop());

                String msg =
                        "(x: " + mouseEvent.getX() + ", y: " + mouseEvent.getY() + ") -- " +
                        "(sceneX: " + mouseEvent.getSceneX() + ", sceneY: " + mouseEvent.getSceneY() + ") -- " +
                        "(screenX: " + mouseEvent.getScreenX() + ", screenY: " + mouseEvent.getScreenY() + ") -- " +
                        "(currentMousePositionX: " + currentMousePosition.getX() + ", currentMousePositionY: " + currentMousePosition.getY() + ")";

                System.out.println(msg);
            }
        });
    }
}
