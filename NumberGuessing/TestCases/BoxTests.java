import com.example.numberguessing.Box;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoxTests {
    Box testBox;

    @Before
    public void setup() {
        testBox = new Box();
    }

    @Test
    public void testSetStatus() {
        //check initial status is 0
        assertEquals(testBox.getStatus(), 0);

        //change status to 1
        testBox.setStatus(1);

        //check status is 1
        assertEquals(testBox.getStatus(), 1);
    }
}
