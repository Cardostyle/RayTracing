package MainClasses;

import java.util.ArrayList;
import java.util.List;

public class RandomSampler implements Sampler {

    @Override
    public List<Point2D> generateSamplePoints(int count) {
        List<Point2D> samplePoints = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double x = Math.random() - 0.5; // Zufälliger Offset zwischen -0.5 und 0.5
            double y = Math.random() - 0.5;
            samplePoints.add(new Point2D(x, y));
        }
        return samplePoints;
    }
}