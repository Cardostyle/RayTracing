public class Point {

    private final double w=0;
    private double x;
    private double y;
    private double z;

    public Point(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public Point add(Vector v) {
        return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    public Point sub(Vector v) {
        return new Point(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public Vector sub(Point p) {
        return new Vector(x - p.x, y - p.y, z - p.z);
    }

    public Point mult(double n) {
        return new Point(x * n, y * n, z * n);
    }

    public Point div(double n) {
        return new Point(x / n, y / n, z / n);
    }

    @Override
    public String toString(){
        return "x: "+x+ "; y: "+y+"; z: "+z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point other)
        {
            return Math.abs(this.x - other.x) < 0.00001 &&
                    Math.abs(this.y - other.y) < 0.00001 &&
                    Math.abs(this.z - other.z) < 0.00001 &&
                    Math.abs(this.w - other.w) < 0.00001;
        }
        return false;
    }
}


