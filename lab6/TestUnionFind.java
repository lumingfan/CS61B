import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {
    @Test
    public void testUnionFind() {
        UnionFind uf = new UnionFind(5);

        uf.union(0, 1);
        uf.union(1, 2);
        uf.union(3, 4);

        assertTrue(uf.connected(0, 1));
        assertTrue(uf.connected(3, 4));
        assertEquals(3, uf.sizeOf(0));
        assertEquals(2, uf.sizeOf(3));

        uf.union(1, 3);
        assertTrue(uf.connected(0, 4));

    }
}
