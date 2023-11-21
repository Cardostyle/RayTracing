public class Intersection{
    private double t; //länge Punkt am Ray/Vector
    private Shape shape;

    public Intersection(double t, Sphere sphere){
        this.t=t;
        this.shape =sphere;
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
