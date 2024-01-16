package MainClasses;

import Lights.*;
import Maths.*;
import Shapes.*;
import Sampler.*;

public class MainTests {

    public static void main(String[] args) {

        long startTime = System.nanoTime(); // Startzeit in Nanosekunden
        /**Farben**/
        //applyColors();

        /**Spheres**/
        //applySpheres();

        /**Transformation**/
        //applyTransformations();

        /**Szene und Kamera***/
        //applySzene();

        /**Raytracer und Camera**/
        //applyRayTracer();

        /**Licht und Material**/
        //applyLights();

        /**Shadows**/
        applySceneWithShadow();

        /**Reflektionen**/
        //applyRefelections();

        /**Verschiedene Formen**/
        //applyShapes();

        long endTime = System.nanoTime(); // Endzeit in Nanosekunden
        System.out.println((endTime-startTime)/6e+10 + "Minuten");
    }

    private static void applyColors(){
        int widthFarben = 900;
        int heightFarben = 600;
        Canvas canvas1 = new Canvas(widthFarben, heightFarben, "Farben1.png");
        Canvas canvas2 = new Canvas(widthFarben, heightFarben, "Farben2.png");
        Canvas canvas3 = new Canvas(widthFarben, heightFarben, "Farben3.png");

        Point eyeFarben = new Point(0, 0, -100);  // Origin des Strahls (Auge)

        for (int x = 0; x < widthFarben; x++) {
            for (int y = 0; y < heightFarben; y++) {
                // Pixelkoordinaten in Farben
                Color color1 = new Color((double) x / widthFarben, (double) y / heightFarben, 0.0);

                // Richtungsvektor des zum Pixel erzeugten Strahls
                Point pixel = new Point(x - widthFarben / 2.0, y - heightFarben / 2.0, 0);
                Ray ray = new Ray(eyeFarben, pixel);
                Vector dir = ray.getVector();
                Color color2 = new Color(Math.abs(dir.getX()), Math.abs(dir.getY()), Math.abs(dir.getZ()));

                // Länge des Vektors vom Auge zum Pixel
                double length = VectorMath.magnitude(VectorMath.subtraction(pixel, eyeFarben));
                Color color3 = new Color(length / 1000, length / 1000, length / 1000);  // Skalierungsfaktor

                canvas1.setPixel(x, y, color1);
                canvas2.setPixel(x, y, color2);
                canvas3.setPixel(x, y, color3);
            }
        }

        canvas1.saveToFile();
        canvas2.saveToFile();
        canvas3.saveToFile();


    }
    private static void applySpheres(){
        int width = 900;
        int height = 600;
        Canvas canvasDirection = new Canvas(width, height, "Kugel1.png");
        Canvas canvasHit = new Canvas(width, height, "Kugel2.png");
        Canvas canvasTValue = new Canvas(width, height, "Kugel3.png");
        Canvas canvasNormal = new Canvas(width, height, "Kugel4.png");
        Sphere sphere = new Sphere();

        Point eye = new Point(0, 0, -10);  // Ursprung des Strahls

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Point pixel = new Point(0.01 * (x - width / 2.0), 0.01 * (y - height / 2.0), 0);
                Ray ray = new Ray(eye, pixel);
                Intersections intersections = sphere.intersect(ray);

                // Richtung des Strahls visualisieren
                Vector dir = ray.getVector();
                Color colorDirection = new Color(Math.abs(dir.getX()), Math.abs(dir.getY()), 0);

                // Treffer oder kein Treffer
                Color colorHit;
                if (intersections.count() == 0) {
                    colorHit =new Color(0, 1, 0);
                }else{
                    colorHit =new Color(1, 0, 0);
                }


                // Wert von t
                Color colorTValue;
                if (intersections.count() > 0) {
                    double t1 = intersections.get(0).getT();
                    double t2 = intersections.get(1).getT();
                    double t = Math.min(t1, t2) - 9;  // Korrekte Berechnung von t
                    colorTValue = new Color(t, t, t);  // Grauwert
                } else {
                    colorTValue = new Color(0, 0, 0);  // Schwarz für keinen Treffer
                }

                // Normalenvektoren an der Kugel
                Color colorNormal;
                if (intersections.count() > 0) {
                    double t = Math.min(intersections.get(0).getT(), intersections.get(1).getT());
                    Point hitPoint = ray.pointAt(t);
                    Vector normal = sphere.normalAt(hitPoint);
                    colorNormal = new Color(Math.abs(normal.getX()), Math.abs(normal.getY()), Math.abs(normal.getZ()));
                } else {
                    colorNormal = new Color(0, 0, 0);
                }

                canvasDirection.setPixel(x, y, colorDirection);
                canvasHit.setPixel(x, y, colorHit);
                canvasTValue.setPixel(x, y, colorTValue);
                canvasNormal.setPixel(x, y, colorNormal);
            }
        }

        canvasDirection.saveToFile();
        canvasHit.saveToFile();
        canvasTValue.saveToFile();
        canvasNormal.saveToFile();

    }
    private static void applyTransformations() {
        int width = 700;
        int height = 700;

        // Transformationen definieren
        Matrix noTransformation = Matrix.identity(4); // Keine Transformation
        Matrix scaleY = Matrix.scale(1, 0.5, 1); // Skalierung um 0.5 in y-Richtung
        Matrix scaleX = Matrix.scale(0.5, 1, 1); // Skalierung um 0.5 in x-Richtung
        Matrix rotationAndScaleX = Matrix.rotateZ(Math.PI / 4).multiply(Matrix.scale(0.5, 1, 1)); // Rotation um die z-Achse um π/4 und Skalierung um 0.5 in x-Richtung

        Matrix[] transformations = {noTransformation, scaleY, scaleX, rotationAndScaleX};

        // Für jede Transformation ein Bild rendern
        for (int i = 0; i < transformations.length; i++) {
            Canvas canvas = new Canvas(width, height, "Kugel_Transformation_" + (i + 1) + ".png");
            Sphere sphere = new Sphere();
            sphere.setTransformation(transformations[i]);
            Point eye = new Point(0, 0, -5);  // Ursprung des Strahls
            double wallZ = 10.0;  // z-Position der Wand
            double wallSize = 7.0;  // Größe der Wand
            double pixelSize = wallSize / canvas.getWidth();  // Größe eines Pixels
            double half = wallSize / 2;

            for (int y = 0; y < canvas.getHeight(); y++) {
                double worldY = half - pixelSize * y;
                for (int x = 0; x < canvas.getWidth(); x++) {
                    double worldX = -half + pixelSize * x;
                    Point position = new Point(worldX, worldY, wallZ);
                    Ray ray = new Ray(eye, position.sub(eye).normalize());
                    Intersections intersections = sphere.intersect(ray);

                    if (intersections.count() > 0) {
                        canvas.setPixel(x, y, new Color(1, 0.5, 0.2));  // Rot für Treffer
                    } else {
                        canvas.setPixel(x, y, new Color(0.2, 0.5, 1));  // Blau für keinen Treffer
                    }
                }
            }
            canvas.saveToFile();
        }
    }

    private static void applySzene(){
        Scene scene = new Scene();

        Camera camera = new Camera(800, 600, 7.7, new Point(0, 0, -10), new Point(0, 0, 0), new Vector(0, 1, 0));
        RayTracer rt = new RayTracer(scene, camera);
        rt.render();
        Canvas canvas = rt.getRenderTarget();
        canvas.setFileName("transformation.png");
        canvas.saveToFile();

    }

    public static void applyRayTracer() {
        // Szene 1: Eine Kugel im Zentrum
        Scene scene1 = createSceneWithCenteredSphere();
        Camera camera1 = new Camera(800, 600, 7.7, new Point(0, 0, -10), new Point(0, 0, 0), new Vector(0, 1, 0));
        renderScene(scene1, camera1, "szene1.png");

        // Szene 2: Ein Viertel der Kugel in der linken oberen Ecke
        Scene scene2 = createSceneWithCenteredSphere();
        Camera camera2 = new Camera(600, 600, 11.4, new Point(0, 0, -10), new Point(1, 1, 0), new Vector(0, 1, 0));
        renderScene(scene2, camera2, "szene2.png");

        // Szene 3: Kugel von Rand zu Rand
        Scene scene3 = createSceneWithCenteredSphere();
        Camera camera3 = new Camera(600, 600, 2.7, new Point(10, 10, -10), new Point(0, 0, 0), new Vector(0, 1, 0));
        renderScene(scene3, camera3, "szene3.png");

        // Weitere Szenen...
    }

    private static Scene createSceneWithCenteredSphere() {
        Scene scene = new Scene();
        Sphere sphere = new Sphere();
        // Transformation der Kugel
        Matrix transformation = Matrix.scale(0.5, 0.5, 0.5); // Skalierung auf Radius 0.5

        //Setzen der Transformation für die Kugel
        sphere.setTransformation(transformation);
        scene.addObject(sphere);
        return scene;
    }

    private static void renderScene(Scene scene, Camera camera, String filename) {
        RayTracer rayTracer = new RayTracer(scene, camera);
        rayTracer.render();
        Canvas canvas = rayTracer.getRenderTarget();
        canvas.setFileName(filename);
        canvas.saveToFile();
    }

    private static void applyLights(){
        // Erstellen der Szene
        Scene scene1 = new Scene();
        Scene scene2 = new Scene();
        Scene scene3 = new Scene();
        Scene scene4 = new Scene();
        Scene sceneComplex = new Scene();


        // Hinzufügen der Punktlichtquelle
        PointLightSource light = new PointLightSource(new Point(-10, 10, -10), new Color(1, 1, 1), 1);
        scene1.addLight(light);
        scene2.addLight(light);
        scene3.addLight(light);
        scene4.addLight(light);
        sceneComplex.addLight(light);

        // Erstellen und Konfigurieren der Kugel4
        Sphere sphere1 = new Sphere();
        Material sphere1Material = new Material(new Color(1, 0.5, 1), 0.1, 0, 0, 200);
        sphere1.setMaterial(sphere1Material);
        scene1.addObject(sphere1);

        // Erstellen und Konfigurieren der Kugel4
        Sphere sphere2 = new Sphere();
        Material sphere2Material = new Material(new Color(1, 0.5, 1), 0, 0.9, 0, 200);
        sphere2.setMaterial(sphere2Material);
        scene2.addObject(sphere2);

        // Erstellen und Konfigurieren der Kugel4
        Sphere sphere3 = new Sphere();
        Material sphere3Material = new Material(new Color(1, 0.5, 1), 0, 0, 0.9, 200);
        sphere3.setMaterial(sphere3Material);
        scene3.addObject(sphere3);

        // Erstellen und Konfigurieren der Kugel4
        Sphere sphere4 = new Sphere();
        Material sphere4Material = new Material(new Color(1, 0.5, 1), 0.1, 0.9, 0.9, 200);
        sphere4.setMaterial(sphere4Material);
        scene4.addObject(sphere4);

        // Erstellen und Konfigurieren der Kamera
        Camera camera = new Camera(800, 800, 90, new Point(0, 0, -2), new Point(0, 0, 0), new Vector(0, 1, 0));

        // Erstellen des RayTracers und Rendern der Szene1
        RayTracer rayTracer = new RayTracer(scene1, camera);
        rayTracer.render();

        // Speichern des gerenderten Bildes
        Canvas canvas = rayTracer.getRenderTarget();
        canvas.setFileName("Lights1.png");
        canvas.saveToFile();

        // Erstellen des RayTracers und Rendern der Szene1
        rayTracer = new RayTracer(scene2, camera);
        rayTracer.render();

        // Speichern des gerenderten Bildes
        canvas = rayTracer.getRenderTarget();
        canvas.setFileName("Lights2.png");
        canvas.saveToFile();

        // Erstellen des RayTracers und Rendern der Szene1
        rayTracer = new RayTracer(scene3, camera);
        rayTracer.render();

        // Speichern des gerenderten Bildes
        canvas = rayTracer.getRenderTarget();
        canvas.setFileName("Lights3.png");
        canvas.saveToFile();

        // Erstellen des RayTracers und Rendern der Szene1
        rayTracer = new RayTracer(scene4, camera);
        rayTracer.render();

        // Speichern des gerenderten Bildes
        canvas = rayTracer.getRenderTarget();
        canvas.setFileName("Lights4.png");
        canvas.saveToFile();

        //Komplexe szene
        sceneComplex.addObject(sphere4);

        Sphere sphere5 = new Sphere();
        Material material5 =  new Material(new Color(1, 0.1, 0.2), 0.2, 0.9, 5, 300);
        sphere5.setTransformation(Matrix.translate(-1.7,-1.2,0).multiply(Matrix.scale(0.4,0.4,0.4)));

        sphere5.setMaterial(material5);
        sceneComplex.addObject(sphere5);

        Sphere sphere6 = new Sphere();
        Material material6 =  new Material(new Color(0.1, 1, 0.2), 0.1, 0.9, 5, 300);
        sphere6.setTransformation(Matrix.translate(1.7,-1.2,0.1).multiply(Matrix.scale(0.4,0.4,0.4)));
        sphere6.setMaterial(material6);
        sceneComplex.addObject(sphere6);


        // Erstellen des RayTracers und Rendern der Szene1

        rayTracer = new RayTracer(sceneComplex, new Camera(1600, 1200, 90, new Point(0, -0.2, -3), new Point(0, 0, 0), new Vector(0, 1, 0)));
        rayTracer.render();

        // Speichern des gerenderten Bildes
        canvas = rayTracer.getRenderTarget();
        canvas.setFileName("LightsComplex.png");
        canvas.saveToFile();
    }

    private static void applySceneWithShadow() {
        Scene scene1 = new Scene();

        // Add a sphere as the floor
        Plane floor = new Plane();
        scene1.addObject(floor);

        Sphere sphere4 = new Sphere();
        Material sphere4Material = new Material(new Color(1, 0.5, 1), 0.1, 0.9, 0.9, 200);
        sphere4.setTransformation(Matrix.translate(0,1.3,0));
        sphere4.setMaterial(sphere4Material);
        scene1.addObject(sphere4);

        Sphere sphere5 = new Sphere();
        Material material5 =  new Material(new Color(1, 0.1, 0.2), 0.2, 0.9, 5, 300);
        sphere5.setTransformation(Matrix.translate(-1.7,0.5,0).multiply(Matrix.scale(0.4,0.4,0.4)));

        sphere5.setMaterial(material5);
        scene1.addObject(sphere5);

        Sphere sphere6 = new Sphere();
        Material material6 =  new Material(new Color(0.1, 1, 0.2), 0.1, 0.9, 5, 300);
        sphere6.setTransformation(Matrix.translate(1.7,0.7,0.1).multiply(Matrix.scale(0.4,0.4,0.4)));
        sphere6.setMaterial(material6);
        scene1.addObject(sphere6);

        // Add light source
        PointLightSource light = new PointLightSource(new Point(-10, 10, -10), new Color(1, 0, 1));
        scene1.addLight(light);

        // Set up the camera
        Camera camera = new Camera(800, 400, 70, new Point(0, 1.5, -5), new Point(0, 1, 0), new Vector(0, 1, 0));

        // Render the scene
        RayTracer rt = new RayTracer(scene1, camera);
        rt.render();
        Canvas canvas = rt.getRenderTarget();
        canvas.setFileName("shadows1.png");
        canvas.saveToFile();


        //gerichtetes Licht
        Scene scene2 = new Scene();
        scene2.addObject(floor);
        scene2.addObject(sphere4);
        scene2.addObject(sphere5);
        scene2.addObject(sphere6);
        DirectionalLightSource directedLight1=new DirectionalLightSource(new Vector(10.0,-10.0,10.0),new Color(1,1,1),0.5);
        DirectionalLightSource directedLight2=new DirectionalLightSource(new Vector(10.0,-10.0,10.0),new Color(1,1,1),1);
        DirectionalLightSource directedLight3=new DirectionalLightSource(new Vector(10.0,-10.0,10.0),new Color(1,1,1),10);
        scene2.addLight(directedLight1);

        rt = new RayTracer(scene2, camera);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("shadows2.png");
        canvas.saveToFile();


        //Spotlights
        Scene scene3 = new Scene();
        scene3.addObject(floor);
        scene3.addObject(sphere4);
        scene3.addObject(sphere5);
        scene3.addObject(sphere6);
        SpotLightSource spotLight1= new SpotLightSource(new Point(0,10,0),new Vector(0,-1,0),30,10,new Color(0.9,0.5,0.9),0.5);
        SpotLightSource spotLight2= new SpotLightSource(new Point(0,10,0),new Vector(0,-1,0),30,10,new Color(1,1,1),1);
        SpotLightSource spotLight3= new SpotLightSource(new Point(0,10,0),new Vector(0,-1,0),30,10,new Color(1,1,1),10);
        scene3.addLight(spotLight1);

        rt = new RayTracer(scene3, camera);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("shadows3.png");
        canvas.saveToFile();
    }

    private static void applyRefelections(){
        Scene scene1 = new Scene();

        // Add a sphere as the floor
        Sphere floor = new Sphere();
        floor.setTransformation(Matrix.scale(10, 0.01, 10));
        floor.setMaterial(new Material(new Color(1,1,1),0.1, 0.9, 0.9, 200,0.5));
        scene1.addObject(floor);

        Sphere sphere4 = new Sphere();
        Material sphere4Material = new Material(new Color(1, 0.5, 1), 0.1, 0.9, 0.9, 200,1);
        sphere4.setTransformation(Matrix.translate(0,1.3,0));
        sphere4.setMaterial(sphere4Material);
        scene1.addObject(sphere4);

        Sphere sphere5 = new Sphere();
        Material material5 =  new Material(new Color(1, 0.1, 0.2), 0.2, 0.9, 5, 300);
        sphere5.setTransformation(Matrix.translate(-1.7,0.5,0).multiply(Matrix.scale(0.4,0.4,0.4)));

        sphere5.setMaterial(material5);
        scene1.addObject(sphere5);

        Sphere sphere6 = new Sphere();
        Material material6 =  new Material(new Color(0.1, 1, 0.2), 0.1, 0.9, 5, 300);
        sphere6.setTransformation(Matrix.translate(1.7,0.7,0.1).multiply(Matrix.scale(0.4,0.4,0.4)));
        sphere6.setMaterial(material6);
        scene1.addObject(sphere6);

        // Add light source
        PointLightSource light = new PointLightSource(new Point(-20, 20, -20), new Color(1, 1, 1));
        scene1.addLight(light);

        // Set up the camera
        Camera camera = new Camera(800, 400, 70, new Point(0, 1.5, -5), new Point(0, 1, 0), new Vector(0, 1, 0));

        // Render the scene
        RayTracer rt = new RayTracer(scene1, camera);
        rt.render();
        Canvas canvas = rt.getRenderTarget();
        canvas.setFileName("reflect.png");
        canvas.saveToFile();

        /*
        rt = new RayTracer(scene1, camera, new OffsetSampler(),2);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("reflect2.png");
        canvas.saveToFile();

        rt = new RayTracer(scene1, camera, new OffsetSampler(),3);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("reflect3.png");
        canvas.saveToFile();

        rt = new RayTracer(scene1, camera, new OffsetSampler(),4);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("reflect4.png");
        canvas.saveToFile();

        rt = new RayTracer(scene1, camera, new OffsetSampler(),9);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("reflect9.png");
        canvas.saveToFile();
*/

        Cube cube= new Cube();
        cube.setTransformation(Matrix.translate(-1.9,0.5,0).multiply(Matrix.scale(0.6,0.6,0.6)));
        Material cubeMaterial =  new Material(new Color(0.1, 1, 0.2), 0.1, 0.9, 5, 300,1);
        cube.setMaterial(cubeMaterial);
        scene1.addObject(cube);

        rt = new RayTracer(scene1, camera, new OffsetSampler(),9);
        rt.render();
        canvas = rt.getRenderTarget();
        canvas.setFileName("reflect9WithCube.png");
        canvas.saveToFile();

    }

    private static void applyShapes(){
        var scene1 = new Scene();

        Cube cube= new Cube();
        cube.setTransformation(Matrix.translate(-1.7,0.5,0).multiply(Matrix.scale(0.4,0.4,0.4)));
        Material cubeMaterial =  new Material(new Color(0.1, 1, 0.2), 0.1, 0.9, 5, 300);
        cube.setMaterial(cubeMaterial);
        scene1.addObject(cube);

        Sphere sphere = new Sphere();
        Material sphereMaterial = new Material(new Color(1, 0.5, 1), 0.1, 0.9, 0.9, 200,1);
        sphere.setTransformation(Matrix.translate(0,1.3,0));
        sphere.setMaterial(sphereMaterial);
        scene1.addObject(sphere);

        Plane plane= new Plane();
        scene1.addObject(plane);

        // Set up the camera
        Camera camera = new Camera(800, 400, 70, new Point(0, 1.5, -5), new Point(0, 1, 0), new Vector(0, 1, 0));

        // Add light source
        PointLightSource light = new PointLightSource(new Point(-10, 10, -10), new Color(1, 1, 1));
        scene1.addLight(light);

        // Render the scene
        RayTracer rt = new RayTracer(scene1, camera);
        rt.render();
        Canvas canvas = rt.getRenderTarget();
        canvas.setFileName("Formen1.png");
        canvas.saveToFile();

    }


}

