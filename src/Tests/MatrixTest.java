package Tests;

import static org.junit.jupiter.api.Assertions.*;

import Maths.Matrix;
import Maths.Point;
import Maths.Vector;
import org.junit.jupiter.api.Test;

public class MatrixTest {

        @Test
        public void test4x4Matrix() {
            double[][] data = {
                    {1, 0, 0, 4},
                    {0, 0, 7.5, 0},
                    {0, 0, 11, 0},
                    {13.5, 0, 15.5, 0}
            };
            Matrix m = new Matrix(data);

            assertEquals(1, m.get(0, 0), 0);
            assertEquals(4, m.get(0, 3), 0);
            assertEquals(7.5, m.get(1, 2), 0);
            assertEquals(11, m.get(2, 2), 0);
            assertEquals(13.5, m.get(3, 0), 0);
            assertEquals(15.5, m.get(3, 2), 0);
        }

        @Test
        public void test3x3Matrix() {
            double[][] data = {
                    {-3, 0, 0},
                    {0, -2, 0},
                    {0, 0, 1}
            };
            Matrix m = new Matrix(data);

            assertEquals(-3, m.get(0, 0), 0);
            assertEquals(-2, m.get(1, 1), 0);
            assertEquals(1, m.get(2, 2), 0);
        }

    @Test
    public void test4x4MatrixWithSet() {
        Matrix m = new Matrix(4);
        m.set(0, 0, 1);
        m.set(0, 3, 4);
        m.set(1, 0, 5.5);
        m.set(1, 2, 7.5);
        m.set(2, 2, 11);
        m.set(3, 0, 13.5);
        m.set(3, 2, 15.5);

        assertEquals(1, m.get(0, 0), 0);
        assertEquals(4, m.get(0, 3), 0);
        assertEquals(5.5, m.get(1, 0), 0);
        assertEquals(7.5, m.get(1, 2), 0);
        assertEquals(11, m.get(2, 2), 0);
        assertEquals(13.5, m.get(3, 0), 0);
        assertEquals(15.5, m.get(3, 2), 0);
    }

    @Test
    public void test2x2MatrixWithSet() {
        Matrix m = new Matrix(2);
        m.set(0, 0, -3);
        m.set(0, 1, 5);
        m.set(1, 0, 1);
        m.set(1, 1, -2);

        assertEquals(-3, m.get(0, 0), 0);
        assertEquals(5, m.get(0, 1), 0);
        assertEquals(1, m.get(1, 0), 0);
        assertEquals(-2, m.get(1, 1), 0);
    }
    @Test
    public void testMatrixEqualityWithIdenticalMatrices() {
        double[][] mData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };
        Matrix m = new Matrix(mData);

        double[][] bData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };
        Matrix b = new Matrix(bData);

        assertEquals(m, b);
    }

    @Test
    public void testMatrixEqualityWithDifferentMatrices() {
        double[][] mData = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        };
        Matrix m = new Matrix(mData);

        double[][] bData = {
                {2, 3, 4, 5},
                {6, 7, 8, 9},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };
        Matrix b = new Matrix(bData);

        assertNotEquals(m, b);
    }

    @Test
    public void testMultiplyingTwoMatrices() {
        Matrix m = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 8, 7, 6},
                {5, 4, 3, 2}
        });

        Matrix b = new Matrix(new double[][]{
                {-2, 1, 2, 3},
                {3, 2, 1, -1},
                {4, 3, 6, 5},
                {1, 2, 7, 8}
        });

        Matrix expected = new Matrix(new double[][]{
                {20, 22, 50, 48},
                {44, 54, 114, 108},
                {40, 58, 110, 102},
                {16, 26, 46, 42}
        });

        Matrix result = m.multiply(b);
        assertEquals(expected, result);
    }

    @Test
    public void testMultiplyingMatrixWithIdentityMatrix() {
        Matrix identity = Matrix.identity(4);
        Matrix b = new Matrix(new double[][]{
                {20, 22, 50, 48},
                {44, 54, 114, 108},
                {40, 58, 110, 102},
                {16, 26, 46, 42}
        });

        Matrix result1 = identity.multiply(b);
        Matrix result2 = b.multiply(identity);

        assertEquals(b, result1);
        assertEquals(b, result2);
    }

    @Test
    public void testMultiplicationWithUnequalSize() {
        Matrix a = new Matrix(new double[][]{
                {1, 2},
                {3, 4}
        });

        Matrix b = new Matrix(new double[][]{
                {5, 6, 7},
                {8, 9, 10},
                {11, 12, 13}
        });

        // This should throw an IllegalArgumentException because the matrices cannot be multiplied
        assertThrows(IllegalArgumentException.class, () -> a.multiply(b));
    }

    @Test
    public void testMatrixMultipliedByVector() {
        Matrix m = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {2, 4, 4, 2},
                {8, 6, 4, 1},
                {0, 0, 0, 1}
        });
        Vector v = new Vector(1, 2, 3, 1);
        Vector expected = new Vector(18, 24, 33, 1);
        Vector result = m.multiply(v);
        System.out.println(expected+" "+result);
        assertEquals(result, expected);
    }

    @Test
    public void testIdentityMatrixMultipliedByVector() {
        Matrix identity = Matrix.identity(4);
        Vector v = new Vector(1, 2, 3, 1);
        Vector result = identity.multiply(v);
        System.out.println(v+" "+result);
        assertEquals(v, result);
    }

    @Test
    public void testMatrixMultipliedByPoint() {
        Matrix m = new Matrix(new double[][]{
                {1, 2, 3, 4},
                {2, 4, 4, 2},
                {8, 6, 4, 1},
                {0, 0, 0, 1}
        });
        Point p = new Point(1, 2, 3); // Assuming the Maths.Point class has a constructor Maths.Point(x, y, z)
        Point expected = new Point(18, 24, 33);
        Point result = m.multiply(p);
        assertEquals(result, expected);
    }

    @Test
    public void testIdentityMatrixMultipliedByPoint() {
        Matrix identity = Matrix.identity(4);
        Point p = new Point(1, 2, 3);
        Point result = identity.multiply(p);
        assertEquals(p, result);
    }

    @Test
    public void testTransposingMatrix() {
        Matrix m = new Matrix(new double[][]{
                {0, 9, 3, 0},
                {9, 8, 0, 8},
                {1, 8, 5, 3},
                {0, 0, 5, 8}
        });

        Matrix expectedTranspose = new Matrix(new double[][]{
                {0, 9, 1, 0},
                {9, 8, 8, 0},
                {3, 0, 5, 5},
                {0, 8, 3, 8}
        });

        Matrix resultTranspose = m.transpose();

        for (int i = 0; i < m.getSize(); i++) {
            assertArrayEquals(expectedTranspose.getRow(i), resultTranspose.getRow(i), 0.0001);
        }
    }

    @Test
    public void testTransposingIdentityMatrix() {
        Matrix identity = Matrix.identity(4);
        Matrix resultTranspose = identity.transpose();

        for (int i = 0; i < identity.getSize(); i++) {
            assertArrayEquals(identity.getRow(i), resultTranspose.getRow(i), 0.0001);
        }
    }

    @Test
    public void testDeterminant2x2() {
        Matrix m = new Matrix(new double[][]{
                {1, 5},
                {-3, 2}
        });
        assertEquals(17, m.determinant(), 0.001);
    }

    @Test
    public void testSubmatrix3x3() {
        Matrix m = new Matrix(new double[][]{
                {1, 5, 0},
                {-3, 2, 7},
                {0, 6, -3}
        });
        Matrix expectedSubmatrix = new Matrix(new double[][]{
                {-3, 2},
                {0, 6}
        });
        Matrix actualSubmatrix = m.subMatrix(0, 2);
        assertEquals(expectedSubmatrix, actualSubmatrix); ;
    }

    @Test
    public void testSubmatrix4x4() {
        Matrix m = new Matrix(new double[][]{
                {-6, 1, 1, 6},
                {-8, 5, 8, 6},
                {-1, 0, 8, 2},
                {-7, 1, -1, 1}
        });
        Matrix expectedSubmatrix = new Matrix(new double[][]{
                {-6, 1, 6},
                {-8, 8, 6},
                {-7, -1, 1}
        });
        Matrix actualSubmatrix = m.subMatrix(2, 1);
        assertEquals(expectedSubmatrix, actualSubmatrix); ;
    }

    @Test
    public void testMinor3x3() {
        Matrix m = new Matrix(new double[][]{
                {3, 5, 0},
                {2, -1, -7},
                {6, -1, 5}
        });
        assertEquals(25, m.minor(1, 0), 0.001);
    }

    @Test
    public void testCofactor3x3() {
        Matrix m = new Matrix(new double[][]{
                {3, 5, 0},
                {2, -1, -7},
                {6, -1, 5}
        });
        assertEquals(-12, m.cofactor(0, 0), 0.001);
        assertEquals(-25, m.cofactor(1, 0), 0.001);
    }

    @Test
    public void testDeterminant3x3() {
        Matrix m = new Matrix(new double[][]{
                {1, 2, 6},
                {-5, 8, -4},
                {2, 6, 4}
        });
        assertEquals(-196, m.determinant(), 0.001);
    }

    @Test
    public void testDeterminant4x4() {
        Matrix m = new Matrix(new double[][]{
                {-2, -8, 3, 5},
                {-3, 1, 7, 3},
                {1, 2, -9, 6},
                {-6, 7, 7, -9}
        });
        assertEquals(-4071, m.determinant(), 0.001);
    }

    @Test
    public void testInvertibleMatrix() {
        Matrix m = new Matrix(new double[][]{
                {6, 4, 4, 4},
                {5, 5, 7, 6},
                {4, -9, 3, -7},
                {9, 1, 7, -6}
        });

        double det = m.determinant();
        assertEquals(-2120, det, 1e-9);
    }

    @Test
    public void testNonInvertibleMatrix() {
        Matrix m = new Matrix(new double[][]{
                {-4, 2, -2, -3},
                {9, 6, 2, 6},
                {0, -5, 1, -5},
                {0, 0, 0, 0}
        });

        double det = m.determinant();
        assertEquals(0, det, 1e-9);
    }

    @Test
    public void testInverseCalculation() {
        Matrix m = new Matrix(new double[][]{
                {-5, 2, 6, -8},
                {1, -5, 1, 8},
                {7, 7, -6, -7},
                {1, -3, 7, 4}
        });

        Matrix expectedInverse = new Matrix(new double[][]{
                {0.21805, 0.45113, 0.24060, -0.04511},
                {-0.80827, -1.45677, -0.44361, 0.52068},
                {-0.07895, -0.22368, -0.05263, 0.19737},
                {-0.52256, -0.81391, -0.30075, 0.30639}
        });

        double det = m.determinant();
        assertEquals(532, det, 1e-9);

        double cofactor23 = m.cofactor(2, 3);
        assertEquals(-160, cofactor23, 1e-9);

        double cofactor32 = m.cofactor(3, 2);
        assertEquals(105, cofactor32, 1e-9);

        Matrix actualInverse = m.inverse();
        System.out.println(actualInverse);
        assertEquals(-160/532.0, actualInverse.get(3, 2), 1e-9);
        assertEquals(105/532.0, actualInverse.get(2, 3), 1e-9);

        assertEquals(actualInverse, expectedInverse);
    }

    @Test
    public void testMultiplyingProductByInverse() {
        Matrix m = new Matrix(new double[][]{
                {3, -9, 7, 3},
                {3, -8, 2, -9},
                {-4, 4, 4, 1},
                {-6, 5, -1, 1}
        });

        Matrix b = new Matrix(new double[][]{
                {8, 2, 2, 2},
                {3, -1, 7, 0},
                {7, 0, 5, 4},
                {6, -2, 0, 5}
        });

        Matrix c = m.multiply(b);
        Matrix inverseB = b.inverse();
        Matrix result = c.multiply(inverseB);

        assertEquals(result, m);
    }

    @Test
    public void testInverseOfAnotherMatrix() {
        Matrix m = new Matrix(new double[][]{
                {8, -5, 9, 2},
                {7, 5, 6, 1},
                {-6, 0, 9, 6},
                {-3, 0, -9, -4}
        });

        Matrix expectedInverse = new Matrix(new double[][]{
                {-0.15385, -0.15385, -0.28205, -0.53846},
                {-0.07692, 0.12308, 0.02564, 0.03077},
                {0.35897, 0.35897, 0.43590, 0.92308},
                {-0.69231, -0.69231, -0.76923, -1.92308}
        });

        Matrix actualInverse = m.inverse();
        assertEquals(actualInverse, expectedInverse);
    }

    @Test
    public void testInverseOfThirdMatrix() {
        Matrix m = new Matrix(new double[][]{
                {9, 3, 0, 9},
                {-5, -2, -6, -3},
                {-4, 9, 6, 4},
                {-7, 6, 6, 2}
        });

        Matrix expectedInverse = new Matrix(new double[][]{
                {-0.04074, -0.07778, 0.14444, -0.22222},
                {-0.07778, 0.03333, 0.36667, -0.33333},
                {-0.02901, -0.14630, -0.10926, 0.12963},
                {0.17778, 0.06667, -0.26667, 0.33333}
        });

        Matrix actualInverse = m.inverse();
        assertTrue(actualInverse.equals(expectedInverse));
    }
}
