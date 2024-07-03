package cz.pk.traycer.pktracer.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pixel {
    int r;
    int g;
    int b;
    int a;

    public Pixel(double r, double g, double b, double a) {
        this.r = (int) r;
        this.g = (int) g;
        this.b = (int) b;
        this.a = (int) a;
    }
}
