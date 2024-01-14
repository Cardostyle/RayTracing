package MainClasses;

import java.util.ArrayList;
import java.util.List;

public class OffsetSampler implements Sampler {
    @Override
    public List<Point2D> generateSamplePoints(int count) {
        List<Point2D> points = new ArrayList<>();
        int gridSize = (int) Math.sqrt(count);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                double offsetX = (i + 0.5) / gridSize - 0.5;
                double offsetY = (j + 0.5) / gridSize - 0.5;
                points.add(new Point2D(offsetX, offsetY));
            }
        }
        return points;
    }

}