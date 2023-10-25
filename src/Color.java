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
        this.red = clamp(red);
        this.green = clamp(green);
        this.blue = clamp(blue);
    }

    private double clamp(double value) {
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

    public boolean isEqual(Color other, double epsilon) {
        return Math.abs(red - other.red) < epsilon &&
                Math.abs(green - other.green) < epsilon &&
                Math.abs(blue - other.blue) < epsilon;
    }

    @Override
    public String toString() {
        return "Color{" +
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
}
