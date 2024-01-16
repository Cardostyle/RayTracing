package Tests;

import Maths.*;
import Shapes.Sphere;
import org.junit.Assert;
import org.junit.Test;

public class TransformationTest {

    @Test
    public void testTranslationOfPoint() {
        Matrix transform = Matrix.translate(5.0, -3.0, 2.0);
        Point p = new Point(-3, 4, 5);
        Point result = transform.multiply(p);
        Assert.assertEquals(new Point(2, 1, 7), result);
    }

    @Test
    public void testInverseTranslationOfPoint() {
        Matrix transform = Matrix.translate(5, -3, 2);
        Matrix inv = transform.inverse();
        Point p = new Point(-3, 4, 5);
        Point result = inv.multiply(p);
        Assert.assertEquals(new Point(-8, 7, 3), result);
    }

    @Test
    public void testTranslationDoesNotAffectVectors() {
        Matrix transform = Matrix.translate(5, -3, 2);
        Vector v = new Vector(-3, 4, 5);
        Vector result = transform.multiply(v);
        Assert.assertEquals(v, result);
    }

    @Test
    public void testScalingMatrixAppliedToPoint() {
        Matrix transform = Matrix.scale(2, 3, 4);
        Point p = new Point(-4, 6, 8);
        Point result = transform.multiply(p);
        Assert.assertEquals(new Point(-8, 18, 32), result);
    }

    @Test
    public void testScalingMatrixAppliedToVector() {
        Matrix transform = Matrix.scale(2, 3, 4);
        Vector v = new Vector(-4, 6, 8);
        Vector result = transform.multiply(v);
        Assert.assertEquals(new Vector(-8, 18, 32), result);
    }

    @Test
    public void testInverseScalingMatrixAppliedToVector() {
        Matrix transform = Matrix.scale(2, 3, 4);
        Matrix inv = transform.inverse();
        Vector v = new Vector(-4, 6, 8);
        Vector result = inv.multiply(v);
        Assert.assertEquals(new Vector(-2, 2, 2), result);
    }

    @Test
    public void testReflectionIsScalingByNegativeValue() {
        Matrix transform = Matrix.scale(-1, 1, 1);
        Point p = new Point(2, 3, 4);
        Point result = transform.multiply(p);
        Assert.assertEquals(new Point(-2, 3, 4), result);
    }

    @Test
    public void testRotationAroundXAxis() {
        Point p = new Point(0, 1, 0);
        Matrix halfQuarter = Matrix.rotateX(0.7853981);
        Matrix fullQuarter = Matrix.rotateX(1.5707963);
        Assert.assertEquals(new Point(0, 0.707106, 0.707106), halfQuarter.multiply(p));
        Assert.assertEquals(new Point(0, 0, 1), fullQuarter.multiply(p));
    }

    @Test
    public void testInverseRotationX() {
        Point p = new Point(0, 1, 0);
        Matrix halfQuarter = Matrix.rotateX(0.7853981);
        Matrix inv = halfQuarter.inverse();
        Assert.assertEquals(new Point(0, 0.707106, -0.707106), inv.multiply(p));
    }

    @Test
    public void testRotationAroundYAxis() {
        Point p = new Point(0, 0, 1);
        Matrix halfQuarter = Matrix.rotateY(0.7853981);
        Matrix fullQuarter = Matrix.rotateY(1.5707963);
        Assert.assertEquals(new Point(0.707106, 0, 0.707106), halfQuarter.multiply(p));
        Assert.assertEquals(new Point(1, 0, 0), fullQuarter.multiply(p));
    }

    @Test
    public void testRotationAroundZAxis() {
        Point p = new Point(0, 1, 0);
        Matrix halfQuarter = Matrix.rotateZ(0.7853981);
        Matrix fullQuarter = Matrix.rotateZ(1.5707963);
        Assert.assertEquals(new Point(-0.707106, 0.707106, 0), halfQuarter.multiply(p));
        Assert.assertEquals(new Point(-1, 0, 0), fullQuarter.multiply(p));
    }

    @Test
    public void testSequenceOfTransformations() {
        Point p = new Point(1, 0, 1);
        Matrix A = Matrix.rotateX(1.5707963);
        Matrix B = Matrix.scale(5, 5, 5);
        Matrix C = Matrix.translate(10, 5, 7);

        Point p2 = A.multiply(p);
        Assert.assertEquals(new Point(1, -1, 0), p2);

        Point p3 = B.multiply(p2);
        Assert.assertEquals(new Point(5, -5, 0), p3);

        Point p4 = C.multiply(p3);
        Assert.assertEquals(new Point(15, 0, 7), p4);
    }

    @Test
    public void testChainedTransformations() {
        Point p = new Point(1, 0, 1);
        Matrix A = Matrix.rotateX(1.5707963);
        Matrix B = Matrix.scale(5, 5, 5);
        Matrix C = Matrix.translate(10, 5, 7);
        Matrix transform = C.multiply(B).multiply(A);
        Assert.assertEquals(new Point(15, 0, 7), transform.multiply(p));
    }

    @Test
    public void testTranslatingARay() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Matrix transform = Matrix.translate(3, 4, 5);
        Ray ray2 = ray.transform(transform);

        Assert.assertEquals(new Point(4, 6, 8), ray2.getOrigin());
        Assert.assertEquals(new Vector(0, 1, 0), ray2.getVector());
    }

    @Test
    public void testScalingARay() {
        Ray ray = new Ray(new Point(1, 2, 3), new Vector(0, 1, 0));
        Matrix transform = Matrix.scale(2, 3, 4);
        Ray ray2 = ray.transform(transform);

        Assert.assertEquals(new Point(2, 6, 12), ray2.getOrigin());
        Assert.assertEquals(new Vector(0, 3, 0), ray2.getVector());
    }
    @Test
    public void testDefaultTransformation() {
        Sphere sphere = new Sphere();
        Matrix expected = Matrix.identity(4);
        Assert.assertEquals(expected, sphere.getTransformation());
    }

    @Test
    public void testChangingSpheresTransformation() {
        Sphere sphere = new Sphere();
        Matrix transform = Matrix.translate(2, 3, 4);
        sphere.setTransformation(transform);
        Assert.assertEquals(transform, sphere.getTransformation());
    }

    @Test
    public void testIntersectingScaledSphereWithRay() {
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Matrix transform = Matrix.scale(2, 2, 2);
        sphere.setTransformation(transform);
        Intersections xs = sphere.intersect(ray);
        Assert.assertEquals(2, xs.count());
        Assert.assertEquals(3, xs.get(0).getT(), 0.0001);
        Assert.assertEquals(7, xs.get(1).getT(), 0.0001);
    }

    @Test
    public void testIntersectingTranslatedSphereWithRay() {
        Ray ray = new Ray(new Point(0, 0, -5), new Vector(0, 0, 1));
        Sphere sphere = new Sphere();
        Matrix transform = Matrix.translate(5, 0, 0);
        sphere.setTransformation(transform);
        Intersections xs = sphere.intersect(ray);
        Assert.assertEquals(0, xs.count());
    }
}
