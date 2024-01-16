package Lights;

import Maths.Point;
import Maths.Vector;

public abstract class LightSource {
    protected Color color;
    protected double intensity;

    public LightSource(Color color, double intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public abstract boolean isDirectional();

    public abstract Vector directionFromPoint(Point p);

    public abstract Vector directionToPoint(Point p);

    public abstract double distanceFromPoint(Point p);

    public Color colorAtPoint(Point p) {
        return color.scale(intensity);
    }

    // Getter und Setter
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        LightSource other = (LightSource) obj;

        return color.equals(other.getColor()) && closeEnough(intensity, other.getIntensity());
    }

    private boolean closeEnough(double a, double b) {
        final double DELTA = 0.0001;
        return Math.abs(a - b) < DELTA;
    }
}
