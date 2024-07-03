package cz.pk.traycer.pktracer.engine.math;

/**
 * Based on this implementation: https://geosoft.no/software/matrix4x4/Matrix4x4.java.html
 */
public class Matrix4X4D {
    private static final int DIMENSION = 4;
    private static final int DIMENSIONS_SQUARED = DIMENSION * DIMENSION;

    private double elements[];

    public Matrix4X4D() {
        initialize();
        set(0.0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0,
                0, 0, 0, 0);
    }

    /**
     * Construct 4x4 matrix.
     *
     * @param m00 Value of element m[0,0].
     * @param m01 Value of element m[0,1].
     * @param m02 Value of element m[0,2].
     * @param m03 Value of element m[0,3].
     * @param m10 Value of element m[1,0].
     * @param m11 Value of element m[1,1].
     * @param m12 Value of element m[1,2].
     * @param m13 Value of element m[1,3].
     * @param m20 Value of element m[2,0].
     * @param m21 Value of element m[2,1].
     * @param m22 Value of element m[2,2].
     * @param m23 Value of element m[2,3].
     * @param m30 Value of element m[3,0].
     * @param m31 Value of element m[3,1].
     * @param m32 Value of element m[3,2].
     * @param m33 Value of element m[3,3].
     */
    public Matrix4X4D(double m00, double m01, double m02, double m03,
                      double m10, double m11, double m12, double m13,
                      double m20, double m21, double m22, double m23,
                      double m30, double m31, double m32, double m33) {
        initialize();
        set(m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33);
    }

    /**
     * Construct 4x4 matrix from matrix m.
     *
     * @param m Matrix to copy.
     */
    public Matrix4X4D(Matrix4X4D m) {
        initialize();
        set(m);
    }

    /**
     * Initialize matrix data.
     */
    private void initialize() {
        elements = new double[16];
    }

    /**
     * Make an identity matrix out of this 4x4 matrix.
     */
    public void setIdentity() {
        for (int y = 0; y < DIMENSION; y++)
            for (int x = 0; x < DIMENSION; x++)
                elements[y * DIMENSION + x] = y == x ? 1.0 : 0.0;
    }

    /**
     * Set the value of this 4x4 matrix according to the specified matrix.
     *
     * @param m Array of 16 matrix elements, m00, m01, etc.
     */
    private void set(Matrix4X4D m) {
        System.arraycopy(m.elements, 0, elements, 0, DIMENSIONS_SQUARED);
    }

    /**
     * Set the values of this 4x4 matrix.
     *
     * @param elements Array of 16 matrix elements, m00, m01, etc.
     */
    private void set(double[] elements) {
        System.arraycopy(elements, 0, this.elements, 0, DIMENSIONS_SQUARED);
    }

    /**
     * Set the values of this 4x4 matrix.
     *
     * @param m00 Value of element m[0,0].
     * @param m01 Value of element m[0,1].
     * @param m02 Value of element m[0,2].
     * @param m03 Value of element m[0,3].
     * @param m10 Value of element m[1,0].
     * @param m11 Value of element m[1,1].
     * @param m12 Value of element m[1,2].
     * @param m13 Value of element m[1,3].
     * @param m20 Value of element m[2,0].
     * @param m21 Value of element m[2,1].
     * @param m22 Value of element m[2,2].
     * @param m23 Value of element m[2,3].
     * @param m30 Value of element m[3,0].
     * @param m31 Value of element m[3,1].
     * @param m32 Value of element m[3,2].
     * @param m33 Value of element m[3,3].
     */
    private void set(double m00, double m01, double m02, double m03,
                     double m10, double m11, double m12, double m13,
                     double m20, double m21, double m22, double m23,
                     double m30, double m31, double m32, double m33) {
        elements[0] = m00;
        elements[1] = m01;
        elements[2] = m02;
        elements[3] = m03;

        elements[4] = m10;
        elements[5] = m11;
        elements[6] = m12;
        elements[7] = m13;

        elements[8] = m20;
        elements[9] = m21;
        elements[10] = m22;
        elements[11] = m23;

        elements[12] = m30;
        elements[13] = m31;
        elements[14] = m32;
        elements[15] = m33;
    }

    /**
     * Return matrix element[i,j].
     *
     * @param y Row of element to get (first row is zero).
     * @param x Column of element to get (first column is 0).
     * @return Element at specified position.
     * @throws ArrayIndexOutOfBoundsException
     */
    public double getElement(int y, int x) {
        return elements[y * DIMENSION + x];
    }

    /**
     * Return matrix element[i,j].
     *
     * @param y Row of element to get (first row is zero).
     * @param x Column of element to get (first column is 0).
     * @throws ArrayIndexOutOfBoundsException
     */
    public void setElement(int y, int x, double value) {
        elements[y * DIMENSION + x] = value;
    }

    /**
     * Add the specified 4x4 matrix to this matrix
     * and return result.
     *
     * @param m Matrix to add.
     * @return result after addition.
     */
    public Matrix4X4D add(Matrix4X4D m) {
        Matrix4X4D result = new Matrix4X4D();
        for (int y = 0; y < DIMENSION; y++) {
            for (int x = 0; x < DIMENSION; x++) {
                elements[y * DIMENSION + x] += m.elements[y * DIMENSION + x];
            }
        }
        return result;
    }

    /**
     * Add matrix m and n together
     * and return result.
     *
     * @param m First matrix to add.
     * @param n Second matrix to add.
     * @return result after m and n addition.
     */
    public static Matrix4X4D add(Matrix4X4D m, Matrix4X4D n) {
        Matrix4X4D result = new Matrix4X4D();
        for (int y = 0; y < DIMENSION; y++) {
            for (int x = 0; x < DIMENSION; x++) {
                result.setElement(y, x,
                        m.elements[y * DIMENSION + x] + n.elements[y * DIMENSION + x]);
            }
        }
        return result;
    }

    /**
     * Subtract the specified 4x4 matrix to this matrix
     * and return result.
     *
     * @param m Matrix to add.
     * @return result after addition.
     */
    public Matrix4X4D sub(Matrix4X4D m) {
        Matrix4X4D result = new Matrix4X4D();
        for (int y = 0; y < DIMENSION; y++) {
            for (int x = 0; x < DIMENSION; x++) {
                elements[y * DIMENSION + x] -= m.elements[y * DIMENSION + x];
            }
        }
        return result;
    }

    /**
     * Subtract matrix m and n and return result.
     *
     * @param m First matrix to add.
     * @param n Second matrix to add.
     * @return result after m and n addition.
     */
    public static Matrix4X4D sub(Matrix4X4D m, Matrix4X4D n) {
        Matrix4X4D result = new Matrix4X4D();
        for (int y = 0; y < DIMENSION; y++) {
            for (int x = 0; x < DIMENSION; x++) {
                result.setElement(y, x,
                        m.elements[y * DIMENSION + x] - n.elements[y * DIMENSION + x]);
            }
        }
        return result;
    }

    /**
     * Multiply the specified 4x4 matrix to this matrix
     * and return result.
     *
     * @param m Matrix to add.
     * @return result after multiplication.
     */
    public Matrix4X4D mul(Matrix4X4D m) {
        Matrix4X4D result = new Matrix4X4D();
        for (int i = 0; i < DIMENSIONS_SQUARED; i += DIMENSION) {
            for (int j = 0; j < DIMENSION; j++) {
                elements[i + j] = 0;
                for (int k = 0; k < DIMENSION; k++) {
                    elements[i + j] += elements[i + k] * elements[k * 4 + j];
                }
            }
        }

        result.set(elements);

        return result;
    }

    /**
     * Multiply matrices m and n
     * and return result.
     *
     * @param m First matrix to multiply.
     * @param n Second matrix to multiply.
     * @return result after multiplication.
     */
    public Matrix4X4D mul(Matrix4X4D m, Matrix4X4D n) {
        Matrix4X4D result = new Matrix4X4D(m);
        result.mul(n);

        return result;
    }

    public Vector4D mul(Vector4D v) {
        Vector4D result = new Vector4D();

        for (int y = 0; y < DIMENSION; y++) {
            double value = 0.0;
            for (int x = 0; x < DIMENSION; x++) {
                value += getElement(y, x) * v.getElement(x);
            }
            result.setElement(y, value);
        }

        return result;
    }

    public Vector4D mul(final double x, final double y, final double z, final double w) {
        return mul(new Vector4D(x, y, z, w));
    }

    /**
     * Invert this 4x4 matrix.
     */
    public void invert() {
        double[] tmp = new double[12];
        double[] src = new double[DIMENSIONS_SQUARED];
        double[] dst = new double[DIMENSIONS_SQUARED];

        // Transpose matrix
        for (int i = 0; i < DIMENSION; i++) {
            src[i + 0] = elements[i * 4 + 0];
            src[i + 4] = elements[i * 4 + 1];
            src[i + 8] = elements[i * 4 + 2];
            src[i + 12] = elements[i * 4 + 3];
        }

        // Calculate pairs for first 8 elements (cofactors)
        tmp[0] = src[10] * src[15];
        tmp[1] = src[11] * src[14];
        tmp[2] = src[9] * src[15];
        tmp[3] = src[11] * src[13];
        tmp[4] = src[9] * src[14];
        tmp[5] = src[10] * src[13];
        tmp[6] = src[8] * src[15];
        tmp[7] = src[11] * src[12];
        tmp[8] = src[8] * src[14];
        tmp[9] = src[10] * src[12];
        tmp[10] = src[8] * src[13];
        tmp[11] = src[9] * src[12];

        // Calculate first 8 elements (cofactors)
        dst[0] = tmp[0] * src[5] + tmp[3] * src[6] + tmp[4] * src[7];
        dst[0] -= tmp[1] * src[5] + tmp[2] * src[6] + tmp[5] * src[7];
        dst[1] = tmp[1] * src[4] + tmp[6] * src[6] + tmp[9] * src[7];
        dst[1] -= tmp[0] * src[4] + tmp[7] * src[6] + tmp[8] * src[7];
        dst[2] = tmp[2] * src[4] + tmp[7] * src[5] + tmp[10] * src[7];
        dst[2] -= tmp[3] * src[4] + tmp[6] * src[5] + tmp[11] * src[7];
        dst[3] = tmp[5] * src[4] + tmp[8] * src[5] + tmp[11] * src[6];
        dst[3] -= tmp[4] * src[4] + tmp[9] * src[5] + tmp[10] * src[6];
        dst[4] = tmp[1] * src[1] + tmp[2] * src[2] + tmp[5] * src[3];
        dst[4] -= tmp[0] * src[1] + tmp[3] * src[2] + tmp[4] * src[3];
        dst[5] = tmp[0] * src[0] + tmp[7] * src[2] + tmp[8] * src[3];
        dst[5] -= tmp[1] * src[0] + tmp[6] * src[2] + tmp[9] * src[3];
        dst[6] = tmp[3] * src[0] + tmp[6] * src[1] + tmp[11] * src[3];
        dst[6] -= tmp[2] * src[0] + tmp[7] * src[1] + tmp[10] * src[3];
        dst[7] = tmp[4] * src[0] + tmp[9] * src[1] + tmp[10] * src[2];
        dst[7] -= tmp[5] * src[0] + tmp[8] * src[1] + tmp[11] * src[2];

        // Calculate pairs for second 8 elements (cofactors)
        tmp[0] = src[2] * src[7];
        tmp[1] = src[3] * src[6];
        tmp[2] = src[1] * src[7];
        tmp[3] = src[3] * src[5];
        tmp[4] = src[1] * src[6];
        tmp[5] = src[2] * src[5];
        tmp[6] = src[0] * src[7];
        tmp[7] = src[3] * src[4];
        tmp[8] = src[0] * src[6];
        tmp[9] = src[2] * src[4];
        tmp[10] = src[0] * src[5];
        tmp[11] = src[1] * src[4];

        // Calculate second 8 elements (cofactors)
        dst[8] = tmp[0] * src[13] + tmp[3] * src[14] + tmp[4] * src[15];
        dst[8] -= tmp[1] * src[13] + tmp[2] * src[14] + tmp[5] * src[15];
        dst[9] = tmp[1] * src[12] + tmp[6] * src[14] + tmp[9] * src[15];
        dst[9] -= tmp[0] * src[12] + tmp[7] * src[14] + tmp[8] * src[15];
        dst[10] = tmp[2] * src[12] + tmp[7] * src[13] + tmp[10] * src[15];
        dst[10] -= tmp[3] * src[12] + tmp[6] * src[13] + tmp[11] * src[15];
        dst[11] = tmp[5] * src[12] + tmp[8] * src[13] + tmp[11] * src[14];
        dst[11] -= tmp[4] * src[12] + tmp[9] * src[13] + tmp[10] * src[14];
        dst[12] = tmp[2] * src[10] + tmp[5] * src[11] + tmp[1] * src[9];
        dst[12] -= tmp[4] * src[11] + tmp[0] * src[9] + tmp[3] * src[10];
        dst[13] = tmp[8] * src[11] + tmp[0] * src[8] + tmp[7] * src[10];
        dst[13] -= tmp[6] * src[10] + tmp[9] * src[11] + tmp[1] * src[8];
        dst[14] = tmp[6] * src[9] + tmp[11] * src[11] + tmp[3] * src[8];
        dst[14] -= tmp[10] * src[11] + tmp[2] * src[8] + tmp[7] * src[9];
        dst[15] = tmp[10] * src[10] + tmp[4] * src[8] + tmp[9] * src[9];
        dst[15] -= tmp[8] * src[9] + tmp[11] * src[10] + tmp[5] * src[8];

        // Calculate determinant
        double det = src[0] * dst[0] + src[1] * dst[1] + src[2] * dst[2] + src[3] * dst[3];

        // Calculate matrix inverse
        det = 1.0 / det;
        for (int i = 0; i < 16; i++)
            elements[i] = dst[i] * det;
    }

    public static Matrix4X4D inverse(Matrix4X4D m) {
        Matrix4X4D result = new Matrix4X4D(m);
        result.invert();
        return result;
    }

    /**
     * Apply specified translation to this 4x4 matrix.
     *
     * @param dx X translation.
     * @param dy Y translation.
     * @param dz Z translation.
     */
    public void translate(double dx, double dy, double dz) {
        Matrix4X4D translationMatrix = new Matrix4X4D();

        translationMatrix.setElement(3, 0, dx);
        translationMatrix.setElement(3, 1, dy);
        translationMatrix.setElement(3, 2, dz);

        mul(translationMatrix);
    }

    /**
     * Compute right-handed coordinate system, row-oriented view matrix. Based on: <br>
     * <a href="https://stackoverflow.com/questions/349050/calculating-a-lookat-matrix">
     *      https://stackoverflow.com/questions/349050/calculating-a-lookat-matrix</a>
     * <br>
     * <a href="https://stackoverflow.com/questions/19740463/lookat-function-im-going-crazy">
     *    https://stackoverflow.com/questions/19740463/lookat-function-im-going-crazy</a>
     * <br>
     * <a href="http://www.songho.ca/opengl/gl_transform.html">
     *   http://www.songho.ca/opengl/gl_transform.html</a>
     * .
     * <p>
     * forward = normal(center - eye) <br>
     * up = normal(cross(up, forward)) <br>
     * right = cross(forward, up) <br>
     *
     * <p>
     * up.x          right.x          forward.x          0 <br>
     * up.y          right.y          forward.y          0 <br>
     * up.z          right.z          forward.z          0 <br>
     * -dot(up, eye) -dot(right, eye) -dot(forward, eye) 1 <br>
     *
     * @param eye Camera position.
     * @param center Point where camera look at.
     * @param up Up vector.
     * @return 4x4 view matrix.
     */
    public static Matrix4X4D lookAt(final Vector3D eye, final Vector3D center, final Vector3D up) {
        Vector3D forward = Vector3D.normalize(center.sub(eye));
        Vector3D normUp = Vector3D.normalize(up);
        Vector3D right = Vector3D.normalize(Vector3D.cross(forward, normUp));
        normUp = Vector3D.cross(right, forward);

        Matrix4X4D result = new Matrix4X4D();
        result.setIdentity();

        result.setElement(0, 0, right.getX());
        result.setElement(1, 0, right.getY());
        result.setElement(2, 0, right.getZ());
        result.setElement(0, 1, normUp.getX());
        result.setElement(1, 1, normUp.getY());
        result.setElement(2, 1, normUp.getZ());
        result.setElement(0, 2, -forward.getX());
        result.setElement(1, 2, -forward.getY());
        result.setElement(2, 2, -forward.getZ());
        result.setElement(3, 0, -Vector3D.dot(right, eye));
        result.setElement(3, 1, -Vector3D.dot(normUp, eye));
        result.setElement(3, 2, Vector3D.dot(forward, eye));

        return result;
    }

    /**
     * Compute right-handed row-oriented perspective matrix. Based on: <br>
     * <a href="https://github.com/g-truc/glm/blob/0.9.5/glm/gtc/matrix_transform.inl#L207-L229">
     * https://github.com/g-truc/glm/blob/0.9.5/glm/gtc/matrix_transform.inl#L207-L229</a>
     * <br>
     *
     * @param fov    Field of view in radians.
     * @param aspect Aspect ratio (width / height, for example: 1920 / 1080)
     * @param near   Near clipping plane
     * @param far    Far clipping plane
     * @return 4x4 projection matrix.
     */
    public static Matrix4X4D perspectiveFov(final double fov, final double aspect,
                                     double near, double far) {
        if (aspect == 0) throw new IllegalArgumentException("aspect must be non-zero");
        if (far == near) throw new IllegalArgumentException("far can't be same like near");

        double tanHalfFov = Math.tan(fov / 2.0);

        Matrix4X4D result = new Matrix4X4D();

        result.setElement(0, 0, 1.0 / (aspect * tanHalfFov));
        result.setElement(1, 1, 1.0 / tanHalfFov);
        result.setElement(2, 2, -(far + near) / (far - near));
        result.setElement(2, 3, -1.0);
        result.setElement(3, 2, (-2.0 * far * near) / (far - near));

        return result;
    }

    // todo:
    // rotateX
    // rotateY
    // rotateZ
    // scale
}
