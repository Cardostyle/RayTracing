public class Strahl {

    private Point origin;
    private Vector vector;

    public Strahl(Point origin, Vector vector){
        this.origin=origin;
        this.vector=vector;
    }

    public Strahl(Point origin, Point destination){
        this.origin=origin;
        this.vector=VectorMath.normalized(VectorMath.subtraction(origin,destination));
    }

    public Point getOrigin() {
        return origin;
    }

    public Vector getVector() {
        return vector;
    }

    public Point pointAt(double t) {
        // P = O + tD
        Vector scaledVector = VectorMath.multiplication(vector, t); // tD
        return VectorMath.addition(origin, scaledVector); // O + tD
    }
}
