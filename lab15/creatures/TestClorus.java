package creatures;

import huglife.Creature;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class TestClorus {
    @Test
    public void testClorusBasic() {
        Clorus c = new Clorus(2.0);
        assertEquals(2.0, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.stay();
        assertEquals(1.96, c.energy(), 0.01);
        Plip p = new Plip(1.5);
        c.attack(p);
        assertEquals(3.46, c.energy(), 0.01);
        Creature newC = c.replicate();
        assertNotSame(newC, c);
        assertEquals(c.energy(), newC.energy(), 0.01);
        assertEquals(c.color(), newC.color());
        assertEquals(1.73, c.energy(), 0.01);
    }

}
