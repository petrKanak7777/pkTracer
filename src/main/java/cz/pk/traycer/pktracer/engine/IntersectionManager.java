package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.math.Vector3D;
import cz.pk.traycer.pktracer.engine.shapes.Sphere;

public class IntersectionManager {

    /**
     * Hit sphere algorithm implementation. <br>
     * <b>(bx^2 + by^2)t^2 + (2(axbx + ayby))t + (ax^2 + ay^2 - r^2) = 2</b> <br>
     * where
     * <pre>
     * a = ray origin
     * b = ray direction
     * r = radius
     * t = hit distance
     * </pre>
     *
     * @param ray
     * @param sphere
     * @param hitRecord
     */
    static void raySphereIntersection(final Ray ray, final Sphere sphere, HitRecord hitRecord) {
        Vector3D rayOrigin = ray.getOrigin().add(sphere.getPosition());
        Vector3D rayDirection = ray.getDirection();

        double a = Vector3D.dot(rayDirection, rayDirection);
        double b = 2.0 * Vector3D.dot(rayOrigin, rayDirection);
        double c = Vector3D.dot(rayOrigin, rayOrigin)
                -sphere.getRadius() * sphere.getRadius();

        // Quadratic formula discriminant: b^2 - 4ac
        double discriminant = b * b - 4.0 * a * c;

        if (discriminant < 0.0) {
            return;
        }

        // -b +- sqrt(discriminant) / 2a
        double t0 = (-b + Math.sqrt(discriminant)) / 2.0f * a;
        double closestT = (-b - Math.sqrt(discriminant)) / 2.0f * a;

        if(closestT > 0.0 && closestT < hitRecord.getDistance()) {
            hitRecord.setDistance(closestT);
            hitRecord.setObjectIndex(sphere.getObjectIndex());
            hitRecord.setMaterialIndex(sphere.getMaterialIndex());
        }
    }
}
