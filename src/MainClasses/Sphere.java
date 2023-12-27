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
    public Vector normalAt(Point worldPoint) {
        // Transformieren des worldPoint in das lokale Koordinatensystem der Kugel
        Point localPoint = this.getTransformation().inverse().multiply(worldPoint);

        // Berechnen der lokalen Normale
        Vector localNormal = localNormalAt(localPoint);

        // Transformieren der lokalen Normale in das Weltkoordinatensystem
        Vector worldNormal = this.getTransformation().inverse().transpose().multiply(localNormal);
        worldNormal = new Vector(worldNormal.getX(), worldNormal.getY(), worldNormal.getZ()); // Setze w-Komponente auf 0

        // Normalisiere den resultierenden Vektor
        return worldNormal.normalize();
    }

    @Override
    protected Vector localNormalAt(Point localPoint) {
        return localPoint.sub(origin); // Lokale Normale berechnen
    }
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Sphere other = (Sphere) obj;
        return Double.compare(radius, other.radius) == 0 &&
                origin.equals(other.origin);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "radius=" + radius +
                ", origin=" + origin +
                ", transformation=" + getTransformation() +
                ", material=" + getMaterial() +
                '}';
    }

}
