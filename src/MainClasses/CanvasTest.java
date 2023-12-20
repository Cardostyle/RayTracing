package MainClasses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class CanvasTest {

    @Test
    public void testCreatingCanvasWithoutName() {
        Canvas canvas = new Canvas(300, 300);
        assertEquals(300, canvas.getWidth());
        assertEquals(300, canvas.getHeight());
        assertEquals("", canvas.getFileName());
    }

    @Test
    public void testCreatingNamedCanvas() {
        Canvas canvas = new Canvas(300, 300, "image");
        assertEquals(300, canvas.getWidth());
        assertEquals(300, canvas.getHeight());
        assertEquals("image", canvas.getFileName());
    }

    @Test
    public void testSettingPixelInCanvas() {
        Canvas canvas = new Canvas(300, 300);
        Color color = new Color(0.5, 0.5, 0.5);
        canvas.setPixel(30, 30, color);
        assertEquals(color, canvas.getPixel(30, 30));
    }

    @Test
    public void testSaveToFile() {
        String filename = "test_image1.png";
        Canvas canvas = new Canvas(300, 300,filename);
        canvas.saveToFile();

        // Überprüfen, ob die Datei existiert
        assertTrue(Files.exists(Paths.get(filename)));
    }

    @Test
    public void testSaveToFileCorrectly() {
        String filename = "test_image2.png";
        Canvas canvas = new Canvas(300, 300,filename);
        Color color = new Color(0.5, 0.5, 0.5);
        canvas.setPixel(30, 30, color);
        canvas.saveToFile();

        // Überprüfen, ob die Datei existiert - vorheriger Test inclusive
        assertTrue(Files.exists(Paths.get(filename)));

        // Erstellen Sie ein neues MainClasses.Canvas und lesen Sie die Datei
        Canvas newCanvas = new Canvas(filename);

        // Überprüfen Sie die Pixelwerte
        Color readColor = newCanvas.getPixel(30, 30);
        assertTrue(color.isEqual(readColor, 0.01));
    }

    @Test
    public void testToInt() {
        Color color = new Color(1, 0.5, 0);
        int expected = 0xFF8000; // 1 * 255 << 16 | 0.5 * 255 << 8 | 0 * 255
        assertEquals(expected, color.toInt());
    }

    @Test
    public void testFromInt() {
        int intRGB = 0xFF8000; // Rot = 1, Grün = 0.5, Blau = 0
        Color color = Color.fromInt(intRGB);
        assertEquals(1, color.getRed(), 0.01);
        assertEquals(0.5, color.getGreen(), 0.01);
        assertEquals(0, color.getBlue(), 0.01);
    }

}
