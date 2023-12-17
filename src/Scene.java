import java.util.ArrayList;
import java.util.List;

// Klasse Scene
public class Scene {
    private List<Shape> objects; // Liste der Objekte in der Szene

    // Konstruktor
    public Scene() {
        this.objects = new ArrayList<>();
    }

    // Methode zum Hinzufügen eines Objekts zur Szene
    public void addObject(Shape object) {
        objects.add(object);
    }

    // Methode, um die Liste der Objekte zu erhalten
    public List<Shape> getObjects() {
        return objects;
    }

    // Statische Methode zur Erstellung einer Standard-Testszene
    public static Scene defaultScene() {
        Scene scene = new Scene();
        scene.addObject(new Sphere()); // nicht transformierte Kugel
        scene.addObject(new Sphere()); // verkleinerte Kugel im Ursprung
        // Weitere Objekte können hier hinzugefügt werden
        return scene;
    }

    public Intersections traceRay(Ray ray) {
        Intersections result = new Intersections();
        for (Shape shape : objects) {
            Intersections shapeIntersections = shape.localIntersect(ray);
            for (Intersection i : shapeIntersections.getIntersections()) {
                result.add(i);
            }
        }
        result.sortIntersections();
        return result;
    }

}