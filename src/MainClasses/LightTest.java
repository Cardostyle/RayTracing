package MainClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class LightTest {
    @Test
    public void testNormalOnTranslatedSphere() {
        Sphere sphere = new Sphere();
        Matrix transform = Matrix.translate(0, 1, 0);
        sphere.setTransformation(transform);

        Point point = new Point(0, 1.707106, -0.707106);
        Vector expected = new Vector(0, 0.707106, -0.707106);

        Vector normal = sphere.normalAt(point);

        Assertions.assertEquals(normal, expected, "Normal should be (0, 0.707106, -0.707106)");
    }

    @Test
    public void testNormalOnTransformedSphere() {
        Sphere sphere = new Sphere();
        Matrix transform = Matrix.scale(1, 0.5, 1).multiply(Matrix.rotateZ(0.62831));
        sphere.setTransformation(transform);

        Point point = new Point(0, 0.70711, -0.70711);
        Vector expected = new Vector(0, 0.970142, -0.242536);

        Vector normal = sphere.normalAt(point);

        Assertions.assertEquals(normal, expected, "Normal should be (0, 0.970142, -0.242536)");
    }

    @Test
    public void testReflectingVectorAt45Degrees() {
        Vector vector1 = new Vector(1, -1, 0);
        Vector vector2 = new Vector(0, 1, 0);

        Vector refl = vector1.reflect(vector2);
        Vector expected = new Vector(1, 1, 0);

        Assertions.assertEquals(expected, refl, "Reflecting a vector at 45 degrees should be (1, 1, 0)");
    }

    @Test
    public void testReflectingVectorOffSlantedSurface() {
        Vector vector1 = new Vector(0, -1, 0);
        Vector vector2 = new Vector(0.707011, 0.707011, 0);

        Vector refl = vector1.reflect(vector2);
        Vector expected = new Vector(1, 0, 0);

        Assertions.assertEquals(expected, refl, "Reflecting a vector off a slanted surface should be (1, 0, 0)");
    }
    @Test
    public void testPointLightHasPositionAndIntensity() {
        Color color = new Color(1, 1, 1);
        Point position = new Point(0, 0, 0);

        PointLightSource light = new PointLightSource(position, color);

        Assertions.assertEquals(position, light.getPosition(), "Light position should be (0, 0, 0)");
        Assertions.assertEquals(color, light.getColor(), "Light color should be (1, 1, 1)");
    }

    @Test
    public void testSceneCreationWithNoObjectsAndNoLights() {
        Scene scene = new Scene();

        Assertions.assertTrue(scene.getObjects().isEmpty(), "Newly created scene should contain no objects");
        Assertions.assertTrue(scene.getLights().isEmpty(), "Newly created scene should contain no lights");
    }

    @Test
    public void testAddingLightsToScene() {
        Scene scene = new Scene();
        PointLightSource light = new PointLightSource(new Point(-10, 10, -10), new Color(1, 1, 1));

        scene.addLight(light);

        Assertions.assertFalse(scene.getLights().isEmpty(), "Scene should not be empty after adding a light");
        Assertions.assertEquals(light, scene.getLights().get(0), "Scene should contain the added light");
    }

    @Test
    public void testSphereHasDefaultMaterial() {
        Sphere sphere = new Sphere();
        Material material = sphere.getMaterial();

        Assertions.assertNotNull(material, "Material should not be null");
        Assertions.assertEquals(new Material(), material, "Default material should be used");
    }

    @Test
    public void testSphereCanBeAssignedAMaterial() {
        Sphere sphere = new Sphere();
        Material material = new Material();
        material.setAmbient(1.0);

        sphere.setMaterial(material);

        Assertions.assertEquals(material, sphere.getMaterial(), "Sphere should have the assigned material");
    }

    @Test
    public void testDefaultScene() {
        Scene scene = Scene.defaultScene();
        PointLightSource expectedLight = new PointLightSource(new Point(-10, 10, -10), new Color(1, 1, 1));

        Material materialS1 = new Material();
        materialS1.setColor(new Color(0.8, 1.0, 0.6));
        materialS1.setDiffuse(0.7);
        materialS1.setSpecular(0.2);
        Sphere s1 = new Sphere();
        s1.setMaterial(materialS1);

        Sphere s2 = new Sphere();
        s2.setTransformation(Matrix.scale(0.5, 0.5, 0.5));

        Assertions.assertEquals(expectedLight, scene.getLights().get(0), "Scene should have the correct light source");
        Assertions.assertEquals(s1, scene.getObjects().get(0), "Scene should contain the first sphere");
        Assertions.assertEquals(s2, scene.getObjects().get(1), "Scene should contain the second sphere");
    }


}
