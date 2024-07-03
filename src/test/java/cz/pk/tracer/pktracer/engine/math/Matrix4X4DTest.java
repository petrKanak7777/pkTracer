package cz.pk.tracer.pktracer.engine.math;

import cz.pk.traycer.pktracer.engine.math.Matrix4X4D;
import cz.pk.traycer.pktracer.engine.math.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Matrix4X4DTest {

    private final double EPSILON = 0.000001d;

    @Test
    public void test_lookAt_expectedCorrectMatrixCreation() {
        Vector3D position = new Vector3D(0.0, 0.0, 3.0);
        Vector3D forwardDirection = new Vector3D(0.0, 0.0, -1.0);


        Matrix4X4D viewMatrix = Matrix4X4D.lookAt(
                position,
                position.add(forwardDirection),
                new Vector3D(0.0, 1.0, 0.0));

        assertEquals(viewMatrix.getElement(0,0), 1.0);
        assertEquals(Math.abs(viewMatrix.getElement(0,1)), 0.0);
        assertEquals(Math.abs(viewMatrix.getElement(0,2)), 0.0);
        assertEquals(Math.abs(viewMatrix.getElement(0,3)), 0.0);

        assertEquals(Math.abs(viewMatrix.getElement(1,0)), 0.0);
        assertEquals(viewMatrix.getElement(1,1), 1.0);
        assertEquals(Math.abs(viewMatrix.getElement(1,2)), 0.0);
        assertEquals(Math.abs(viewMatrix.getElement(1,3)), 0.0);

        assertEquals(Math.abs(viewMatrix.getElement(2,0)), 0.0);
        assertEquals(Math.abs(viewMatrix.getElement(2,1)), 0.0);
        assertEquals(viewMatrix.getElement(2,2), 1.0);
        assertEquals(Math.abs(viewMatrix.getElement(2,3)), 0.0);

        assertEquals(Math.abs(viewMatrix.getElement(3,0)), 0.0);
        assertEquals(Math.abs(viewMatrix.getElement(3,1)), 0.0);
        assertEquals(viewMatrix.getElement(3,2), -3.0);
        assertEquals(viewMatrix.getElement(3,3), 1.0);
    }

    @Test
    public void test_perspectiveFov_expectedCorrectMatrixCreation() {
        double verticalFOV = 45.0f;
        int width = 1920;
        int height = 1080;
        double nearClip = 0.1;
        double farClip = 100.0;

        Matrix4X4D projectionMatrix = Matrix4X4D.perspectiveFov(
                Math.toRadians(verticalFOV),
                (double)width / (double)height,
                nearClip,
                farClip
        );

        assertEquals(projectionMatrix.getElement(0,0), 1.3579951288348662, EPSILON);
        assertEquals(Math.abs(projectionMatrix.getElement(0,1)), 0.0);
        assertEquals(Math.abs(projectionMatrix.getElement(0,2)), 0.0);
        assertEquals(Math.abs(projectionMatrix.getElement(0,3)), 0.0);

        assertEquals(Math.abs(projectionMatrix.getElement(1,0)), 0.0);
        assertEquals(projectionMatrix.getElement(1,1), 2.414213562373095, EPSILON);
        assertEquals(Math.abs(projectionMatrix.getElement(1,2)), 0.0);
        assertEquals(Math.abs(projectionMatrix.getElement(1,3)), 0.0);

        assertEquals(Math.abs(projectionMatrix.getElement(2,0)), 0.0);
        assertEquals(Math.abs(projectionMatrix.getElement(2,1)), 0.0);
        assertEquals(projectionMatrix.getElement(2,2), -1.002002002002002, EPSILON);
        assertEquals(projectionMatrix.getElement(2,3), -1.0);

        assertEquals(Math.abs(projectionMatrix.getElement(3,0)), 0.0);
        assertEquals(Math.abs(projectionMatrix.getElement(3,1)), 0.0);
        assertEquals(projectionMatrix.getElement(3,2), -0.20020020020020018, EPSILON);
        assertEquals(Math.abs(projectionMatrix.getElement(3,3)), 0.0);
    }

    @Test
    public void test_inverse_expectedProjectionMatrixInverseCreation() {
        double verticalFOV = 45.0f;
        int width = 1920;
        int height = 1080;
        double nearClip = 0.1;
        double farClip = 100.0;

        Matrix4X4D projectionMatrix = Matrix4X4D.perspectiveFov(
                Math.toRadians(verticalFOV),
                (double)width / (double)height,
                nearClip,
                farClip
        );

        Matrix4X4D inverseProjectionMatrix = Matrix4X4D.inverse(projectionMatrix);

        assertEquals(inverseProjectionMatrix.getElement(0,0), 0.7363796664410578, EPSILON);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(0,1)), 0.0);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(0,2)), 0.0);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(0,3)), 0.0);

        assertEquals(Math.abs(inverseProjectionMatrix.getElement(1,0)), 0.0);
        assertEquals(inverseProjectionMatrix.getElement(1,1), 0.41421356237309503, EPSILON);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(1,2)), 0.0);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(1,3)), 0.0);

        assertEquals(Math.abs(inverseProjectionMatrix.getElement(2,0)), 0.0);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(2,1)), 0.0);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(2,2)), 0.0);
        assertEquals(inverseProjectionMatrix.getElement(2,3), -4.995, EPSILON);

        assertEquals(Math.abs(inverseProjectionMatrix.getElement(3,0)), 0.0);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(3,1)), 0.0);
        assertEquals(inverseProjectionMatrix.getElement(3,2), -0.9999999999999999, EPSILON);
        assertEquals(Math.abs(inverseProjectionMatrix.getElement(3,3)), 5.005);
    }
}
