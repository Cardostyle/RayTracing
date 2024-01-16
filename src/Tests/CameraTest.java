package Tests;

import MainClasses.Camera;
import Maths.Matrix;
import Maths.Point;
import Maths.Ray;
import Maths.Vector;
import org.junit.Assert;
import org.junit.Test;

public class CameraTest {

    @Test
    public void constructingACamera() {
        int hsize = 160;
        int vsize = 120;
        double fieldOfView = 90;
        Camera camera = new Camera(hsize, vsize, fieldOfView);
        camera.calculateCameraParameters(); // Berechnet interne Parameter

        Assert.assertEquals("MainClasses.Camera width should be 160", hsize, camera.getWidth());
        Assert.assertEquals("MainClasses.Camera height should be 120", vsize, camera.getHeight());
        Assert.assertEquals("MainClasses.Camera field of view should be 90 degrees", Math.toRadians(fieldOfView), camera.getFov(), 0.0);
        Assert.assertEquals("MainClasses.Camera transform should be the identity matrix", Matrix.identity(4), camera.getTransform());
    }

    @Test
    public void pixelSizeForHorizontalCanvas() {
        Camera camera = new Camera(200, 125, 90);
        camera.calculateCameraParameters(); // Berechnet interne Parameter

        Assert.assertEquals("MainClasses.Camera pixel size for horizontal canvas should be 0.01", 0.01, camera.pixelSize, 0.0001);
    }

    @Test
    public void pixelSizeForVerticalCanvas() {
        Camera camera = new Camera(125, 200, 90);
        camera.calculateCameraParameters(); // Berechnet interne Parameter

        Assert.assertEquals("MainClasses.Camera pixel size for vertical canvas should be 0.01", 0.01, camera.pixelSize, 0.0001);
    }

    @Test
    public void constructingRayThroughCenterOfCanvas() {
        Camera camera = new Camera(201, 101, 90);
        Ray ray = camera.generateRay(100, 50);

        Assert.assertEquals("Maths.Ray origin should be at (0, 0, 0)", new Point(0, 0, 0), ray.getOrigin());
        Assert.assertEquals("Maths.Ray direction should be (0, 0, -1)", new Vector(0, 0, -1), ray.getVector());
    }

    @Test
    public void constructingRayThroughCornerOfCanvas() {
        Camera camera = new Camera(201, 101, 90);
        Ray ray = camera.generateRay(0, 0);

        Assert.assertEquals("Maths.Ray origin should be at (0, 0, 0)", new Point(0, 0, 0), ray.getOrigin());
        Assert.assertEquals("Maths.Ray direction should be approximately (0.665186, 0.332593, -0.668512)",
                new Vector(0.665186, 0.332593, -0.668512), ray.getVector());
    }

    @Test
    public void constructingRayWhenCameraIsTransformed() {
        Camera camera = new Camera(201, 101, 90);
        Matrix transform = Matrix.rotateY(0.785398).multiply(Matrix.translate(0, -2, 5));
        camera.setTransform(transform);

        Ray ray = camera.generateRay(100, 50);

        Assert.assertEquals("Maths.Ray origin should be at (0, 2, -5)", new Point(0, 2, -5), ray.getOrigin());
        Assert.assertEquals("Maths.Ray direction should be approximately (0.707106, 0, -0.707106)",
                new Vector(0.707106, 0, -0.707106), ray.getVector());
    }
}
