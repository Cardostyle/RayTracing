package Lights;

import Maths.Point;
import Maths.Vector;

public class PointLightSource extends LightSource {
    private Point position;

    public PointLightSource(Point position, Color color) {
        super(color, 50); // Standardintensität ist 1
        this.position = position;
    }

    public PointLightSource(Point position, Color color, double intensity) {
        super(color, intensity);
        this.position = position;
    }

    @Override
    public boolean isDirectional() {
        return false;
    }

    @Override
    public Vector directionFromPoint(Point p) {
        return position.sub(p).normalize();
    }

    @Override
    public Vector directionToPoint(Point p) {
        return p.sub(position).normalize();
    }

    @Override
    public double distanceFromPoint(Point p) {
        return position.sub(p).magnitude();
    }


    // Getter und Setter
    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        PointLightSource other = (PointLightSource) obj;

        return super.equals(other) && position.equals(other.getPosition());
    }

    @Override
    public String toString() {
        return "PointLightSource{" +
                "position=" + position +
                ", color=" + getColor() +
                ", intensity=" + getIntensity() +
                '}';
    }

    @Override
    public Color colorAtPoint(Point p) {
//        return this.getColor().scale(intensity);
        double distance = this.distanceFromPoint(p);
        double attenuation = 1.0 / (distance * distance); // Abschwächungsfaktor 1/d^2
        return this.getColor().scale(intensity * attenuation);
    }

}
