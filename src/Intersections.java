import java.util.ArrayList;
import java.util.List;

public class Intersections {

    private List<Intersection> intersections= new ArrayList<>();;

    public Intersections(Intersection[] i){
        for (Intersection intersection : i){
            intersections.add(intersection);
        }
    }

    public Intersections(){
        intersections=new ArrayList<>();
    }

    public void add(Intersection i){
        intersections.add(i);
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public int count(){
        return intersections.size();
    }

    public Intersection get(int index) {
        if (index >= 0 && index < intersections.size()) {
            return intersections.get(index);
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds.");
        }
    }
}
