package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.math.MathUtility;
import cz.pk.traycer.pktracer.engine.math.Vector3D;
import cz.pk.traycer.pktracer.engine.math.Vector4D;
import cz.pk.traycer.pktracer.engine.shapes.Hittable;
import cz.pk.traycer.pktracer.engine.shapes.Sphere;
import javafx.event.EventHandler;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;

import static cz.pk.traycer.pktracer.engine.Enums.Shape.SPHERE;
import static cz.pk.traycer.pktracer.engine.Enums.Shape.TRIANGLE;
import static cz.pk.traycer.pktracer.engine.HitRecord.EMPTY_OBJECT_INDEX;
import static cz.pk.traycer.pktracer.engine.Constants.EPSILON;

@Data
@NoArgsConstructor
public class Renderer {
    private @NonNull Vector4D[] accumulationData;
    private @NonNull Vector4D[] viewportData;
    private int viewportWidth;
    private int viewportHeight;

    private Camera camera;

    private Scene activeScene;
    private long frameIndex;

    public Renderer(int width, int height) {
        viewportWidth = width;
        viewportHeight = height;
        camera = new Camera(viewportWidth, viewportHeight);

        accumulationData = new Vector4D[viewportHeight * viewportWidth];
        activeScene = new Scene(new ArrayList<>(), new ArrayList<>());
        frameIndex = 1;
    }

    public void initialize() {
        loadScene();
    }

    //todo: Move it to initialize method and dont separate it. Problem with
    // initCenterPanel method call and sceneFX object creation.
    public void initializeKeyboardAndMouse(javafx.scene.Scene sceneFX) {

        camera.registerKeysPressedEvent(sceneFX);
        camera.registerMouseKeysPressedEvent(sceneFX);
    }

    public void onResize(final int newWidth, final int newHeight) {
//        if(newWidth == imageWidth && newHeight == imageHeight) {
//            return;
//        } else {
//            imageData = new Pixel[imageHeight * imageWidth];
//            accumulationData = new Vector4D[imageHeight * imageWidth];
//
//            camera.onResize(imageWidth, imageHeight);
//        }

        accumulationData = new Vector4D[viewportHeight * viewportWidth];

        camera.onResize(viewportWidth, viewportHeight);
        camera.onUpdate(0.0f);
    }

    public WritableImage render() {
        WritableImage writableImage = new WritableImage(viewportWidth, viewportHeight);

        if(frameIndex == 1) {
            for (int y = 0; y < viewportHeight; y++) {
                for (int x = 0; x < viewportWidth; x++) {
                    accumulationData[x + y * viewportWidth] = new Vector4D();
                }
            }
        }

        PixelWriter pw = writableImage.getPixelWriter();

        for (int y = 0; y < viewportHeight; y++) {
            for (int x = 0; x < viewportWidth; x++) {
                Vector4D color = perPixel(x, y);

                accumulationData[x + y * viewportWidth] = accumulationData[x + y * viewportWidth].add(color);

                Vector4D accumulatedColor = accumulationData[x + y * viewportWidth];
                accumulatedColor = accumulatedColor.div(frameIndex);
                Vector4D finalColor = Vector4D.clamp(accumulatedColor, Vector4D.ZERO, Vector4D.ONE);

                pw.setColor(x, y, new Color(finalColor.getX(), finalColor.getY(), finalColor.getZ(), 1.0));
            }
        }

        frameIndex++;

        return writableImage;
    }

    private Vector4D perPixel(int x, int y) {
        Ray ray = new Ray();
        ray.setOrigin(camera.getPosition());
        ray.setDirection(camera.getRayDirections()[x + y * viewportWidth]);

        int bounces = 5;
        Vector3D contribution = new Vector3D(1.0);
        Vector3D light = new Vector3D(0.0);

        Material material;

        for(int i = 0; i < bounces; i++) {
            HitRecord hitRecord = traceRay(ray);
            if(EMPTY_OBJECT_INDEX == hitRecord.getObjectIndex()) {
                break;
            }

            material = activeScene.getMaterials().get(hitRecord.getMaterialIndex());

            contribution = contribution.mul(material.getAlbedo());
            light = light.add(material.getEmission().mul(contribution));

            ray.setOrigin(hitRecord.getWorldPosition().add(
                    hitRecord.getWorldNormal().mul(EPSILON)));

            ray.setDirection(Vector3D.normalize(
                    hitRecord.getWorldNormal().add(Vector3D.normalize(MathUtility.randomInUnitSphere2()))));
        }

        return new Vector4D(light, 1.0);
    }

    private HitRecord traceRay(final Ray ray) {
        HitRecord hitRecord = new HitRecord();

        for(Hittable model : activeScene.getModels()) {
            if(SPHERE.equals(model.getShape())) {
                Sphere sphere = (Sphere) model;
                IntersectionManager.raySphereIntersection(ray, sphere, hitRecord);
            }
        }

        if(EMPTY_OBJECT_INDEX == hitRecord.getObjectIndex()) {
            return new HitRecord();
        }

        return closestHit(ray, hitRecord);
    }

    private HitRecord closestHit(final Ray ray, HitRecord hitRecord) {
        Hittable model = activeScene.getModels().get(hitRecord.getObjectIndex());
        if (SPHERE.equals(model.getShape())) {
            Sphere closestSphere = (Sphere) model;

            Vector3D origin = ray.getOrigin().add(closestSphere.getPosition());
            Vector3D worldPosition = origin.add(ray.getDirection().mul(hitRecord.getDistance()));
            hitRecord.setWorldNormal(Vector3D.normalize(worldPosition));
            hitRecord.setWorldPosition(worldPosition.sub(closestSphere.getPosition()));
        } else if (TRIANGLE.equals(model.getShape())) {

        }

        return hitRecord;
    }

    private void loadScene() {
        camera.setVerticalFOV(45.0);
        camera.setPosition(new Vector3D(-4.0, -3.5, 45.0));
        camera.setForwardDirection(new Vector3D(-0.08, -0.0461, -0.998));  // new Vector3D(0.0, 0.0, -1.0)
        camera.setNearClip(0.1);
        camera.setFarClip(100.0);

        Sphere sphere0 = new Sphere();
        sphere0.setPosition(new Vector3D(-1.5, 0.0, 0.0));
        sphere0.setRadius(2.0);
        sphere0.setObjectIndex(0);
        sphere0.setMaterialIndex(0);

        Sphere sphere1 = new Sphere();
        sphere1.setPosition(new Vector3D(0.0, -101.0, 0.0));
        sphere1.setRadius(100.0);
        sphere1.setObjectIndex(1);
        sphere1.setMaterialIndex(1);

        Sphere sphere2 = new Sphere();
        sphere2.setPosition(new Vector3D(2.0, 0.0, 0.0));
        sphere2.setRadius(0.78);
        sphere2.setObjectIndex(2);
        sphere2.setMaterialIndex(2);

        Sphere light1 = new Sphere();
        light1.setPosition(new Vector3D(3.3, 4.0, 4));
        light1.setRadius(3.5);
        light1.setObjectIndex(3);
        light1.setMaterialIndex(3);

        Sphere light2 = new Sphere();
        light2.setPosition(new Vector3D(-10.0, 5.0, -50));
        light2.setRadius(1.5);
        light2.setObjectIndex(4);
        light2.setMaterialIndex(4);

        Material limeMaterial = new Material(
                new Vector3D(0.1953125, 0.80078125, 0.1953125),
                0.0f,
                0.0f,
                new Vector3D(0.0, 0.0, 0.0),
                0.0f
        );

        Material redMaterial = new Material(
                new Vector3D(0.850, 0.0, 0.0),
                0.1f,
                0.1f,
                new Vector3D(0.350, 0.0, 0.0),
                0.0f
        );

        Material blueMaterial = new Material(
                new Vector3D(0.0, 0.0, 0.850),
                0.1f,
                0.1f,
                new Vector3D(0.0, 0.0, 0.0),
                0.0f
        );

        Material lightMaterial0 = new Material(
                new Vector3D(0.8, 0.8, 0.8),
                0.1f,
                0.1f,
                new Vector3D(0.8, 0.8, 0.8),
                5.0f
        );

        Material lightMaterial1 = new Material(
                new Vector3D(0.4, 0.4, 0.4),
                0.1f,
                0.1f,
                new Vector3D(0.4, 0.4, 0.4),
                3.0f
        );

        activeScene.getModels().clear();
        activeScene.getModels().add(sphere0);
        activeScene.getModels().add(sphere1);
        activeScene.getModels().add(sphere2);
        activeScene.getModels().add(light1);
        activeScene.getModels().add(light2);

        activeScene.getMaterials().clear();
        activeScene.getMaterials().add(limeMaterial);
        activeScene.getMaterials().add(redMaterial);
        activeScene.getMaterials().add(blueMaterial);
        activeScene.getMaterials().add(lightMaterial0);
        activeScene.getMaterials().add(lightMaterial1);
    }
}
