package MainClasses;

public class DirectionalLightSource extends LightSource {
    private Vector direction;

    public DirectionalLightSource(Vector direction, Color color, double intensity) {
        super(color, intensity);
        this.direction = direction.normalized(); // Stellen Sie sicher, dass die Richtung normalisiert ist
    }

    public DirectionalLightSource(Vector direction) {
        super(new Color(1,1,1),1);
        this.direction = direction;
    }

    public DirectionalLightSource(Vector direction, Color color) {
        super(color,1);
        this.direction = direction;
    }

    public DirectionalLightSource(Vector direction, double intensity) {
        super(new Color(1,1,1),intensity);
        this.direction = direction;
    }

    @Override
    public boolean isDirectional() {
        return true;
    }

    @Override
    public Vector directionToPoint(Point point) {
        return direction.normalized();
    }

    @Override
    public Vector directionFromPoint(Point point) {
        return direction.negate().normalized();
    }

    @Override
    public double distanceFromPoint(Point point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Color colorAtPoint(Point point) {
        return color.scale(intensity);
    }


    public Point getPosition() {
        throw new IllegalCallerException("Only PointLightSources and SpotLightSources can have a position");
    }

    // Getter und Setter
    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction.normalized();
    }



}
