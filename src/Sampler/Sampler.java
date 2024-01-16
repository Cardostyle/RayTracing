package Sampler;

import Maths.Point;

import java.util.List;

public interface Sampler {
    List<Point> generateSamplePoints(int count);
}
