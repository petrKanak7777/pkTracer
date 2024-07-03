package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.math.Vector3D;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Material {
    private Vector3D albedo;
    private float roughness;
    private float metallic;
    private Vector3D emissionColor;
    private float emissionPower;

    public Vector3D getEmission() {
        return emissionColor.mul(emissionPower);
    }
}
