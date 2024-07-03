package cz.pk.traycer.pktracer.engine.math;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector3D {
    private double x;
    private double y;
    private double z;

    public Vector3D() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    public Vector3D(final double d) {
        this.x = d;
        this.y = d;
        this.z = d;
    }

    public Vector3D(final Vector3D v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    public Vector3D(final Vector4D v) {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    public double getElement(final int index) {
        return switch (index) {
            case 0 -> x;
            case 1 -> y;
            case 2 -> z;
            default -> throw new IllegalArgumentException(
                    String.format("Index out of bounds. Fill 0 to 2 as index value. Index value=%d", index));
        };
    }

    public void setElement(final int index, final double value) {
        switch (index) {
            case 0 -> x = value;
            case 1 -> y = value;
            case 2 -> z = value;
            default -> throw new IllegalArgumentException(
                    String.format("Index out of bounds. Fill 0 to 3 as index value. Index value=%d", index));
        }
    }

    public Vector3D neg() {
        return new Vector3D(
                -this.x,
                -this.y,
                -this.z
        );
    }

    public Vector3D add(final Vector3D v) {
        return new Vector3D(
                this.x + v.getX(),
                this.y + v.getY(),
                this.z + v.getZ()
        );
    }

    public Vector3D sub(final Vector3D v) {
        return new Vector3D(
                this.x - v.getX(),
                this.y - v.getY(),
                this.z - v.getZ()
        );
    }

    public Vector3D mul(final double t) {
        return new Vector3D(
                this.x * t,
                this.y * t,
                this.z * t
        );
    }

    public Vector3D mul(final Vector3D v) {
        return new Vector3D(
                this.x * v.getX(),
                this.y * v.getY(),
                this.z * v.getZ()
        );
    }

    public Vector3D div(final double t) {
        double denominator = 1 / t;
        return new Vector3D(
                this.x * denominator,
                this.y * denominator,
                this.z * denominator
        );
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public static double dot(final Vector3D u, final Vector3D v) {
        return u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ();
    }

    public static Vector3D cross(final Vector3D u, final Vector3D v) {
        return new Vector3D(u.getY() * v.getZ() - u.getZ() * v.getY(),
                u.getZ() * v.getX() - u.getX() * v.getZ(),
                u.getX() * v.getY() - u.getY() * v.getX());
    }

    /**
     * Normalize given vector.
     *
     * @param v Vector to normalization.
     * @return Normalized vector.
     */
    public static Vector3D unitVector3D(final Vector3D v) {
        return new Vector3D(v.div(v.length()));
    }

    /**
     * Normalize given vector.
     *
     * @param v Vector to normalization.
     * @return Normalized vector.
     */
    public static Vector3D normalize(final Vector3D v) {
        return unitVector3D(v);
    }
}
