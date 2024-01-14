package MainClasses;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private List<Shape> objects; // Liste der Objekte in der Szene
    private List<LightSource> lights; // Liste der Lichtquellen in der Szene

    // Konstruktor
    public Scene() {
        this.objects = new ArrayList<>();
        this.lights = new ArrayList<>();
    }

    // Methode zum Hinzufügen eines Objekts zur Szene
    public void addObject(Shape object) {
        objects.add(object);
    }

    // Methode zum Hinzufügen einer Lichtquelle zur Szene
    public void addLight(LightSource light) {
        lights.add(light);
    }

    // Methode, um die Liste der Objekte zu erhalten
    public List<Shape> getObjects() {
        return objects;
    }

    // Methode, um die Liste der Lichtquellen zu erhalten
    public List<LightSource> getLights() {
        return lights;
    }


    // Statische Methode zur Erstellung einer Standard-Testszene
    public static Scene defaultScene() {
        Scene scene = new Scene();

        // Erstellen der ersten Kugel ohne Transformation
        Sphere sphere1 = new Sphere();
        Material material1 = new Material(new Color(0.8,1,0.6),0.1,0.7,0.2,200); // Standardmaterial
        sphere1.setMaterial(material1);
        scene.addObject(sphere1);

        // Erstellen der zweiten Kugel, verkleinert und im Ursprung
        Sphere sphere2 = new Sphere();
        sphere2.setTransformation(Matrix.scale(0.5, 0.5, 0.5));
        scene.addObject(sphere2);

        // Hinzufügen einer Punktlichtquelle
        PointLightSource light = new PointLightSource(new Point(-10, 10, -10), new Color(1, 1, 1));
        scene.addLight(light);

        return scene;
    }

    public Intersections traceRay(Ray ray) {
        Intersections result = new Intersections();
        for (Shape shape : objects) {
            Intersections shapeIntersections = shape.intersect(ray);
            for (Intersection i : shapeIntersections.getIntersections()) {
                result.add(i);
            }
        }
        return result;
    }

    public Color shadeHit(HitInfo info) {
        // Anfangsfarbe ist schwarz (keine Beleuchtung)
        Color finalColor = Color.BLACK;

        // Iterieren über alle Lichtquellen
        for (LightSource light : lights) {
            // Bestimmen, ob der Punkt im Schatten dieser Lichtquelle liegt
            boolean isShadowed = isShadowed(info, light);

            // Beleuchtungsberechnung für diese Lichtquelle
            Color lightContribution = info.getObject().getMaterial().phongLighting(
                    (PointLightSource) light, info.getHitPoint(), info.getEyeDirection(), info.getNormal(), isShadowed);

            // Aufsummieren der Beleuchtungsbeiträge
            finalColor = finalColor.add(lightContribution);
        }

        // Addieren der reflektierten Farbe
        finalColor = finalColor.add(reflectedColor(info));

        return finalColor;
    }

    /**
    public Color shadeHit(HitInfo info) {
        // Anfangsfarbe ist schwarz (keine Beleuchtung)
        Color finalColor = Color.BLACK;

        // Iterieren über alle Lichtquellen
        for (LightSource light : lights) {
            // Bestimmen, ob der Punkt im Schatten dieser Lichtquelle liegt
            boolean isShadowed = isShadowed(info, light);

            // Beleuchtungsberechnung für diese Lichtquelle
            Color lightContribution = info.getObject().getMaterial().phongLighting(
                    light, info.getHitPoint(), info.getEyeDirection(), info.getNormal(), isShadowed);

            // Aufsummieren der Beleuchtungsbeiträge
            finalColor = finalColor.add(lightContribution);
        }

        return finalColor;
    }
    **/

    public boolean isShadowed(HitInfo info, LightSource light) {
        final double EPSILON = 0.0001; // Ein kleiner Wert zur Vermeidung von numerischen Problemen
        if (!(light instanceof PointLightSource)) {
            return false; // Aktuell nur Punktlichtquellen unterstützt
        }
        PointLightSource pointLight = (PointLightSource) light;

        // Verschieben des Startpunkts entlang der Normalen
        Point shadowOrigin = info.getHitPoint().add(info.getNormal().mult(EPSILON));
        Vector toLight = pointLight.getPosition().sub(shadowOrigin);
        double distanceToLight = toLight.magnitude();
        Ray shadowRay = new Ray(shadowOrigin, toLight.normalize());

        Intersections intersections = traceRay(shadowRay);
        Intersection hit = intersections.hit();
        return hit != null && hit.getT() < distanceToLight;
    }


    public boolean isShadowed(Point point) {
        PointLightSource light = (PointLightSource) lights.get(0);
        Vector toLight = light.getPosition().sub(point); // Richtung zur Lichtquelle
        double distanceToLight = toLight.magnitude(); // Entfernung zur Lichtquelle
        Ray shadowRay = new Ray(point, toLight.normalize()); // Schattenfühler

        Intersections intersections = traceRay(shadowRay);

        Intersection hit = intersections.hit();
        return hit != null && hit.getT() < distanceToLight;
    }

    public boolean isShadowed(HitInfo info) {
        final double EPSILON = 0.0001; // Ein kleiner Wert zur Vermeidung von numerischen Problemen
        PointLightSource light = (PointLightSource) lights.get(0);

        // Verschieben des Startpunkts entlang der Normalen
        Point shadowOrigin = info.getHitPoint().add(info.getNormal().mult(EPSILON));
        Vector toLight = light.getPosition().sub(shadowOrigin);
        double distanceToLight = toLight.magnitude();
        Ray shadowRay = new Ray(shadowOrigin, toLight.normalize());

        Intersections intersections = traceRay(shadowRay);
        Intersection hit = intersections.hit();
        return hit != null && hit.getT() < distanceToLight;
    }

    public Color colorAt(Ray ray) {
        var intersections = traceRay(ray);
        var hit = intersections.hit();
        if (hit == null) return Color.BLACK;
        var hitInfo = hit.prepareHitInfo(ray);
        var color = shadeHit(hitInfo);
        return color;
    }

    public Color reflectedColor(HitInfo hitInfo) {
        Material material = hitInfo.getObject().getMaterial();
        if (material.getReflective() == 0) {
            return Color.BLACK;
        }

        // Berechnen des reflektierten Strahls
        Vector reflectDir = hitInfo.getReflectedVector();
        Point reflectOrigin = hitInfo.getHitPoint().add(hitInfo.getNormal().mult(0.0001)); // Kleiner Offset
        Ray reflectRay = new Ray(reflectOrigin, reflectDir);

        Color reflectedColor = colorAt(reflectRay);
        return reflectedColor.scale(material.getReflective());
    }

    public Color colorAt(Ray ray, int depth) {
        if (depth <= 0) {
            return Color.BLACK; // Rekursion begrenzen
        }

        var intersections = traceRay(ray);
        var hit = intersections.hit();
        if (hit == null) return Color.BLACK;

        var hitInfo = hit.prepareHitInfo(ray);
        var color = shadeHit(hitInfo, depth);
        return color;
    }

    public Color shadeHit(HitInfo info, int depth) {
        Color finalColor = Color.BLACK;

        for (LightSource light : lights) {
            boolean isShadowed = isShadowed(info);

            Color lightContribution = info.getObject().getMaterial().phongLighting(
                    (PointLightSource) light, info.getHitPoint(), info.getEyeDirection(), info.getNormal(), isShadowed);

            finalColor = finalColor.add(lightContribution);
        }

        finalColor = finalColor.add(reflectedColor(info, depth));
        return finalColor;


    }

    public Color reflectedColor(HitInfo hitInfo, int depth) {
        Material material = hitInfo.getObject().getMaterial();
        if (material.getReflective() == 0) {
            return Color.BLACK;
        }

        Vector reflectDir = hitInfo.getReflectedVector();
        Point reflectOrigin = hitInfo.getHitPoint().add(hitInfo.getNormal().mult(0.0001));
        Ray reflectRay = new Ray(reflectOrigin, reflectDir);

        Color reflectedColor = colorAt(reflectRay, depth - 1); // Rekursionstiefe verringern
        return reflectedColor.scale(material.getReflective());
    }




}
