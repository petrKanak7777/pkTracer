package cz.pk.traycer.pktracer.engine.math;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector4D {
    private double x;
    private double y;
    private double z;
    private double w;

    public static final Vector4D ZERO = new Vector4D(0, 0, 0, 0);
    public static final Vector4D ONE = new Vector4D(1, 1, 1, 1);

    public Vector4D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
        this.w = 0.0;
    }

    public Vector4D(final Vector4D v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = v.getW();
    }

    public Vector4D(final Vector3D v, final double w) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
        this.w = w;
    }

    public Vector4D(final double w) {
        this.x = w;
        this.y = w;
        this.z = w;
        this.w = w;
    }

    public double getElement(final int index) {
        return switch (index) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            case 3 -> w;
            default -> throw new IllegalArgumentException(
                    String.format("Index out of bounds. Fill 0 to 3 as index value. Index value=%d", index));
        };
    }

    public void setElement(final int index, final double value) {
        switch (index) {
            case 0 -> x = value;
            case 1 -> y = value;
            case 2 -> z = value;
            case 3 -> w = value;
            default -> throw new IllegalArgumentException(
                    String.format("Index out of bounds. Fill 0 to 3 as index value. Index value=%d", index));
        }
    }

    public Vector4D neg() {
        return new Vector4D(
                -this.x,
                -this.y,
                -this.z,
                -this.w
        );
    }

    public Vector4D add(final Vector4D v) {
        return new Vector4D(
                this.x + v.getX(),
                this.y + v.getY(),
                this.z + v.getZ(),
                this.z + v.getW()
        );
    }

    /**
     * Special add operation to prevent garbage collected on Vector object of viewport data.
     * Value of u is reused and isn't recreated. It prevents vector to be garbage collected.
     *
     * @param u Input reused Vector4D.
     * @param v Input Vector3D.
     * @param a Input Vector4D alpha value.
     */
    public static void referencedAdd(final Vector4D u, final Vector3D v, final double a) {
        u.setX(u.getX() + v.getX());
        u.setY(u.getY() + v.getY());
        u.setZ(u.getZ() + v.getZ());
        u.setW(a);
    }

    public Vector4D sub(final Vector4D v) {
        return new Vector4D(
                this.x - v.getX(),
                this.y - v.getY(),
                this.z - v.getZ(),
                this.w - v.getW()
        );
    }

    public Vector4D mul(final double t) {
        return new Vector4D(
                this.x * t,
                this.y * t,
                this.z * t,
                this.w * t
        );
    }

    public Vector4D div(final double t) {
        double denominator = 1 / t;
        return new Vector4D(
                this.x * denominator,
                this.y * denominator,
                this.z * denominator,
                this.w * denominator
        );
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public static double dot(final Vector4D u, final Vector4D v) {
        return u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ() + u.getW() * v.getW();
    }

    public static Vector4D clamp(final Vector4D u, final Vector4D min, final Vector4D max) {
        double newX = Math.min(Math.max(u.getX(), min.getX()), max.getX());
        double newY = Math.min(Math.max(u.getY(), min.getY()), max.getY());
        double newZ = Math.min(Math.max(u.getZ(), min.getZ()), max.getZ());
        double newW = Math.min(Math.max(u.getW(), min.getW()), max.getW());

        return new Vector4D(newX, newY, newZ, newW);
    }

    /**
     * Normalize given vector.
     *
     * @param v Vector to normalization.
     * @return Normalized vector.
     */
    public static Vector4D unitVector4D(final Vector4D v) {
        return new Vector4D(v.div(v.length()));
    }

    /**
     * Normalize given vector.
     *
     * @param v Vector to normalization.
     * @return Normalized vector.
     */
    public static Vector4D normalize(final Vector4D v) {
        return unitVector4D(v);
    }
}
