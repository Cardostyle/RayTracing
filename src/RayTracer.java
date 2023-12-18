public class RayTracer {
    private Scene scene;
    private Camera camera;
    private Canvas renderTarget;

    public RayTracer(Scene scene, Camera camera) {
        this.scene = scene;
        this.camera = camera;
        this.renderTarget = new Canvas(camera.getWidth(), camera.getHeight());
    }

    public void render() {
        for (int y = 0; y < camera.getHeight(); y++) {
            for (int x = 0; x < camera.getWidth(); x++) {
                Ray ray = camera.generateRay(x, y);
                Intersections intersections = scene.traceRay(ray);
                if (intersections.hit() != null) {
                    renderTarget.setPixel(x, y, new Color(1,0.5,0));
                } else {
                    renderTarget.setPixel(x, y, new Color(0,1,1));
                }
            }
        }
    }

    public Canvas getRenderTarget() {
        return renderTarget;
    }
    public static void main(String[] args) {
        // Szene 1: Eine Kugel im Zentrum
        Scene scene1 = createSceneWithCenteredSphere();
        Camera camera1 = new Camera(800, 600, 7.7*2, new Point(0, 0, -10), new Point(0, 0, 0), new Vector(0, 1, 0));
        renderScene(scene1, camera1, "szene1.png");

        // Szene 2: Ein Viertel der Kugel in der linken oberen Ecke
        Scene scene2 = createSceneWithCenteredSphere();
        Camera camera2 = new Camera(600, 600, 11.4*2, new Point(0, 0, -10), new Point(1, 1, 0), new Vector(0, 1, 0));
        renderScene(scene2, camera2, "szene2.png");

        // Szene 3: Kugel von Rand zu Rand
        Scene scene3 = createSceneWithCenteredSphere();
        Camera camera3 = new Camera(600, 600, 2.7*2, new Point(10, 10, -10), new Point(0, 0, 0), new Vector(0, 1, 0));
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


}


