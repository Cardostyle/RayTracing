public class Ray {

    private Point origin;
    private Vector vector;

    public Ray(Point origin, Vector vector){
        this.origin=origin;
        this.vector=vector;
    }

    public Ray(Point origin, Point destination){
        this.origin=origin;
        this.vector=VectorMath.normalized(VectorMath.subtraction(destination,origin));
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

    public Ray transform(Matrix matrix) {
        Point transformedOrigin = matrix.multiply(this.origin);
        Vector transformedVector = matrix.multiply(this.vector);
        return new Ray(transformedOrigin, transformedVector);
    }
}
