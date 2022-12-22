package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test; import static org.junit.Assert.*; import byog.TileEngine.TETile; import java.util.Random; /**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    /** draw single hexagonal with the start point position and the sideLength
     * the position (x, y) is the bottom left corner point
     *    xxx
     *   xxxxx
     *  xxxxxxx
     *  xxxxxxx
     *   xxxxx
     *    Xxx
     *    X is the position(x, y)
     */
    public static void addHexagon(TETile[][] tiles, Point startPoint, int sideLength) {
        int tileNum = StdRandom.uniform(8);
        TETile tile;
        switch (tileNum) {
            case 0: tile = Tileset.GRASS; break;
            case 1: tile = Tileset.FLOWER; break;
            case 2: tile = Tileset.MOUNTAIN; break;
            case 3: tile = Tileset.SAND; break;
            case 4: tile = Tileset.TREE; break;
            case 5: tile = Tileset.PLAYER; break;
            case 6: tile = Tileset.WALL; break;
            case 7: tile = Tileset.WATER; break;
            default: tile = Tileset.NOTHING; break;
        }

        fillTiles(tiles, startPoint, sideLength, tile);
    }

    private static class Point {
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private static void fillTiles(TETile[][] tiles, Point startPoint, int sideLength, TETile tile) {
        for (int row = 0; row < 2 * sideLength; ++row) {
            int rowLength = calRowLength(row, sideLength);
            int startX = calStartX(row, startPoint.x, sideLength);
            for (int col = 0; col < rowLength; ++col) {
                tiles[startX + col][row + startPoint.y] = TETile.colorVariant(tile, 32, 32, 32, new Random());
            }
        }
    }


    /** cal the min offset between each row and the (bottom or top)
      */
    private static int calRowOffSet(int row, int sideLength) {
        return Math.min(2 * sideLength - 1 - row, row);
    }

    /** cal the length of each row with given row(range from 0 to 2 * sideLength - 1)
     */
    private static int calRowLength(int row, int sideLength) {
        return 2 * calRowOffSet(row, sideLength) + sideLength;
    }

    /** cal the start x-coordinate of each row
     * @param originX: the given startPoint.x in the addHexagon
     */
    private static int calStartX(int row, int originX, int sideLength) {
        return originX - calRowOffSet(row, sideLength);
    }

    private static void initTiles(TETile[][] tiles) {
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[i].length; ++j) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }

    /** draw multiple hexagon
     *
     * @param minNum: the least hexagons in one col
     * @param maxNum: the most hexagons in one col
     * @param startPoint: the bottom-left point of bottom-left hexagon
     */
    private static void addMuliHexagon(TETile[][] tiles, int minNum, int maxNum, Point startPoint, int sideLength) {
        for (int colNum = 0; colNum <= 2 * (maxNum - minNum); ++colNum) {
            int colCnt = Math.min(colNum, 2 * (maxNum - minNum) - colNum);
            for (int id = 0; id < colCnt + minNum; ++id) {
                int x = startPoint.x + colNum * (sideLength * 2 - 1);
                int y = startPoint.y - colCnt * sideLength + id * sideLength * 2;
                addHexagon(tiles, new Point(x, y), sideLength);
            }
        }
    }
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);

        TETile[][] tiles = new TETile[50][50];
        initTiles(tiles);

        addMuliHexagon(tiles, 3, 5, new Point(15, 15), 3);
        ter.renderFrame(tiles);
    }



    @Test
    public void testRowLength() {
        assertEquals(4, calRowLength(0, 4));
        assertEquals(6, calRowLength(1, 4));
        assertEquals(10, calRowLength(3, 4));
        assertEquals(10, calRowLength(4, 4));
        assertEquals(4, calRowLength(7, 4));
        assertEquals(5, calRowLength(9, 5));
    }

    @Test
    public void testStartX() {
        assertEquals(9, calStartX(1, 10, 5));
        assertEquals(10, calStartX(9, 10, 5));
        assertEquals(9, calStartX(8, 10, 5));
        assertEquals(6, calStartX(4, 10, 5));
        assertEquals(6, calStartX(5, 10, 5));
    }

}
