package MainClasses;

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


}


