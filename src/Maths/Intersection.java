package Maths;

import Lights.HitInfo;
import Shapes.Shape;

import java.util.Objects;


/**
 * @param t länge Punkt am Maths.Ray/Maths.Vector
 */
public record Intersection(double t, Shape shape) {

    @Override
    public String toString() {
        return "t: " + t + "\n shape: " + shape.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Überprüft, ob es sich um dasselbe Objekt handelt
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Überprüft, ob das gegebene Objekt null ist oder zu einer anderen Klasse gehört
        }

        Intersection other = (Intersection) obj;
        return Double.compare(t, other.t) == 0 && (Objects.equals(shape, other.shape));
    }

    public int compareTo(Intersection other) {
        return Double.compare(this.t, other.t);
    }

    public double getT(){
        return t;
    }

    public Shape getShape(){
        return shape;
    }

    public HitInfo prepareHitInfo(Ray ray) {
        Point hitPoint = ray.pointAt(t);
        Vector eyeDirection = ray.getVector().negate();
        Vector normal = shape.normalAt(hitPoint);
        Vector reflectedVector = ray.getVector().reflect(normal);

        return new HitInfo(shape, t, hitPoint, eyeDirection, normal, reflectedVector);
    }

}
