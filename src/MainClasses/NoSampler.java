package MainClasses;

import java.util.Collections;
import java.util.List;

public class NoSampler implements Sampler {
    @Override
    public List<Point2D> generateSamplePoints(int count) {
        return Collections.singletonList(new Point2D(0, 0));
    }
}
