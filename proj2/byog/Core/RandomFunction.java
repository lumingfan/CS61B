package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

import static byog.Core.RandomUtils.uniform;

public class RandomFunction {
    public static TETile tileRandom(int i) {
        switch (i) {
            case 0 :
                return Tileset.WATER;
            case 1 :
                return Tileset.SAND;
            case 2 :
                return Tileset.GRASS;
            case 3 :
                return Tileset.FLOWER;
            case 4 :
                return Tileset.LOCKED_DOOR;
            case 5 :
                return Tileset.TREE;
            case 6 :
                return Tileset.MOUNTAIN;
            case 7 :
                return Tileset.PLAYER;
            default:
                break;
        }
        return Tileset.NOTHING;
    }

    public static int generateRandomPosX(int xBegin, int xEnd, Random random) {
        // we need the wall, so must -2; + 1;
        // +1: in case the x is 0, then the wall is out of index -1;
        // -2: in case the uniform(random, width - 1) -> x = width - 2, then the x = width - 1,
        // then the wall is width, out of index;
        return uniform(random, (xEnd - xBegin) - 2) + xBegin + 1;
    }

    public static int generateRandomPosY(int yBegin, int yEnd, Random random) {
        return uniform(random, (yEnd - yBegin) - 2) + yBegin + 1;
    }

    public static int generateRandomWidth(int xLength, Random random) {
        return uniform(random, xLength / 2) + 1;
    }

    public static int generateRandomHeight(int yLength, Random random) {
        return uniform(random, yLength / 2) + 1;
    }

    public static Rectangle generateRandomRec(int xBegin, int xEnd, int yBegin,
                                              int yEnd, Random random) {
        int x = generateRandomPosX(xBegin, xEnd, random);
        int y = generateRandomPosY(yBegin, yEnd, random);
        int xLength = generateRandomWidth(xEnd - xBegin, random);
        int yLength = generateRandomHeight(yEnd - yBegin, random);

        if (x + xLength >= xEnd) {
            // we need the wall, so must -1
            xLength = xEnd - x - 1;
        }
        if (y + yLength >= yEnd) {
            yLength = yEnd - y - 1;
        }
        return new Rectangle(x, y, xLength, yLength);
    }
}
