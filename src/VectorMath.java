public class VectorMath {

    //TODO
    public static Vector addition(Vector v1,Vector v2){
        return v1;
    }
    //TODO
    public static Vector subtraction(Vector v1,Vector v2){
        return v1;
    }

    public static Vector negation(Vector v1){
        return new Vector(v1.getX(), v1.getY(), v1.getZ());
    }

    public static Vector multiplication(Vector v1,double n1){
        double x=v1.getX()*n1;
        double y=v1.getY()*n1;
        double z=v1.getZ()*n1;
        return new Vector(x,y,z);
    }

    public static Vector multiplication(double n1,Vector v1){
        double x=v1.getX()*n1;
        double y=v1.getY()*n1;
        double z=v1.getZ()*n1;
        return new Vector(x,y,z);
    }

    public static Vector division(Vector v1,double n1){
        double x=v1.getX()/n1;
        double y=v1.getY()/n1;
        double z=v1.getZ()/n1;
        return new Vector(x,y,z);
    }
    public static boolean isEqual(Vector v1,Vector v2){
        return v1.getX() == v2.getX() && v1.getY() == v2.getY() && v1.getZ() == v2.getZ();
    }

    //TODO
    public static double magnitude(Vector v1){
        return 0;
    }

    //TODO
    public static double sqrMagnitude(Vector v1){
        return 0;
    }

    //TODO
    public static Vector normalized(Vector v1){
        return v1;
    }

    //TODO
    public static double dot(Vector v1,Vector v2){
        return 0;
    }

    //TODO
    public static Vector cross(Vector v1, Vector v2){
        return v1;
    }

    //TODO
    public static Vector subtraction(Point p1, Point p2){
        return new Vector(1,1,1);
    }

    public static Point multiplication(Point p1,double n1){
        double x=p1.getX()*n1;
        double y=p1.getY()*n1;
        double z=p1.getZ()*n1;
        return new Point(x,y,z);
    }

    public static Point multiplication(double n1,Point p1){
        double x=p1.getX()*n1;
        double y=p1.getY()*n1;
        double z=p1.getZ()*n1;
        return new Point(x,y,z);
    }


    public static Point division(Point p1, double n1){
        double x=p1.getX()/n1;
        double y=p1.getY()/n1;
        double z=p1.getZ()/n1;
        return new Point(x,y,z);
    }

    //TODO
    public static Point addition(Point p1, Vector v1){
        return p1;
    }

    //TODO
    public static Point subtraction(Point p1, Vector v1){
        return p1;
    }

    public static boolean isEqual(Point p1,Point p2){
        return p1.getX() == p2.getX() && p1.getY() == p2.getY() && p1.getZ() == p2.getZ();
    }

    //TODO
    public static Point min(Point p1, Point p2){
        return p1;
    }

    //TODO
    public static Point max(Point p1, Point p2){
        return p1;
    }








}
