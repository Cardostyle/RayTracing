import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StrahlTest {

    @Test
    public void testCreatingAndQueryingRay() {
        Point origin = new Point(1, 2, 3);
        Vector direction = new Vector(4, 5, 6);
        Strahl ray = new Strahl(origin, direction);

        assertTrue(VectorMath.isEqual(origin, ray.getOrigin()) );
        assertTrue(VectorMath.isEqual(direction, ray.getVector()));
    }

    @Test
    public void testCreatingAndQueryingRayFromTwoPoints() {
        Point origin = new Point(2, 3, 4);
        Point target = new Point(3, 4, 5);
        Strahl ray = new Strahl(origin, target);

        assertTrue(VectorMath.isEqual(origin, ray.getOrigin()) );
        assertTrue(VectorMath.isEqual(VectorMath.normalized(VectorMath.subtraction(target, origin)), ray.getVector()));
    }

    @Test
    public void testComputingPointFromDistance() {
        Strahl ray = new Strahl(new Point(2, 3, 4), new Vector(1, 0, 0));

        assertTrue(VectorMath.isEqual(new Point(2, 3, 4), ray.pointAt(0)));
        assertTrue(VectorMath.isEqual(new Point(3, 3, 4), ray.pointAt(1)));
        assertTrue(VectorMath.isEqual(new Point(1, 3, 4), ray.pointAt(-1)));
        assertTrue(VectorMath.isEqual(new Point(4.5, 3, 4), ray.pointAt(2.5)));
    }
}
