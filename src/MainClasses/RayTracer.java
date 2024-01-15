package MainClasses;

public class RayTracer {
    private Scene scene;
    private Camera camera;
    private Canvas renderTarget;
    private Sampler sampler;
    private int sampleCount; // Anzahl der Samples pro Pixel

    public RayTracer(Scene scene, Camera camera) {
        this.scene = scene;
        this.camera = camera;
        this.renderTarget = new Canvas(camera.getWidth(), camera.getHeight());
        this.sampler= new NoSampler();
        this.sampleCount=1;
    }

    public RayTracer(Scene scene, Camera camera, Sampler sampler, int sampleCount) {
        this.scene = scene;
        this.camera = camera;
        this.renderTarget = new Canvas(camera.getWidth(), camera.getHeight());
        this.sampler = sampler;
        this.sampleCount = sampleCount;
    }


    public void render() {
        int maxDepth = 5; // Maximale Rekursionstiefe
        long startTime = System.nanoTime(); // Startzeit in Nanosekunden
        for (int y = 0; y < camera.getHeight(); y++) {
            for (int x = 0; x < camera.getWidth(); x++) {
                Color superSamplingColor = Color.BLACK;
                for (Point offset : sampler.generateSamplePoints(sampleCount)) {
                    double offsetX = offset.getX();
                    double offsetY = offset.getY();
                    Ray ray = camera.generateRay(x + offsetX, y + offsetY);
                    superSamplingColor = superSamplingColor.add(scene.colorAt(ray, maxDepth));
                }
                Color color = superSamplingColor.scale(1.0 / sampleCount);
                renderTarget.setPixel(x, y, new Color(color.clamp(color.getRed()), color.clamp(color.getGreen()),color.clamp(color.getBlue())));
            }
        }
        long endTime = System.nanoTime(); // Endzeit in Nanosekunden
        System.out.println((endTime-startTime)/1e+9 + "Sekunden");
    }




    public Canvas getRenderTarget() {
        return renderTarget;
    }
}
