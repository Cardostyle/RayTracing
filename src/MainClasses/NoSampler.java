package MainClasses;

import java.util.Collections;
import java.util.List;

public class NoSampler implements Sampler {
    @Override
    public List<Point> generateSamplePoints(int count) {
        return Collections.singletonList(new Point(0, 0,0));
    }
}
