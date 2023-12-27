package MainClasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MaterialTest {

    @Test
    public void testLightingEyeBetweenLightAndSurface() {
        Material material = new Material();
        Point position = new Point(0, 0, 0);
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLightSource light = new PointLightSource(new Point(0, 0, -10), new Color(1, 1, 1));
        Color result = material.phongLighting(light, position, eyev, normalv);
        assertEquals(new Color(1.9, 1.9, 1.9), result);
    }

    @Test
    public void testLightingWithEyeOffset45Deg() {
        Material material = new Material();
        Point position = new Point(0, 0, 0);
        Vector eyev = new Vector(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
        Vector normalv = new Vector(0, 0, -1);
        PointLightSource light = new PointLightSource(new Point(0, 0, -10), new Color(1, 1, 1));
        Color result = material.phongLighting(light, position, eyev, normalv);
        assertEquals(new Color(1.0, 1.0, 1.0), result);
    }

    @Test
    public void testLightingWithLightOffset45Deg() {
        Material material = new Material();
        Point position = new Point(0, 0, 0);
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLightSource light = new PointLightSource(new Point(0, 10, -10), new Color(1, 1, 1));
        Color result = material.phongLighting(light, position, eyev, normalv);
        assertEquals(new Color(0.736396, 0.736396, 0.736396), result);
    }

    @Test
    public void testLightingWithEyeInPathOfReflectionVector() {
        Material material = new Material();
        Point position = new Point(0, 0, 0);
        Vector eyev = new Vector(0, -Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
        Vector normalv = new Vector(0, 0, -1);
        PointLightSource light = new PointLightSource(new Point(0, 10, -10), new Color(1, 1, 1));
        Color result = material.phongLighting(light, position, eyev, normalv);
        assertEquals(new Color(1.636197, 1.636197, 1.636197), result);
    }

    @Test
    public void testLightingWithLightBehindSurface() {
        Material material = new Material();
        Point position = new Point(0, 0, 0);
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLightSource light = new PointLightSource(new Point(0, 0, 10), new Color(1, 1, 1));
        Color result = material.phongLighting(light, position, eyev, normalv);
        assertEquals(new Color(0.1, 0.1, 0.1), result);
    }
}
