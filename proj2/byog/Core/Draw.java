package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Draw {
    public static void initTile(TETile[][] tiles) {
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[i].length; ++j) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
    }
    public static void drawRec(Rectangle rec, TETile[][] tiles, TETile type) {
        for (int x = rec.leftBottomPoint.x; x <= rec.rightTopPoint.x; ++x) {
            drawCol(x, rec.leftBottomPoint.y, rec.rightTopPoint.y, tiles, type);
        }
    }

    private static void drawCol(int col, int rowBegin, int rowEnd, TETile[][] tiles, TETile type) {
        for (int i = rowBegin; i <= rowEnd; ++i) {
            tiles[col][i] = type;
        }
    }

    private static void drawRow(int row, int colBegin, int colEnd, TETile[][] tiles, TETile type) {
        for (int i = colBegin; i <= colEnd; ++i) {
            tiles[i][row] = type;
        }
    }

    public static void drawWall(Rectangle rec, TETile[][] tiles, TETile type) {
        int leftBottomX = rec.leftBottomPoint.x;
        int leftBottomY = rec.leftBottomPoint.y;
        int rightTopX = rec.rightTopPoint.x;
        int rightTopY = rec.rightTopPoint.y;
        drawCol(leftBottomX - 1, leftBottomY, rightTopY, tiles, type);
        drawCol(rightTopX + 1, leftBottomY, rightTopY, tiles, type);

        drawRow(rightTopY + 1, leftBottomX - 1, rightTopX  + 1, tiles, type);
        drawRow(leftBottomY - 1, leftBottomX - 1, rightTopX  + 1, tiles, type);
    }


    public static void drawGoldenDoor(TETile[][]tiles, TETile type, Random random) {

        for (int i = 1; i < tiles.length - 1; ++i) {
            for (int j = 1; j < tiles[i].length - 1; ++j) {
                TETile tileType = tiles[i][j];
                TETile tileTypePre = tiles[i - 1][j];
                TETile tileTypeNext = tiles[i + 1][j];
                boolean condition = false;
                condition = tileType == Tileset.WALL && tileTypePre == Tileset.WALL;
                condition = condition && tileTypeNext == Tileset.WALL;
                if (condition) {
                    if (random.nextDouble() <= 0.5) {
                        tiles[i][j] = Tileset.LOCKED_DOOR;
                        return;
                    }
                }
            }
        }
    }
}
