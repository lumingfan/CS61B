package byog.Core;

import java.util.Random;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import static byog.Core.Generate.generateHall;
import static byog.Core.Generate.generateMultiRec;
public class GenerateRandomMap {
    private Random random;
    private int width;
    private int height;

    private TETile[][] tiles;

    public GenerateRandomMap(long seed, int width, int height) {
        this.random = new Random(seed);
        this.width = width;
        this.height = height;
        this.tiles = new TETile[width][height];
    }

    public TETile[][] generate() {
        int rectangleNum = 25 + random.nextInt(20);
        Draw.initTile(tiles);
        Rectangle[] recArr = generateMultiRec(rectangleNum, width, height, random);
        Rectangle[] hallArr = generateHall(recArr);

        for (int i = 0; i < recArr.length; ++i) {
            Draw.drawWall(recArr[i], tiles, Tileset.WALL);
        }
        for (int i = 0; i < hallArr.length; ++i) {
            Draw.drawWall(hallArr[i], tiles, Tileset.WALL);
        }

        for (int i = 0; i < recArr.length; ++i) {
            Draw.drawRec(recArr[i], tiles, Tileset.FLOOR);
        }
        for (int i = 0; i < hallArr.length; ++i) {
            Draw.drawRec(hallArr[i], tiles, Tileset.FLOOR);
        }

        // Draw.drawGoldenDoor(tiles, Tileset.LOCKED_DOOR, random);

        return tiles;
    }



}
