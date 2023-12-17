public class Camera {
    private int width;
    private int height;
    private double fov;
    private Matrix transform;
    private double aspectRatio;
    private double halfWidth;
    private double halfHeight;
    public double pixelSize; // Muss public sein für Tests

    // Konstruktor mit Standard-View-Transformation (Einheitsmatrix)
    public Camera(int width, int height, double fov) {
        this.width = width;
        this.height = height;
        this.fov = Math.toRadians(fov); // Umwandlung von Grad in Bogenmaß
        this.transform = Matrix.identity(4);
        setTransform(transform);
    }

    // Konstruktor mit extern berechneter View-Transformation
    public Camera(int width, int height, double fov, Matrix viewTransform) {
        this.width = width;
        this.height = height;
        this.fov = Math.toRadians(fov); // Umwandlung von Grad in Bogenmaß
        this.transform = viewTransform;
        setTransform(transform);
    }

    // Konstruktor, der die View-Transformation berechnet
    public Camera(int width, int height, double fov, Point position, Point lookAt, Vector up) {
        this.width = width;
        this.height = height;
        this.fov = Math.toRadians(fov); // Umwandlung von Grad in Bogenmaß
        this.transform = Matrix.viewTransform(position, lookAt, up);
        setTransform(transform);
    }

    // Getter- und Setter-Methoden
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getFov() {
        return fov;
    }

    public Matrix getTransform() {
        return transform;
    }

    public void setTransform(Matrix transform) {
        this.transform = transform;
    }

    // Methode zur Berechnung der internen Kameraparameter
    public void calculateCameraParameters() {
        aspectRatio = (double) width / height;
        double halfView = Math.tan(fov / 2);

        if (aspectRatio >= 1) {
            halfWidth = halfView;
            halfHeight = halfView / aspectRatio;
        } else {
            halfWidth = halfView * aspectRatio;
            halfHeight = halfView;
        }

        pixelSize = (2 * halfWidth) / width;
    }

    // Methode zur Erzeugung eines Strahls für ein spezifisches Pixel
    public Ray generateRay(int pixelX, int pixelY) {
        // Offset vom Rand des Canvas zum Pixelzentrum
        double xOffset = (pixelX + 0.5) * pixelSize;
        double yOffset = (pixelY + 0.5) * pixelSize;

        // Berechnung der Weltkoordinaten des Pixels
        double worldX = halfWidth - xOffset;
        double worldY = halfHeight - yOffset;

        // Transformation der berechneten Koordinaten und des Kameraursprungs
        Point pixel = transform.inverse().multiply(new Point(worldX, worldY, -1));
        Point origin = transform.inverse().multiply(new Point(0, 0, 0));

        // Berechnung der Richtung des Strahls
        Vector direction = pixel.sub(origin).normalize();

        // Rückgabe des erzeugten Strahls
        return new Ray(origin, direction);
    }

}
