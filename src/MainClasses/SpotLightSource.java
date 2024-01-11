package MainClasses;

public class SpotLightSource extends PointLightSource {
    private Vector direction;
    private double totalAngleDegrees;
    private double fallOffStartDegrees;

    public SpotLightSource(Point position, Vector direction, double totalAngleDegrees, double fallOffStartDegrees, Color color, double intensity) {
        super(position, color, intensity);
        this.direction = direction.normalize();
        this.totalAngleDegrees = totalAngleDegrees;
        this.fallOffStartDegrees = fallOffStartDegrees;
    }

    @Override
    public boolean isDirectional() {
        return false;
    }

/**
    @Override
    public Color colorAtPoint(Point p) {
        Vector toPoint = p.sub(getPosition()).normalize();
        double angleToDirection = Math.acos(direction.dot(toPoint)) * (180 / Math.PI);

        if (angleToDirection > totalAngleDegrees / 2) {
            return new Color(0, 0, 0); // Außerhalb des Lichtkegels
        } else if (angleToDirection < fallOffStartDegrees / 2) {
            return getColor().scale(getIntensity()); // Innerhalb des voll beleuchteten Bereichs
        } else {
            // Im FallOff-Bereich
            double fallOffRatio = (angleToDirection - fallOffStartDegrees / 2) / (totalAngleDegrees / 2 - fallOffStartDegrees / 2);
            double intensityFactor = Math.pow(1 - fallOffRatio, 2); // Quadratische Abschwächung für realistischeren Effekt
            return getColor().scale(getIntensity() * intensityFactor);
        }
    }
**/
    @Override
    public Color colorAtPoint(Point p) {
        double distance = this.distanceFromPoint(p);
        double attenuation = 1.0 / (distance * distance); // Abschwächungsfaktor 1/d^2
        return this.getColor().scale(this.getIntensity() * attenuation);
    }

    // Getter und Setter für die neuen Felder
    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction.normalize();
    }

    public double getTotalAngleDegrees() {
        return totalAngleDegrees;
    }

    public void setTotalAngleDegrees(double totalAngleDegrees) {
        this.totalAngleDegrees = totalAngleDegrees;
    }

    public double getFallOffStartDegrees() {
        return fallOffStartDegrees;
    }

    public void setFallOffStartDegrees(double fallOffStartDegrees) {
        this.fallOffStartDegrees = fallOffStartDegrees;
    }
}
