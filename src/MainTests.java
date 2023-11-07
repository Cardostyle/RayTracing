public class MainTests {
    public static void main(String[] args) {
        int width = 900;
        int height = 600;
        Canvas canvas1 = new Canvas(width, height, "pic1.png");
        Canvas canvas2 = new Canvas(width, height, "pic2.png");
        Canvas canvas3 = new Canvas(width, height, "pic3.png");

        Point eye = new Point(0, 0, -100);  // Origin des Strahls (Auge)

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Pixelkoordinaten in Farben
                Color color1 = new Color((double) x / width, (double) y / height, 0.0);

                // Richtungsvektor des zum Pixel erzeugten Strahls
                Point pixel = new Point(x - width / 2.0, y - height / 2.0, 0);
                Strahl strahl = new Strahl(eye, pixel);
                Vector dir = strahl.getVector();
                Color color2 = new Color(Math.abs(dir.getX()), Math.abs(dir.getY()), Math.abs(dir.getZ()));

                // Länge des Vektors vom Auge zum Pixel
                double length = VectorMath.magnitude(VectorMath.subtraction(pixel, eye));
                Color color3 = new Color(length / 1000, length / 1000, length / 1000);  // Skalierungsfaktor

                canvas1.setPixel(x, y, color1);
                canvas2.setPixel(x, y, color2);
                canvas3.setPixel(x, y, color3);
            }
        }

        canvas1.saveToFile();
        canvas2.saveToFile();
        canvas3.saveToFile();
    }
}
