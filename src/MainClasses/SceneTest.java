package MainClasses;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class SceneTest {

    @Test
    public void sceneContainsNoObjectsWhenCreated() {
        Scene scene = new Scene();
        assertTrue("Newly created scene should contain no objects", scene.getObjects().isEmpty());
    }

    @Test
    public void sceneContainsSphereWhenAdded() {
        Scene scene = new Scene();
        Sphere sphere = new Sphere();
        scene.addObject(sphere);
        assertFalse("MainClasses.Scene should not be empty after adding a sphere", scene.getObjects().isEmpty());
        assertTrue("MainClasses.Scene should contain the added sphere", scene.getObjects().contains(sphere));
    }
    @Test
    public void intersectSceneWithRay() {
        // Erstellen der Szene und des Strahls
        Scene scene = Scene.defaultScene();
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));

        // Berechnen der Schnittpunkte
        Intersections xs = scene.traceRay(ray);

        // Überprüfen der Anzahl der Schnittpunkte
        assertEquals("Anzahl der Schnittpunkte sollte 4 sein", 4, xs.count());

        // Überprüfen der spezifischen t-Werte
        assertEquals("t-Wert des ersten Schnittpunkts sollte 4 sein", 4, xs.get(1).getT(), 0.0);
        assertEquals("t-Wert des zweiten Schnittpunkts sollte 4.5 sein", 4.5, xs.get(1).getT(), 0.0);
        assertEquals("t-Wert des dritten Schnittpunkts sollte 5.5 sein", 5.5, xs.get(3).getT(), 0.0);
        assertEquals("t-Wert des vierten Schnittpunkts sollte 6 sein", 6, xs.get(4).getT(), 0.0);
    }
    @Test
    public void hitWhenAllIntersectionsHavePositiveT() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(1, sphere);
        Intersection i2 = new Intersection(2, sphere);
        Intersections xs = new Intersections(new Intersection[] {i2, i1});
        Intersection i = xs.hit();
        assertEquals("Hit should be the first intersection", i1, i);
    }

    @Test
    public void hitWhenSomeIntersectionsHaveNegativeT() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(-1, sphere);
        Intersection i2 = new Intersection(1, sphere);
        Intersections xs = new Intersections(new Intersection[] {i2, i1});
        Intersection i = xs.hit();
        assertEquals("Hit should be the second intersection", i2, i);
    }

    @Test
    public void hitWhenAllIntersectionsHaveNegativeT() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(-2, sphere);
        Intersection i2 = new Intersection(-1, sphere);
        Intersections xs = new Intersections(new Intersection[] {i2, i1});
        Intersection i = xs.hit();
        assertNull("There should be no hit", i);
    }

    @Test
    public void hitIsLowestNonNegativeIntersection() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(5, sphere);
        Intersection i2 = new Intersection(7, sphere);
        Intersection i3 = new Intersection(-3, sphere);
        Intersection i4 = new Intersection(2, sphere);
        Intersections xs = new Intersections(new Intersection[] {i1, i2, i3, i4});
        Intersection i = xs.hit();
        assertEquals("Hit should be the lowest non-negative intersection", i4, i);
    }
    @Test
    public void viewTransformationForDefaultOrientation() {
        Point position = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, -1);
        Vector up = new Vector(0, 1, 0);
        Matrix transform = Matrix.viewTransform(position, lookAt, up);
        assertEquals("Transform should be the identity matrix", Matrix.identity(4), transform);
    }

    @Test
    public void viewTransformationLookingInPositiveZDirection() {
        Point position = new Point(0, 0, 0);
        Point lookAt = new Point(0, 0, 1);
        Vector up = new Vector(0, 1, 0);
        Matrix transform = Matrix.viewTransform(position, lookAt, up);
        Matrix expected = Matrix.scale(-1, 1, -1);
        assertEquals("Transform should be a scaling matrix", expected, transform);
    }

    @Test
    public void viewTransformationMovesTheWorld() {
        Point position = new Point(0, 0, 8);
        Point lookAt = new Point(0, 0, 0);
        Vector up = new Vector(0, 1, 0);
        Matrix transform = Matrix.viewTransform(position, lookAt, up);
        Matrix expected = Matrix.translate(0, 0, -8);
        assertEquals("Transform should be a translation matrix", expected, transform);
    }

    @Test
    public void arbitraryViewTransformation() {
        Point position = new Point(1, 3, 2);
        Point lookAt = new Point(4, -2, 8);
        Vector up = new Vector(1, 1, 0);
        Matrix transform = Matrix.viewTransform(position, lookAt, up);
        Matrix expected = new Matrix(
                new double[][] {
                        {-0.50709, 0.50709, 0.67612, -2.36643},
                        {0.76772, 0.60609, 0.12122, -2.82843},
                        {-0.35857, 0.59761, -0.71714, 0.00000},
                        {0.00000, 0.00000, 0.00000, 1.00000}
                }
        );
        assertEquals("Transform should match the expected matrix", expected, transform);
    }
}
