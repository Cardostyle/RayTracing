package MainClasses;

public class Material {
    private Color color;
    private double ambient;
    private double diffuse;
    private double specular;
    private int shininess;

    // Standardkonstruktor
    public Material() {
        this.color = new Color(1, 1, 1); // Weiß
        this.ambient = 0.1;
        this.diffuse = 0.9;
        this.specular = 0.9;
        this.shininess = 200;
    }

    // Konstruktor mit spezifischen Werten
    public Material(Color color, double ambient, double diffuse, double specular, int shininess) {
        this.color = color;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
    }

    // Getter und Setter
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAmbient() {
        return ambient;
    }

    public void setAmbient(double ambient) {
        this.ambient = ambient;
    }

    public double getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(double diffuse) {
        this.diffuse = diffuse;
    }

    public double getSpecular() {
        return specular;
    }

    public void setSpecular(double specular) {
        this.specular = specular;
    }

    public int getShininess() {
        return shininess;
    }

    public void setShininess(int shininess) {
        this.shininess = shininess;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Material other = (Material) obj;

        return color.equals(other.color) &&
                closeEnough(ambient, other.ambient) &&
                closeEnough(diffuse, other.diffuse) &&
                closeEnough(specular, other.specular) &&
                shininess == other.shininess;
    }

    private boolean closeEnough(double a, double b) {
        final double DELTA = 0.0001;
        return Math.abs(a - b) < DELTA;
    }

    @Override
    public String toString() {
        return "Material{" +
                "color=" + color +
                ", ambient=" + ambient +
                ", diffuse=" + diffuse +
                ", specular=" + specular +
                ", shininess=" + shininess +
                '}';
    }


    public Color phongLighting(PointLightSource light, Point point, Vector eye, Vector normal, boolean inShadow) {
        // Ambiente Beleuchtung
        Color ambientColor = this.color.scale(this.ambient);

        // Wenn der Punkt im Schatten liegt, wird nur der ambiente Anteil zurückgegeben
        if (inShadow) {
            return ambientColor;
        }

        // Lichtvektor (von Punkt zur Lichtquelle)
        Vector lightV = light.getPosition().sub(point).normalize();

        // Diffuse Beleuchtung
        double lightDotNormal = lightV.dot(normal);
        Color diffuseColor = light.getColor().scale(this.diffuse).scale(Math.max(lightDotNormal, 0));

        // Reflektierter Vektor
        Vector reflectV = lightV.negate().reflect(normal);

        // Spekulare Beleuchtung
        double reflectDotEye = reflectV.dot(eye);
        Color specularColor = new Color(0, 0, 0);
        if (reflectDotEye > 0) {
            double factor = Math.pow(reflectDotEye, this.shininess);
            specularColor = light.getColor().scale(this.specular).scale(factor);
        }

        // Gesamte Beleuchtung
        return ambientColor.add(diffuseColor).add(specularColor);
    }
}
