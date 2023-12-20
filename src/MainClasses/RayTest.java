package MainClasses;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RayTest {

    @Test
    public void testCreatingAndQueryingRay() {
        Point origin = new Point(1, 2, 3);
        Vector direction = new Vector(4, 5, 6);
        Ray ray = new Ray(origin, direction);

        Assertions.assertTrue(VectorMath.isEqual(origin, ray.getOrigin()) );
        Assertions.assertTrue(VectorMath.isEqual(direction, ray.getVector()));
    }

    @Test
    public void testCreatingAndQueryingRayFromTwoPoints() {
        Point origin = new Point(2, 3, 4);
        Point target = new Point(3, 4, 5);
        Ray ray = new Ray(origin, target);

        Assertions.assertTrue(VectorMath.isEqual(origin, ray.getOrigin()) );
        Assertions.assertTrue(VectorMath.isEqual(VectorMath.normalized(VectorMath.subtraction(target, origin)), ray.getVector()));
    }

    @Test
    public void testComputingPointFromDistance() {
        Ray ray = new Ray(new Point(2, 3, 4), new Vector(1, 0, 0));

        Assertions.assertTrue(VectorMath.isEqual(new Point(2, 3, 4), ray.pointAt(0)));
        Assertions.assertTrue(VectorMath.isEqual(new Point(3, 3, 4), ray.pointAt(1)));
        Assertions.assertTrue(VectorMath.isEqual(new Point(1, 3, 4), ray.pointAt(-1)));
        Assertions.assertTrue(VectorMath.isEqual(new Point(4.5, 3, 4), ray.pointAt(2.5)));
    }
}
