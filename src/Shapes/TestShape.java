package Shapes;

import Maths.Intersections;
import Maths.Point;
import Maths.Ray;
import Maths.Vector;

public class TestShape extends Shape {
    private Ray savedRay;

    public TestShape() {
        super(); // Aufruf des Konstruktors der Oberklasse
    }

    public Ray getSavedRay() {
        return savedRay;
    }

    @Override
    protected Intersections localIntersect(Ray localRay) {
        this.savedRay = localRay; // Speichern des übergebenen Rays
        return new Intersections(); // Leere Liste von Schnittpunkten zurückgeben
    }


    @Override
    public Vector normalAt(Point worldPoint) {
        // Umwandlung des Punktes in das lokale Koordinatensystem des Shapes
        Point localPoint = getTransformation().inverse().multiply(worldPoint);

        // Berechnen der lokalen Normalen
        Vector localNormal = localNormalAt(localPoint);

        // Umwandlung der lokalen Normalen zurück in das Weltkoordinatensystem
        Vector worldNormal = getTransformation().inverse().transpose().multiply(localNormal);

        // Normalisieren der Welt-Normalen
        return worldNormal.normalize();
    }

    @Override
    protected Vector localNormalAt(Point localPoint) {
        // Rückgabe des Ortsvektors des übergebenen Punktes als Normalenvektor
        return new Vector(localPoint.getX(), localPoint.getY(), localPoint.getZ());
    }
}
