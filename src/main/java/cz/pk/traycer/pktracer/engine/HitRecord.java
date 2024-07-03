package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.math.Vector3D;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HitRecord {

    public static final int EMPTY_OBJECT_INDEX = -1;
    public static final int EMPTY_MATERIAL_INDEX = -1;
    public static final double MAX_DISTANCE_VALUE = Double.MAX_VALUE;

    private int objectIndex;
    private int materialIndex;
    private double distance;
    Vector3D worldPosition;
    Vector3D worldNormal;

    public HitRecord() {
        objectIndex = EMPTY_OBJECT_INDEX;
        materialIndex = EMPTY_MATERIAL_INDEX;
        distance = MAX_DISTANCE_VALUE;
        worldPosition = new Vector3D();
        worldNormal = new Vector3D();
    }

    public void restart() {
        new HitRecord();
    }
}
