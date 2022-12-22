package byog.Core;

import java.util.Random;

import static byog.Core.RandomUtils.uniform;

public class Rectangle implements Comparable<Rectangle>{
    public Point leftBottomPoint;
    public Point rightTopPoint;

    public int xLength;
    public int yLength;

    public Rectangle(int x, int y, int xLength, int yLength) {
        this.leftBottomPoint = new Point(x, y);
        this.xLength = xLength;
        this.yLength = yLength;
        this.rightTopPoint = new Point(x + xLength - 1, y + yLength - 1);
    }

    private boolean xInRec(int x) {
        return x >= leftBottomPoint.x && x <= rightTopPoint.x;
    }

    private boolean yInRec(int y) {
        return y >= leftBottomPoint.y && y <= rightTopPoint.y;
    }

    public static boolean isOverLapped(Rectangle lhs, Rectangle rhs) {
        if (rhs.leftBottomPoint.x - 1> lhs.rightTopPoint.x) {
            return false;
        }
        if (rhs.rightTopPoint.x < lhs.leftBottomPoint.x - 1) {
            return false;
        }
        if (rhs.leftBottomPoint.y - 1> lhs.rightTopPoint.y) {
            return false;
        }
        if (rhs.rightTopPoint.y < lhs.leftBottomPoint.y - 1) {
            return false;
        }
        return true;
    }

    public static Rectangle checkHall(Rectangle lhs, Rectangle rhs, int direction) {
        Rectangle returnRec = null;
        switch (direction) {
            case 0 -> {
                if (lhs.leftBottomPoint.x > rhs.rightTopPoint.x) {
                    for (int i = lhs.leftBottomPoint.y; i <= lhs.rightTopPoint.y; ++i) {
                        if (rhs.yInRec(i)) {
                            returnRec = new Rectangle(rhs.rightTopPoint.x, i, lhs.leftBottomPoint.x - rhs.rightTopPoint.x + 1, 1);
                            break;
                        }
                    }
                }
            }
            case 1 -> {
                if (lhs.leftBottomPoint.y > rhs.rightTopPoint.y) {
                    for (int i = lhs.leftBottomPoint.x; i <= lhs.rightTopPoint.x; ++i) {
                        if (rhs.xInRec(i)) {
                            returnRec = new Rectangle(i, rhs.leftBottomPoint.y, 1, lhs.leftBottomPoint.y - rhs.rightTopPoint.y + 1);
                            break;
                        }
                    }
                }
            }
            case 2 -> {
                if (lhs.rightTopPoint.x < rhs.leftBottomPoint.x) {
                    for (int i = lhs.leftBottomPoint.y; i <= lhs.rightTopPoint.y; ++i) {
                        if (rhs.yInRec(i)) {
                            returnRec = new Rectangle(lhs.rightTopPoint.x, i, rhs.leftBottomPoint.x - lhs.rightTopPoint.x + 1, 1);
                        }
                    }
                }
            }
            case 3 -> {
                if (lhs.rightTopPoint.y < rhs.leftBottomPoint.y) {
                    for (int i = lhs.leftBottomPoint.x; i <= lhs.rightTopPoint.x; ++i) {
                        if (rhs.xInRec(i)) {
                            returnRec = new Rectangle(i, lhs.rightTopPoint.y, 1, rhs.leftBottomPoint.y - lhs.rightTopPoint.y + 1);
                        }
                    }
                }
            }
        }
        return returnRec;
    }

    @Override
    public int compareTo(Rectangle o) {
        if (leftBottomPoint.x == o.leftBottomPoint.x) {
            return leftBottomPoint.y - o.leftBottomPoint.y;
        }
        return leftBottomPoint.x - o.leftBottomPoint.x + leftBottomPoint.y - o.leftBottomPoint.y;
    }
}