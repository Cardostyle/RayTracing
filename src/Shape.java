public abstract class Shape {
    private Matrix transformation;

    public Shape() {
        this.transformation = Matrix.identity(4); // Initialisiere mit Einheitsmatrix
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


}
