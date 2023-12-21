package MainClasses;

import java.util.Objects;


/**
 * @param t länge Punkt am MainClasses.Ray/MainClasses.Vector
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

}
