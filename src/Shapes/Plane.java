package Shapes;

import Maths.*;

public class Plane extends Shape {
    private static final double EPSILON = 1e-7;
    public Plane() {
        super(); // Aufruf des Konstruktors der Oberklasse
    }

    @Override
    public Intersections localIntersect(Ray localRay) {
        // Fall 1: Strahl verläuft parallel zur Ebene
        if (Math.abs(localRay.getVector().getY()) < EPSILON) {
            return new Intersections(); // Kein Schnitt, leeres Intersections-Objekt zurückgeben
        }

        // Fall 2: Strahl schneidet die Ebene
        double t = -localRay.getOrigin().getY() / localRay.getVector().getY();
        var output = new Intersections();
        output.add(new Intersection(t, this));
        return output;
    }

    @Override
    public Vector normalAt(Point worldPoint) {
        // Transformiere den Punkt in das lokale Koordinatensystem der Ebene
        Point localPoint = getTransformation().inverse().multiply(worldPoint);

        // Berechne die lokale Normale (ist bei einer Ebene immer (0, 1, 0))
        Vector localNormal = localNormalAt(localPoint);

        // Transformiere die lokale Normale zurück in das Weltkoordinatensystem
        Vector worldNormal = getTransformation().inverse().transpose().multiply(localNormal);

        // Normalisiere die Welt-Normale
        return worldNormal.normalize();
    }

    @Override
    public Vector localNormalAt(Point localPoint) {
        return new Vector(0, 1, 0); // Normale der Ebene ist immer (0, 1, 0)
    }
}
