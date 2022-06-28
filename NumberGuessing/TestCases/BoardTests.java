import com.example.numberguessing.Board;
import com.example.numberguessing.Box;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTests {
    Board testBoard;

    @Before
    public void setup() {
        testBoard = new Board();
    }

    @Test
    public void testBoardSize() {
        //get the 2D boxes on the board
        Box[][] boxes = testBoard.getBoxes();

        //check the number of rows is 10
        assertEquals(boxes.length, 10);

        //check the number of columns is 10
        assertEquals(boxes[0].length, 10);
    }
}
