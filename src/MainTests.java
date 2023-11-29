public class MainTests {

    public static void main(String[] args) {

        /**Farben**/
        //applyColors();

        /**Spheres**/
        //applySpheres();

        /**Transformation**/
        applyTransformations();
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
        int width = 1000;
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
            Point eye = new Point(0, 0, -10);  // Ursprung des Strahls
            double wallZ = 10.0;  // z-Position der Wand
            double wallSize = 15.0;  // Größe der Wand
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

}

