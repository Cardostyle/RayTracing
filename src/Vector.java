public class Vector {

    private double w;
    private double x;
    private double y;
    private double z;

    public Vector(double x,double y,double z){
        this.x=x;
        this.y=y;
        this.z=z;
        this.w=0;
    }

    public Vector(double x,double y,double z, double w){
        this.x=x;
        this.y=y;
        this.z=z;
        this.w=w;
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

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    public Vector negate() {
        return new Vector(-x, -y, -z);
    }

    public Vector mult(double n) {
        return new Vector(x * n, y * n, z * n);
    }

    public Vector div(double n) {
        return new Vector(x / n, y / n, z / n);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector normalized() {
        double mag = magnitude();
        return this.div(mag);
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector cross(Vector v) {
        return new Vector(
                y * v.z - z * v.y,
                z * v.x - x * v.z,
                x * v.y - y * v.x
        );
    }

    @Override
    public String toString(){
        return "x: "+x+ "; y: "+y+"; z: "+z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector other)
        {
            return Math.abs(this.x - other.x) < 0.00001 &&
                    Math.abs(this.y - other.y) < 0.00001 &&
                    Math.abs(this.z - other.z) < 0.00001 &&
                    Math.abs(this.w - other.w) < 0.00001;
        }
        return false;
    }

}
