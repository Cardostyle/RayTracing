package MainClasses;

public class Cube extends Shape {
    private static final double EPSILON = 1e-7;
    public Cube() {
        super(); // Aufruf des Konstruktors der Oberklasse
    }

    private static class MinMaxT {


        double minT;
        double maxT;

        MinMaxT(double minT, double maxT) {
            this.minT = minT;
            this.maxT = maxT;
        }
    }

    private MinMaxT checkAxis(double origin, double direction) {
        double tminNumerator = -1 - origin;
        double tmaxNumerator = 1 - origin;

        double tmin, tmax;
        if (Math.abs(direction) >= EPSILON) {
            tmin = tminNumerator / direction;
            tmax = tmaxNumerator / direction;
        } else {
            tmin = tminNumerator * Double.POSITIVE_INFINITY;
            tmax = tmaxNumerator * Double.POSITIVE_INFINITY;
        }

        if (tmin > tmax) {
            double temp = tmin;
            tmin = tmax;
            tmax = temp;
        }

        return new MinMaxT(tmin, tmax);
    }

    @Override
    protected Intersections localIntersect(Ray localRay) {
        MinMaxT xMinMax = checkAxis(localRay.getOrigin().getX(), localRay.getVector().getX());
        MinMaxT yMinMax = checkAxis(localRay.getOrigin().getY(), localRay.getVector().getY());
        MinMaxT zMinMax = checkAxis(localRay.getOrigin().getZ(), localRay.getVector().getZ());

        double tmin = Math.max(xMinMax.minT, Math.max(yMinMax.minT, zMinMax.minT));
        double tmax = Math.min(xMinMax.maxT, Math.min(yMinMax.maxT, zMinMax.maxT));

        if (tmin > tmax) {
            return new Intersections(); // Kein Schnittpunkt
        }
        var output= new Intersections();
        output.add(new Intersection(tmin, this));
        output.add(new Intersection(tmax, this));
        return output;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        // Transformiere den Punkt in das lokale Koordinatensystem des Würfels
        Point localPoint = getTransformation().inverse().multiply(worldPoint);

        // Berechne die lokale Normale
        Vector localNormal = localNormalAt(localPoint);

        // Transformiere die lokale Normale zurück in das Weltkoordinatensystem
        Vector worldNormal = getTransformation().inverse().transpose().multiply(localNormal);

        // Normalisiere die Welt-Normale
        return worldNormal.normalize();
    }

    @Override
    protected Vector localNormalAt(Point localPoint) {
        double maxC = Math.max(Math.abs(localPoint.getX()), Math.max(Math.abs(localPoint.getY()), Math.abs(localPoint.getZ())));

        if (maxC == Math.abs(localPoint.getX())) {
            return new Vector(localPoint.getX(), 0, 0);
        } else if (maxC == Math.abs(localPoint.getY())) {
            return new Vector(0, localPoint.getY(), 0);
        } else {
            return new Vector(0, 0, localPoint.getZ());
        }
    }
}
