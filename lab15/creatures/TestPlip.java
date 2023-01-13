package creatures;
import huglife.*;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;

/** Tests the plip class   
 *  @authr FIXME
 */

public class TestPlip {

    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        Plip newP = p.replicate();
        assertNotSame(newP, p);
        assertEquals(p.energy(), newP.energy(), 0.01);
        assertEquals(p.color(), newP.color());
    }

    @Test
    public void testChoose() {
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!  

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        HashMap<Direction, Occupant> newSurrounded = new HashMap<>();
        newSurrounded.put(Direction.TOP, new Empty());
        newSurrounded.put(Direction.BOTTOM, new Impassible());
        newSurrounded.put(Direction.LEFT, new Empty());
        newSurrounded.put(Direction.RIGHT, new Impassible());

        int topTimes = 0;
        int bottomTimes = 0;
        int leftTimes = 0;
        int rightTimes = 0;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; ++i) {
            assertEquals(Action.ActionType.REPLICATE, p.chooseAction(newSurrounded).type);
            switch (p.chooseAction(newSurrounded).dir) {
                case TOP:
                    ++topTimes;
                    break;
                case BOTTOM:
                    ++bottomTimes;
                    break;
                case LEFT:
                    ++leftTimes;
                    break;
                case RIGHT:
                    ++rightTimes;
                    break;
                default:
                    break;
            }
        }

        assertEquals(0, rightTimes);
        assertEquals(0, bottomTimes);
        assertEquals(0.5, topTimes * 1.0 / testTimes, 0.01);
        assertEquals(0.5, leftTimes * 1.0 / testTimes, 0.01);

    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
} 
