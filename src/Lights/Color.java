package Lights;

public class Color {
    private double red;
    private double green;
    private double blue;

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(1, 1, 1);
    public static final Color RED = new Color(1, 0, 0);
    public static final Color GREEN = new Color(0, 1, 0);
    public static final Color BLUE = new Color(0, 0, 1);

    public Color(double red, double green, double blue) {
        this.red = (red);
        this.green = (green);
        this.blue = (blue);
    }

    public double clamp(double value) {
        return Math.min(1, Math.max(0, value));
    }

    public Color add(Color other) {
        return new Color(red + other.red, green + other.green, blue + other.blue);
    }

    public Color scale(double factor) {
        return new Color(red * factor, green * factor, blue * factor);
    }

    public Color hadamardProduct(Color other) {
        return new Color(red * other.red, green * other.green, blue * other.blue);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Objekte sind identisch
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Objekt ist null oder von einem anderen Typ
        }

        Color other = (Color) obj;
        final double epsilon = 0.01; // Toleranz für Farbvergleiche

        return Math.abs(red - other.red) < epsilon &&
                Math.abs(green - other.green) < epsilon &&
                Math.abs(blue - other.blue) < epsilon;
    }


    @Override
    public String toString() {
        return "Lights.Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }

    public double getBlue() {
        return blue;
    }

    public double getGreen() {
        return green;
    }

    public double getRed() {
        return red;
    }

    public int toInt() {
        int r = (int) Math.round(red * 255);
        int g = (int) Math.round(green * 255);
        int b = (int) Math.round(blue * 255);
        return (r << 16) | (g << 8) | b;
    }

    public static Color fromInt(int intRGB) {
        int r = (intRGB >> 16) & 0xFF;
        int g = (intRGB >> 8) & 0xFF;
        int b = intRGB & 0xFF;
        return new Color(r / 255.0, g / 255.0, b / 255.0);
    }
}
