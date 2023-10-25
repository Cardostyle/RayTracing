import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private static final double DELTA = 1e-9; // Toleranz für Gleitkommazahlen

    @Test
    public void testSubtractingTwoPoints() {
        Point point1 = new Point(3, 2, 1);
        Point point2 = new Point(5, 6, 7);
        Vector result = VectorMath.subtraction(point1, point2);
        assertEquals(new Vector(-2.0, -4.0, -6.0), result);
    }

    @Test
    public void testAddingVectorToPoint() {
        Point point = new Point(3.0, -2.0, 5.0);
        Vector vector = new Vector(-2.0, 3.0, 1.0);
        Point result = VectorMath.addition(point, vector);
        assertEquals(new Point(1.0, 1.0, 6.0), result);
    }

    @Test
    public void testSubtractingVectorFromPoint() {
        Point point = new Point(3, 2, 1);
        Vector vector = new Vector(5, 6, 7);
        Point result = VectorMath.subtraction(point, vector);
        assertEquals(new Point(-2.0, -4.0, -6.0), result);
    }

    @Test
    public void testScalingAPoint() {
        Point point = new Point(0.1, 0.2, 0.3);
        double value = 0.3;
        Point result = VectorMath.multiplication(point, value);
        assertEquals(new Point(0.03, 0.06, 0.09), result);
    }
    @Test
    public void testIllegalDivisionByZeroForPoints() {
        Point point = new Point(0.1, 0.2, 0.3);
        try {
            Point result = VectorMath.division(point, 0.0);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testIllegalDivisionByZeroForVectors() {
        Vector vector = new Vector(0.1, 0.2, 0.3);
        try {
            Vector result = VectorMath.division(vector, 0.0);
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testDotProductOfTwoVectors() {
        Vector vector1 = new Vector(1, 2, 3);
        Vector vector2 = new Vector(2, 3, 4);
        double result = VectorMath.dot(vector1, vector2);
        assertEquals(20.0, result, DELTA);
    }

    @Test
    public void testNegatingAVector() {
        Vector vector = new Vector(0.1, 2.3, 9.6);
        Vector result = VectorMath.negate(vector);
        assertEquals(new Vector(-0.1, -2.3, -9.6), result);
    }

    @Test
    public void testAddingTwoVectors() {
        Vector vector1 = new Vector(3.0, -2.0, 5.0);
        Vector vector2 = new Vector(-2.0, 3.0, 1.0);
        Vector result = VectorMath.addition(vector1, vector2);
        assertEquals(new Vector(1.0, 1.0, 6.0), result);
    }

    @Test
    public void testSubtractingTwoVectors() {
        Vector vector1 = new Vector(3, 2, 1);
        Vector vector2 = new Vector(5, 6, 7);
        Vector result = VectorMath.subtraction(vector1, vector2);
        assertEquals(new Vector(-2.0, -4.0, -6.0), result);
    }

}
