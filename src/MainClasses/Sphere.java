package MainClasses;

public class Sphere extends Shape {
    private double radius;
    private Point origin;


    public Sphere() {
        this.radius = 1;
        this.origin = new Point(0, 0, 0);
    }

    @Override
    protected Intersections localIntersect(Ray localRay) {
        Vector sphereToRay = localRay.getOrigin().sub(origin); // Ursprung des Strahls - Ursprung der Kugel
        double a = localRay.getVector().dot(localRay.getVector());
        double b = 2 * localRay.getVector().dot(sphereToRay);
        double c = sphereToRay.dot(sphereToRay) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return new Intersections(); // Keine Schnittpunkte
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        Intersection[] result = {new Intersection(t1, this), new Intersection(t2, this)};
        return new Intersections(result);
    }

    @Override
    public Vector normalAt(Point point) {
        return point.sub(origin).normalized(); // Vektor vom Ursprung zum Punkt
    }
}
