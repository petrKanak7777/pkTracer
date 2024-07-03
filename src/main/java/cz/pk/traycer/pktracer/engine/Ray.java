package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.math.Vector3D;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ray {
    private Vector3D origin;
    private Vector3D direction;

    public Ray() {
        origin = new Vector3D();
        direction = new Vector3D();
    }
}
