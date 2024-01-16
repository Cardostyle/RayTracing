package Tests;
import Lights.Material;
import Maths.*;
import Shapes.Plane;
import Shapes.TestShape;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ShapeTests {
    @Test
    void defaultTransformation() {
        TestShape shape = new TestShape();
        assertEquals(Matrix.identity(4), shape.getTransformation());
    }

    @Test
    void assigningTransformation() {
        TestShape shape = new TestShape();
        Matrix t = Matrix.translate(2, 3, 4);
        shape.setTransformation(t);
        assertEquals(t, shape.getTransformation());
    }

    @Test
    void defaultMaterial() {
        TestShape shape = new TestShape();
        Material m = shape.getMaterial();
        assertEquals(new Material(), m);
    }

    @Test
    void assigningMaterial() {
        TestShape shape = new TestShape();
        Material m = new Material();
        m.setAmbient(1.0);
        shape.setMaterial(m);
        assertEquals(m, shape.getMaterial());
    }

    @Test
    void intersectingScaledShapeWithRay() {
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        TestShape shape = new TestShape();
        shape.setTransformation(Matrix.scale(2, 2, 2));
        Intersections xs = shape.intersect(ray);
        assertEquals(new Point(0, 0, -2.5), shape.getSavedRay().getOrigin());
        assertEquals(new Vector(0, 0, 0.5), shape.getSavedRay().getVector());
    }

    @Test
    void intersectingTranslatedShapeWithRay() {
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        TestShape shape = new TestShape();
        shape.setTransformation(Matrix.translate(5, 0, 0));
        Intersections xs = shape.intersect(ray);
        assertEquals(new Point(-5, 0, -5), shape.getSavedRay().getOrigin());
        assertEquals(new Vector(0, 0, 1), shape.getSavedRay().getVector());
    }

    @Test
    void computingNormalOnTranslatedShape() {
        TestShape shape = new TestShape();
        shape.setTransformation(Matrix.translate(0, 1, 0));
        Vector normal = shape.normalAt(new Point(0, 1.70711, -0.70711));
        assertEquals(new Vector(0, 0.707106, -0.70711), normal);
    }
    @Test
    void computingNormalOnTransformedShape() {
        TestShape shape = new TestShape();
        Matrix m = Matrix.scale(1, 0.5, 1).multiply(Matrix.rotateZ(0.628318));
        shape.setTransformation(m);
        Vector normal = shape.normalAt(new Point(0, 0.707106, -0.707106));
        assertEquals(new Vector(0, 0.9701425, -0.2425356), normal);
    }

    @Test
    void normalOfPlaneIsConstantEverywhere() {
        Plane plane = new Plane();
        Vector n1 = plane.localNormalAt(new Point(0, 0, 0));
        Vector n2 = plane.localNormalAt(new Point(10, 0, -10));
        Vector n3 = plane.localNormalAt(new Point(-5, 0, 150));

        Vector expectedNormal = new Vector(0, 1, 0);
        assertEquals(expectedNormal, n1);
        assertEquals(expectedNormal, n2);
        assertEquals(expectedNormal, n3);
    }

    @Test
    void intersectWithRayParallelToPlane() {
        Plane plane = new Plane();
        Ray ray = new Ray(new Point(0, 10, 0), new Vector(0, 0, 1));
        Intersections xs = plane.localIntersect(ray);

        assertEquals(0, xs.count());
    }

    @Test
    void intersectWithCoplanarRay() {
        Plane plane = new Plane();
        Ray ray = new Ray(new Point(0, 0, 0), new Vector(0, 0, 1));
        Intersections xs = plane.localIntersect(ray);

        assertEquals(0, xs.count());
    }

    @Test
    void rayIntersectingPlaneFromAbove() {
        Plane plane = new Plane();
        Ray ray = new Ray(new Point(0, 1, 0), new Vector(0, -1, 0));
        Intersections xs = plane.localIntersect(ray);

        assertEquals(1, xs.count());
        assertEquals(1, xs.get(0).getT());
        assertEquals(plane, xs.get(0).getShape());
    }

    @Test
    void rayIntersectingPlaneFromBelow() {
        Plane plane = new Plane();
        Ray ray = new Ray(new Point(0, -1, 0), new Vector(0, 1, 0));
        Intersections xs = plane.localIntersect(ray);

        assertEquals(1, xs.count());
        assertEquals(1, xs.get(0).getT());
        assertEquals(plane, xs.get(0).getShape());
    }


}
