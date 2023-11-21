public class Sphere extends Shape{
    private double radius;
    private Point origin;

    public Sphere(){
        radius=1;
        origin=new Point(0,0,0);
    }

    @Override
    public Intersections intersect(Ray ray) {
        Vector sphereToRay = ray.getOrigin().sub(origin); // Origin Ray - origin Sphere
        double a = ray.getVector().dot(ray.getVector()); // Direction Ray·Direction Ray
        double b = 2 * ray.getVector().dot(sphereToRay); // 2Direction Ray·(Origin Ray - origin Sphere)
        double c = sphereToRay.dot(sphereToRay) - radius*radius; // (Origin Ray - origin Sphere)·(Origin Ray - origin Sphere) - radius²

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            return new Intersections(); // Keine Schnittpunkte
        }

        double t1 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b + Math.sqrt(discriminant)) / (2 * a);
        Intersection[] result={new Intersection(t1, this), new Intersection(t2, this)};
        return new Intersections(result);
    }


    @Override
    public Vector normalAt(Point point) {
        return point.sub(origin).normalized(); //Vector vom Ursprung zum Punkt
    }
}
