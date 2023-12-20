package MainClasses;

public class Intersection{
    private double t; //länge Punkt am MainClasses.Ray/MainClasses.Vector
    private Shape shape;

    public Intersection(double t, Shape shape){
        this.t=t;
        this.shape =shape;
    }
    @Override
    public String toString(){
        return "t: " +t+"\n shape: " +shape.getClass().toString();
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
        return Double.compare(t, other.t) == 0 && (shape == other.shape || (shape != null && shape.equals(other.shape)));
    }

    public double getT() {
        return t;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Sphere shape) {
        this.shape = shape;
    }

    public void setT(double t) {
        this.t = t;
    }

    public int compareTo(Intersection other) {
        return Double.compare(this.t, other.t);
    }

}
