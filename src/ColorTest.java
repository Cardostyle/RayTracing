import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ColorTest {

    @Test
    public void testNewColorHasAllComponentsSetToZero() {
        Color color = new Color(0, 0, 0);
        assertEquals(0.0, color.getRed());
        assertEquals(0.0, color.getGreen());
        assertEquals(0.0, color.getBlue());
    }

    @Test
    public void testStandardConstructor() {
        Color color = new Color(0.3, 0.2, 0.4);
        assertEquals(0.3, color.getRed());
        assertEquals(0.2, color.getGreen());
        assertEquals(0.4, color.getBlue());
    }

    @Test
    public void testAddingColors() {
        Color color1 = new Color(0.9, 0.6, 0.75);
        Color color2 = new Color(0.7, 0.1, 0.25);
        Color result = color1.add(color2);
        assertEquals(1.6, result.getRed());
        assertEquals(0.7, result.getGreen());
        assertEquals(1.0, result.getBlue());
    }

    @ParameterizedTest
    @CsvSource({
            "0.2, 0.3, 0.4, 2",
            "0.8, 1.2, 1.6, 0.5"
    })
    public void testMultiplyingColorByScalar(double red, double green, double blue, double scalar) {
        Color color = new Color(red, green, blue);
        Color result = color.scale(scalar);
        assertEquals(0.4, result.getRed());
        assertEquals(0.6, result.getGreen());
        assertEquals(0.8, result.getBlue());
    }

    @Test
    public void testMultiplyingColorsUsingHadamardProduct() {
        Color color1 = new Color(1, 0.2, 0.4);
        Color color2 = new Color(0.9, 1, 0.1);
        Color result = color1.hadamardProduct(color2);
        assertEquals(0.9, result.getRed());
        assertEquals(0.2, result.getGreen());
        assertEquals(0.04, result.getBlue());
    }

    @ParameterizedTest
    @CsvSource({
            "0.1, 0.2, 0.3, 0.1, 0.2, 0.3",
            "-0.1, -0.2, 0.3, 0.0, 0.0, 0.3",
            "0.1, 1.2, 1.3, 0.1, 1.0, 1.0"
    })
    public void testClampingColors(double red, double green, double blue, double cred, double cgreen, double cblue) {
        Color color = new Color(red, green, blue);
        assertEquals(cred, color.getRed());
        assertEquals(cgreen, color.getGreen());
        assertEquals(cblue, color.getBlue());
    }
}
