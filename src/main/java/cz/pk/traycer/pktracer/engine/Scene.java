package cz.pk.traycer.pktracer.engine;

import cz.pk.traycer.pktracer.engine.shapes.Hittable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Scene {
    private List<Hittable> models;
    private List<Material> materials;
}
