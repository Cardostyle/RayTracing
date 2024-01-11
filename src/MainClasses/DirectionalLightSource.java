package MainClasses;

public class DirectionalLightSource extends LightSource {
    private Vector direction;

    public DirectionalLightSource(Vector direction, Color color, double intensity) {
        super(color, intensity);
        this.direction = direction.normalized(); // Stellen Sie sicher, dass die Richtung normalisiert ist
    }

    // Getter und Setter
    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction.normalized();
    }

    @Override
    public boolean isDirectional() {
        return true;
    }

    @Override
    public Vector directionToPoint(Point p) {
        // Die Richtung zur Lichtquelle ist überall gleich
        return direction;
    }

    @Override
    public Vector directionFromPoint(Point p) {
        // Die Richtung von der Lichtquelle ist die negierte Lichtrichtung
        return direction.negate();
    }

    @Override
    public double distanceFromPoint(Point p) {
        // Gerichtete Lichtquellen sind unendlich weit entfernt
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Color colorAtPoint(Point p) {
        // Die Farbe an jedem Punkt ist gleich
        return getColor().scale(getIntensity());
    }
}
