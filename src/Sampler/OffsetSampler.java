package Sampler;

import Maths.Point;

import java.util.ArrayList;
import java.util.List;

public class OffsetSampler implements Sampler {
    @Override
    public List<Point> generateSamplePoints(int count) {
        List<Point> points = new ArrayList<>();
        double gridSize =  Math.sqrt(count);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                double offsetX = (i + 0.5) / gridSize - 0.5;
                double offsetY = (j + 0.5) / gridSize - 0.5;
                points.add(new Point(offsetX, offsetY,0));
//                System.out.println(offsetX+" "+offsetY);
            }
        }
        return points;
    }
}