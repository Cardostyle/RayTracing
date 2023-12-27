package MainClasses;

public class PointLightSource extends LightSource {
    private Point position;

    public PointLightSource(Point position, Color color) {
        super(color, 1); // Standardintensität ist 1
        this.position = position;
    }

    public PointLightSource(Point position, Color color, double intensity) {
        super(color, intensity);
        this.position = position;
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
}
