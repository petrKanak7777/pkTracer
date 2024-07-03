package cz.pk.traycer.pktracer.engine.shapes;

import cz.pk.traycer.pktracer.engine.HitRecord;
import cz.pk.traycer.pktracer.engine.Ray;
import cz.pk.traycer.pktracer.engine.math.Vector3D;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static cz.pk.traycer.pktracer.engine.Enums.Shape.SPHERE;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class Sphere extends Hittable {
    private static final double MIN_RADIUS = 0.2;

    private Vector3D position;
    private double radius;

    public Sphere() {
        this.shape = SPHERE;
        this.position = new Vector3D();
        this.radius = MIN_RADIUS;
    }

    @Override
    public boolean hit(Ray ray, float rayTMin, float rayTMax, HitRecord hitRecord) {
        return false;
    }
}
