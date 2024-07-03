package cz.pk.traycer.pktracer.engine.math;

import cz.pk.traycer.pktracer.engine.Pixel;
import it.unimi.dsi.util.XoRoShiRo128PlusRandom;

import static cz.pk.traycer.pktracer.engine.Constants.MAX_RGB;
import static cz.pk.traycer.pktracer.engine.Constants.MIN_RGB;

public class MathUtility {

    private static final XoRoShiRo128PlusRandom random =
            new XoRoShiRo128PlusRandom(System.currentTimeMillis());

    public static Vector3D randomInUnitSphere() {
        return Vector3D.normalize(new Vector3D(
                random.nextDouble(-1.0, 1.0),
                random.nextDouble(-1.0, 1.0),
                random.nextDouble(-1.0, 1.0)
        ));
    }

    public static void changeSeed(long seed) {
        random.setSeed(seed);
    }

    /*
    https://karthikkaranth.me/blog/generating-random-points-in-a-sphere/
     */
    public static Vector3D randomInUnitSphere2() {
        double x, y, z, d;
        do {
            x = random.nextDouble() * 2.0 - 1.0;
            y = random.nextDouble() * 2.0 - 1.0;
            z = random.nextDouble() * 2.0 - 1.0;
            d = x*x + y*y + z*z;
        } while (d > 1.0);

        return new Vector3D(x, y, z);
    }

    public static Pixel convertToRGBA(final Vector4D color) {
        return new Pixel(
                color.getX() * MAX_RGB,
                color.getY() * MAX_RGB,
                color.getZ() * MAX_RGB,
                color.getW() * MAX_RGB);
    }

    public static Vector4D convertFromRGBA(final Vector4D color) {
        return new Vector4D(
                color.getX() * MIN_RGB,
                color.getY() * MIN_RGB,
                color.getZ() * MIN_RGB,
                color.getW() * MIN_RGB);
    }
}
