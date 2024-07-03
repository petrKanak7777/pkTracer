package cz.pk.tracer.pktracer.engine.math;

import cz.pk.traycer.pktracer.engine.math.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Vector3DTest {

    private static final Vector3D VA = new Vector3D(1.0, 2.0, 3.0);
    private static final Vector3D VB = new Vector3D(2.0, 4.0, 6.0);

    @Test
    public void test_add_expectedCorrectResult() {
        Vector3D result = VA.add(VB);
        assertEquals(result, new Vector3D(3.0, 6.0, 9.0));
    }

    @Test
    public void test_sub_expectedCorrectResult() {
        Vector3D result = VB.sub(VA);
        assertEquals(result, new Vector3D(1.0, 2.0, 3.0));
    }

    @Test
    public void test_neg_expectedCorrectResult() {
        Vector3D result = VA.neg();
        assertEquals(result, new Vector3D(-1.0, -2.0, -3.0));
    }

    @Test
    public void test_mul_expectedCorrectResult() {
        Vector3D result = VA.mul(2.0);
        assertEquals(result, new Vector3D(2.0, 4.0, 6.0));
    }
}
