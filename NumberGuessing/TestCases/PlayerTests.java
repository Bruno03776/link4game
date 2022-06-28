import com.example.numberguessing.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTests {
    Player player;

    @Before
    public void setup() {
        player = new Player("Andy", "A1B2C3", 10);
    }

    @Test
    public void testGetters() {
        //check username is "Andy"
        assertEquals(player.getUsername(), "Andy");

        //check password is "A1B2C3"
        assertEquals(player.getPassword(), "A1B2C3");

        //check score is 10
        assertEquals(player.getScore(), 10);
    }

    @Test
    public void testSetScore() {
        //check initial score is 10
        assertEquals(player.getScore(), 10);

        //change score to 20
        player.setScore(20);

        //check new score is 20
        assertEquals(player.getScore(), 20);
    }
}
