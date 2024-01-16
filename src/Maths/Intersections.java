package Maths;

import java.util.*;

public class Intersections {

    private List<Intersection> intersections= new ArrayList<>();

    public Intersections(Intersection[] intersections){
        for (Intersection i : intersections) add(i);
    }

    public Intersections(){
        intersections=new ArrayList<>();
    }

    public void add(Intersection i){
        intersections.add(i);
        intersections.sort(Comparator.comparing(Intersection::t));
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

    // Methode, um den sichtbaren Schnittpunkt zu finden
    public Intersection hit() {
        for (Intersection intersection : intersections) {
            if (intersection.t() > 0) {
                return intersection;
            }
        }
        return null; // Kein sichtbarer Schnittpunkt gefunden
    }
}