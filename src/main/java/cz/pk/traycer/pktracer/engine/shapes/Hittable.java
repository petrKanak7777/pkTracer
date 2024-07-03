package cz.pk.traycer.pktracer.engine.shapes;

import cz.pk.traycer.pktracer.engine.Enums;
import cz.pk.traycer.pktracer.engine.HitRecord;
import cz.pk.traycer.pktracer.engine.Ray;
import lombok.AllArgsConstructor;
import lombok.Data;

import static cz.pk.traycer.pktracer.engine.HitRecord.EMPTY_MATERIAL_INDEX;
import static cz.pk.traycer.pktracer.engine.HitRecord.EMPTY_OBJECT_INDEX;

@Data
@AllArgsConstructor
public abstract class Hittable {
    protected Enums.Shape shape;
    public int objectIndex;
    public int materialIndex;

    protected Hittable() {
        shape = Enums.Shape.SPHERE;
        objectIndex = EMPTY_OBJECT_INDEX;
        materialIndex = EMPTY_MATERIAL_INDEX;
    }

    abstract boolean hit(final Ray ray, float rayTMin, float rayTMax, HitRecord hitRecord);
}
