import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    void helloWorld(){
        Point test=new Point(1,2,3);
        assertEquals(1,test.getX());
        assertEquals(2,test.getY());
        assertEquals(2,test.getZ());

    }
}
