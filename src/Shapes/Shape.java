package Shapes;

import Lights.Material;
import Maths.*;

public abstract class Shape {
    private Matrix transformation;
    private Material material;

    public Shape() {
        this.transformation = Matrix.identity(4); // Initialisiere mit Einheitsmatrix
        this.material = new Material(); // Standardmaterial zuweisen
    }

    public Intersections intersect(Ray worldRay) {
        Ray transformedRay = worldRay.transform(this.transformation.inverse());
        return localIntersect(transformedRay);
    }

    protected abstract Intersections localIntersect(Ray localRay);

    public abstract Vector normalAt(Point point);

    public Matrix getTransformation() {
        return transformation;
    }

    public void setTransformation(Matrix transformation) {
        this.transformation = transformation;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    protected abstract Vector localNormalAt(Point localPoint);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Shape other = (Shape) obj;
        return transformation.equals(other.transformation) &&
                material.equals(other.material);
    }
}



