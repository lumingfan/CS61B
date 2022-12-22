package byog.Core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static byog.Core.RandomFunction.generateRandomRec;

public class Generate {
    public static  Rectangle[] generateMultiRec(int randomNum, int width, int height, Random random) {
        Rectangle[] returnRec = new Rectangle[randomNum];

        int index = 0;
        int xBegin = 0, xEnd = width / 2, yBegin = 0, yEnd = height / 2;
        returnRec[index++] = generateRandomRec(xBegin, xEnd, yBegin, yEnd, random);
        while (index != randomNum) {
            if (index <= randomNum / 4) {
                xBegin = 0; xEnd = width / 2; yBegin = 0; yEnd = height / 2;
            } else if (index <= randomNum / 2){
                xBegin = width / 2; xEnd = width;
            } else if (index <= 3 * randomNum / 4) {
                yBegin = height / 2; yEnd = height;
            } else {
                xBegin = 0; xEnd = width / 2;
            }

            Rectangle rec = generateRandomRec(xBegin, xEnd, yBegin, yEnd, random);
            boolean overLapped = false;
            for (int i = 0; i < index; ++i) {
                if (Rectangle.isOverLapped(returnRec[i], rec)) {
                    overLapped = true;
                    break;
                }
            }
            if (!overLapped) {
                returnRec[index++] = rec;
            }
        }

        System.out.println(returnRec.length);

        Arrays.sort(returnRec);
        return returnRec;
    }


    private static int setHall(Rectangle rec, Rectangle[] recArr, Rectangle[] mostHall, int index, Set<Rectangle> set) {
        Set<Integer> directSet = new HashSet<>();
        for (Rectangle resRec : recArr) {
            if (resRec != rec && !set.contains(resRec)) {
                for (int i = 0; i < 4; ++i) {
                    if (directSet.contains(i)) {
                        continue;
                    }
                    Rectangle hall = Rectangle.checkHall(rec, resRec, i);
                    if (hall != null) {
                        mostHall[index++] = hall;
                        directSet.add(i);
                    }
                }
            }
        }
        set.add(rec);
        return index;
    }

    public static Rectangle[] generateHall(Rectangle[] recArr) {
        Rectangle[] mostHall = new Rectangle[recArr.length * 4];
        int index = 0;
        Set<Rectangle> set = new HashSet<>();
        for (Rectangle rec : recArr) {
            index = setHall(rec, recArr, mostHall, index, set);
        }
        Rectangle[] returnRec = new Rectangle[index];
        for (int i = 0; i < index; ++i) {
            returnRec[i] = mostHall[i];
        }
        return returnRec;
    }
}
