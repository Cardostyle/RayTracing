package MainClasses;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
public class IntersectionSphereTest {

    @Test
    public void testIntersectionProperties() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(3.5, sphere);

        assertEquals(3.5, i1.getT(), 0.0001); // Überprüft, ob t den erwarteten Wert hat
        assertSame(sphere, i1.getShape());    // Überprüft, ob shape das erwartete Objekt ist
    }

    @Test
    public void testIntersectionComparison() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(3.5, sphere);
        Intersection i2 = new Intersection(0.3, sphere);

        assertTrue(i1.compareTo(i2) > 0); // Überprüft, ob i1 größer als i2 ist
    }
    @Test
    public void testAggregatingIntersections() {
        Sphere sphere = new Sphere();
        Intersection i1 = new Intersection(1, sphere);
        Intersection i2 = new Intersection(2, sphere);

        Intersections xs = new Intersections(new Intersection[]{i1, i2});

        assertEquals(2, xs.count());
        assertEquals(1.0, xs.get(0).getT(), 0.0);
        assertEquals(2.0, xs.get(1).getT(), 0.0);
    }

    @Test
    public void testRayIntersectsSphereAtTwoPoints() {
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Intersections xs = sphere.intersect(ray);

        assertEquals(2, xs.count());
        assertEquals(4.0, xs.get(0).getT(), 0.0);
        assertEquals(6.0, xs.get(1).getT(), 0.0);
    }

    @Test
    public void testIntersectSetsObjectOnIntersection() {
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Intersections xs = sphere.intersect(ray);

        assertEquals(2, xs.count());
        assertSame(sphere, xs.get(0).getShape());
        assertSame(sphere, xs.get(1).getShape());
    }

    @Test
    public void testRayIntersectsSphereAtTangent() {
        Ray ray = new Ray(new Point(0, 1, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Intersections xs = sphere.intersect(ray);

        assertEquals(2, xs.count());
        assertEquals(5.0, xs.get(0).getT(), 0.0);
        assertEquals(5.0, xs.get(1).getT(), 0.0);
    }

    @Test
    public void testRayMissesSphere() {
        Ray ray = new Ray(new Point(0, 2, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Intersections xs = sphere.intersect(ray);

        assertEquals(0, xs.count());
    }

    @Test
    public void testRayOriginatesInsideSphere() {
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Intersections xs = sphere.intersect(ray);

        assertEquals(2, xs.count());
        assertEquals(-1.0, xs.get(0).getT(), 0.0);
        assertEquals(1.0, xs.get(1).getT(), 0.0);
    }

    @Test
    public void testSphereIsBehindRay() {
        Ray ray = new Ray(new Point(0, 0, 5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Intersections xs = sphere.intersect(ray);

        assertEquals(2, xs.count());
        assertEquals(-6.0, xs.get(0).getT(), 0.0);
        assertEquals(-4.0, xs.get(1).getT(), 0.0);
    }

    @Test
    public void testNormalOnSphereAtPointOnXAxis() {
        Sphere sphere = new Sphere();
        Vector n = sphere.normalAt(new Point(1, 0, 0));
        Vector expected = new Vector(1, 0, 0);

        assertEquals(expected, n);
        assertEquals(expected, n.normalize());
    }

    @Test
    public void testNormalOnSphereAtPointOnYAxis() {
        Sphere sphere = new Sphere();
        Vector n = sphere.normalAt(new Point(0, 1, 0));
        Vector expected = new Vector(0, 1, 0);

        assertEquals(expected, n);
        assertEquals(expected, n.normalize());
    }

    @Test
    public void testNormalOnSphereAtPointOnZAxis() {
        Sphere sphere = new Sphere();
        Vector n = sphere.normalAt(new Point(0, 0, 1));
        Vector expected = new Vector(0, 0, 1);

        assertEquals(expected, n);
        assertEquals(expected, n.normalize());
    }

    @Test
    public void testNormalOnSphereAtNonaxialPoint() {
        Sphere sphere = new Sphere();
        double value = Math.sqrt(1.0 / 3.0);
        Vector n = sphere.normalAt(new Point(value, value, value));
        Vector expected = new Vector(value, value, value);

        assertEquals(expected, n);
        assertEquals(expected, n.normalize());
    }

}
