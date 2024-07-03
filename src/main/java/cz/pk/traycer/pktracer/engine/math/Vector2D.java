package cz.pk.traycer.pktracer.engine.math;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector2D {
    private double x;
    private double y;

    public Vector2D(final Vector2D v) {
        this.x = v.getX();
        this.y = v.getY();
    }

    public Vector2D neg() {
        return new Vector2D(
                -this.x,
                -this.y
        );
    }

    public Vector2D add(final Vector2D v) {
        return new Vector2D(
                this.x + v.x,
                this.y + v.y
        );
    }

    public Vector2D sub(final Vector2D v) {
        return new Vector2D(
                this.x - v.x,
                this.y - v.y
        );
    }

    public Vector2D sub(final double t) {
        return new Vector2D(
                this.x - t,
                this.y - t
        );
    }

    public Vector2D mul(final double t) {
        return new Vector2D(
                this.x * t,
                this.y * t
        );
    }

    public Vector2D div(final double t) {
        double denominator = 1 / t;

        return new Vector2D(
                this.x * denominator,
                this.y * denominator);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y;
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public static double dot(final Vector2D u, final Vector2D v) {
        return u.getX() * v.getX() + u.getY() * v.getY();
    }

    /**
     * <a href="https://stackoverflow.com/questions/243945/calculating-a-2d-vectors-cross-product">
     * https://stackoverflow.com/questions/243945/calculating-a-2d-vectors-cross-product
     * </a>
     *
     * <p>
     * returns the magnitude of the vector that would result from a regular 3D cross product of the input vectors, taking their Z values implicitly as 0 (i.e. treating the 2D space as a plane in the 3D space). The 3D cross product will be perpendicular to that plane, and thus have 0 X & Y components (thus the scalar returned is the Z value of the 3D cross product vector).
     *
     * <p>
     * Note that the magnitude of the vector resulting from 3D cross product is also equal to the area of the parallelogram between the two vectors, which gives Implementation 1 another purpose. In addition, this area is signed and can be used to determine whether rotating from V1 to V2 moves in an counter clockwise or clockwise direction. It should also be noted that implementation 1 is the determinant of the 2x2 matrix built from these two vectors.
     *
     * @param u input 2D vector.
     * @param v input 2D vector.
     * @return double value.
     */
    public static double cross(final Vector2D u, final Vector2D v) {
        return u.getX() * v.getY() - u.getY() * v.getX();
    }

    /**
     * <a href="https://stackoverflow.com/questions/243945/calculating-a-2d-vectors-cross-product">
     * https://stackoverflow.com/questions/243945/calculating-a-2d-vectors-cross-product
     * </a>
     *
     * <p>
     * returns a vector perpendicular to the input vector still in the same 2D plane. Not a cross product in the classical sense but consistent in the "give me a perpendicular vector" sense.
     *
     * <p>
     * Note that 3D euclidean space is closed under the cross product operation--that is, a cross product of two 3D vectors returns another 3D vector. Both of the above 2D implementations are inconsistent with that in one way or another.
     *
     * @param u input 2D vector.
     * @return 2D vector.
     */
    public static Vector2D cross(final Vector2D u) {
        return new Vector2D(u.getY(), -u.getX());
    }

    public static Vector2D unitVector3D(final Vector2D v) {
        return new Vector2D(v.div(v.length()));
    }
}
