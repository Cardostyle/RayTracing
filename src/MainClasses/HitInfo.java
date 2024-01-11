package MainClasses;

public class HitInfo {
    private final Shape object;
    private final double t;
    private final Point hitPoint;
    private final Vector eyeDirection;
    private final Vector normal;

    public HitInfo(Shape object, double t, Point hitPoint, Vector eyeDirection, Vector normal) {
        this.object = object;
        this.t = t;
        this.hitPoint = hitPoint;
        this.eyeDirection = eyeDirection;
        this.normal = normal;
    }

    public double getT() {
        return t;
    }

    public Point getHitPoint() {
        return hitPoint;
    }

    public Shape getObject() {
        return object;
    }

    public Vector getEyeDirection() {
        return eyeDirection;
    }

    public Vector getNormal() {
        return normal;
    }
}