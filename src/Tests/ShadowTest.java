package Tests;

import Lights.Color;
import Lights.DirectionalLightSource;
import Lights.Material;
import Lights.PointLightSource;
import MainClasses.Scene;
import Maths.Point;
import Maths.Vector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ShadowTest {
    private Scene scene;

    @Before
    public void setUp() {
        scene = Scene.defaultScene(); // Assuming defaultScene() sets up the scene with required objects and light source
    }

    @Test
    public void testNoShadowWhenNothingIsCollinearWithPointAndLight() {
        Point point = new Point(0, 10, 0);
        boolean isShadowed = scene.isShadowed(point);
        Assert.assertFalse(isShadowed);
    }

    @Test
    public void testShadowWhenObjectIsBetweenPointAndLight() {
        Point point = new Point(10, -10, 10);
        boolean isShadowed = scene.isShadowed(point);
        Assert.assertTrue(isShadowed);
    }

    @Test
    public void testNoShadowWhenObjectIsBehindTheLight() {
        Point point = new Point(-20, 20, -20);
        boolean isShadowed = scene.isShadowed(point);
        Assert.assertFalse(isShadowed);
    }

    @Test
    public void testNoShadowWhenObjectIsBehindThePoint() {
        Point point = new Point(-2, 2, -2);
        boolean isShadowed = scene.isShadowed(point);
        Assert.assertFalse(isShadowed);
    }

    @Test
    public void testLightingWithSurfaceInShadow() {
        Material material = new Material();
        Point position = new Point(0, 0, 0);
        Vector eyev = new Vector(0, 0, -1);
        Vector normalv = new Vector(0, 0, -1);
        PointLightSource light = new PointLightSource(new Point(0, 0, -10), new Color(1, 1, 1));
        boolean isShadow = true;

        Color result = material.phongLighting(light, position, eyev, normalv, isShadow);
        Color expected = new Color(0.1, 0.1, 0.1);

        assertEquals(expected, result, "Lighting with the surface in shadow should result in color(0.1, 0.1, 0.1)");
    }

    @Test
    public void testPointLightWithStandardParameters() {
        PointLightSource light = new PointLightSource(new Point(0, 0, 0), new Color(1, 1, 1), 1.0);
        Point p = new Point(0, 1, 0);

        assertFalse(light.isDirectional());
        assertEquals(1.0, light.distanceFromPoint(p), 0.0001);
        assertEquals(new Vector(0, 1, 0), light.directionToPoint(p));
        assertEquals(new Vector(0, -1, 0), light.directionFromPoint(p));
        assertEquals(new Color(1, 1, 1), light.colorAtPoint(p));
    }

    @Test
    public void testPointLightWithCustomParameters() {
        PointLightSource light = new PointLightSource(new Point(0, 0, 0), new Color(0.2, 0.1, 0.3), 3.0);
        Point p = new Point(0, 1, 0);

        assertFalse(light.isDirectional());
        assertEquals(1.0, light.distanceFromPoint(p), 0.0001);
        assertEquals(new Vector(0, 1, 0), light.directionToPoint(p));
        assertEquals(new Vector(0, -1, 0), light.directionFromPoint(p));
        assertEquals(new Color(0.6, 0.3, 0.9), light.colorAtPoint(p));
    }

    @Test
    public void testDirectionalLightWithStandardParameters() {
        DirectionalLightSource light = new DirectionalLightSource(new Vector(0, -1, 0), new Color(1, 1, 1), 1.0);
        Point p = new Point(0, 1, 0);

        assertTrue(light.isDirectional());
        assertEquals(Double.POSITIVE_INFINITY, light.distanceFromPoint(p));
        assertEquals(new Vector(0, -1, 0), light.directionToPoint(p));
        assertEquals(new Vector(0, 1, 0), light.directionFromPoint(p));
        assertEquals(new Color(1, 1, 1), light.colorAtPoint(p));
    }

    @Test
    public void testDirectionalLightWithCustomParameters() {
        DirectionalLightSource light = new DirectionalLightSource(new Vector(0, -1, 0), new Color(0.2, 0.1, 0.3), 3.0);
        Point p = new Point(0, 1, 0);

        assertTrue(light.isDirectional());
        assertEquals(Double.POSITIVE_INFINITY, light.distanceFromPoint(p));
        assertEquals(new Vector(0, -1, 0), light.directionToPoint(p));
        assertEquals(new Vector(0, 1, 0), light.directionFromPoint(p));
        assertEquals(new Color(0.6, 0.3, 0.9), light.colorAtPoint(p));
    }
}
