package Lights;

import Lights.Color;
import Lights.LightSource;
import Maths.Point;
import Maths.Vector;

public class SpotLightSource extends LightSource {
    private Point position;
    private Vector direction;
    private double totalAngleDegrees;
    private double fallOffStartDegrees;

    public SpotLightSource(Point position, Vector direction, double totalAngleDegrees, double fallOffStartDegrees, Color color, double intensity) {
        super(color, intensity);
        this.position=position;
        this.direction = direction.normalize();
        this.totalAngleDegrees = totalAngleDegrees;
        this.fallOffStartDegrees = fallOffStartDegrees;
    }

    @Override
    public boolean isDirectional() {
        return false;
    }


    @Override
    public Color colorAtPoint(Point point) {
        // Color of surface point scaled with light intensity
        Color baseColor = color.scale(intensity);

        // direction of light -> normalized
        Vector lightDirection = direction.normalized();
        // direction to surface point -> normalized
        Vector pointDirection = directionToPoint(point);

        // light direction scaled with radians of cone edge -> normalized
        Vector outerEdgeDirection = lightDirection.mult(Math.toRadians(totalAngleDegrees)).normalized();
        // direction of start of fall-off region -> normalized
        Vector fallOffDirection = lightDirection.mult(Math.toRadians(fallOffStartDegrees)).normalized();

        // Point is in the fall-off region, apply attenuation
        double attenuationPortion = getAttenuationPortion(pointDirection, outerEdgeDirection, fallOffDirection);
        return baseColor.scale(Math.pow(attenuationPortion, 2));
    }

    private double getAttenuationPortion(Vector pointDirection, Vector outerEdgeDirection, Vector fallOffDirection) {
        double attenuationPortion;

        double pointToEdge = Math.toDegrees(Math.acos(pointDirection.dot(outerEdgeDirection)));
        double fallOffToEdge = Math.toDegrees(Math.acos(fallOffDirection.dot(outerEdgeDirection)));

        // Adjust the calculation of attenuationPortion
        if (pointToEdge < fallOffStartDegrees / 2) {
            // Inside the illuminated center, no attenuation
            attenuationPortion = 1.0;
        } else if (pointToEdge <= totalAngleDegrees / 2) {
            // Inside the fall-off region
            attenuationPortion = 1.0 - ((pointToEdge - fallOffStartDegrees / 2) / (totalAngleDegrees / 2 - fallOffStartDegrees / 2));
        } else {
            // Outside the total angle, completely attenuated
            attenuationPortion = 0.0;
        }
        return attenuationPortion;
    }


    @Override
    public Vector directionFromPoint(Point point) {
        return position.sub(point).normalized();
    }

    @Override
    public Vector directionToPoint(Point point) {
        return point.sub(position).normalized();
    }

    @Override
    public double distanceFromPoint(Point point) {
        return directionFromPoint(point).magnitude();
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

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
