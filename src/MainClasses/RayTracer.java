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
                Intersection hit = intersections.hit();

                if (hit != null) {
                    HitInfo hitInfo = hit.prepareHitInfo(ray);
                    Color color = scene.shadeHit(hitInfo);
                    renderTarget.setPixel(x, y, color);
                } else {
                    renderTarget.setPixel(x, y, Color.BLACK);
                }
            }
        }
    }


    public Canvas getRenderTarget() {
        return renderTarget;
    }
}
