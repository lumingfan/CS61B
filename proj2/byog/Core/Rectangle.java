package byog.Core;

public class Rectangle implements Comparable<Rectangle> {
    private Point leftBottomPoint;
    private Point rightTopPoint;

    private int xLength;
    private int yLength;

    public Rectangle(int x, int y, int xLength, int yLength) {
        this.setLeftBottomPoint(new Point(x, y));
        this.setxLength(xLength);
        this.setyLength(yLength);
        this.setRightTopPoint(new Point(x + xLength - 1, y + yLength - 1));
    }

    private boolean xInRec(int x) {
        return x >= getLeftBottomPoint().getX() && x <= getRightTopPoint().getX();
    }

    private boolean yInRec(int y) {
        return y >= getLeftBottomPoint().getY() && y <= getRightTopPoint().getY();
    }

    public static boolean isOverLapped(Rectangle lhs, Rectangle rhs) {
        if (rhs.getLeftBottomPoint().getX() - 1 > lhs.getRightTopPoint().getX()) {
            return false;
        }
        if (rhs.getRightTopPoint().getX() < lhs.getLeftBottomPoint().getX() - 1) {
            return false;
        }
        if (rhs.getLeftBottomPoint().getY() - 1 > lhs.getRightTopPoint().getY()) {
            return false;
        }
        if (rhs.getRightTopPoint().getY() < lhs.getLeftBottomPoint().getY() - 1) {
            return false;
        }
        return true;
    }

    public static Rectangle checkHall(Rectangle lhs, Rectangle rhs, int direction) {
        Rectangle returnRec = null;
        switch (direction) {
            case 0 : {
                if (lhs.getLeftBottomPoint().getX() > rhs.getRightTopPoint().getX()) {
                    for (int i = lhs.getLeftBottomPoint().getY();
                         i <= lhs.getRightTopPoint().getY(); ++i) {
                        if (rhs.yInRec(i)) {
                            returnRec = new Rectangle(rhs.getRightTopPoint().getX(), i,
                                    lhs.getLeftBottomPoint().getX()
                                            - rhs.getRightTopPoint().getX() + 1, 1);
                            break;
                        }
                    }
                }
                break;
            }
            case 1 : {
                if (lhs.getLeftBottomPoint().getY() > rhs.getRightTopPoint().getY()) {
                    for (int i = lhs.getLeftBottomPoint().getX();
                         i <= lhs.getRightTopPoint().getX(); ++i) {
                        if (rhs.xInRec(i)) {
                            returnRec = new Rectangle(i, rhs.getLeftBottomPoint().getY(),
                                    1, lhs.getLeftBottomPoint().getY()
                                    - rhs.getRightTopPoint().getY() + 1);
                            break;
                        }
                    }
                }
                break;
            }
            case 2 : {
                if (lhs.getRightTopPoint().getX() < rhs.getLeftBottomPoint().getX()) {
                    for (int i = lhs.getLeftBottomPoint().getY();
                         i <= lhs.getRightTopPoint().getY(); ++i) {
                        if (rhs.yInRec(i)) {
                            returnRec = new Rectangle(lhs.getRightTopPoint().getX(), i,
                                    rhs.getLeftBottomPoint().getX()
                                            - lhs.getRightTopPoint().getX() + 1, 1);
                        }
                    }
                }
                break;
            }
            case 3 : {
                if (lhs.getRightTopPoint().getY() < rhs.getLeftBottomPoint().getY()) {
                    for (int i = lhs.getLeftBottomPoint().getX();
                         i <= lhs.getRightTopPoint().getX(); ++i) {
                        if (rhs.xInRec(i)) {
                            returnRec = new Rectangle(i, lhs.getRightTopPoint().getY(),
                                    1, rhs.getLeftBottomPoint().getY()
                                    - lhs.getRightTopPoint().getY() + 1);
                        }
                    }
                }
                break;
            }
            default: break;
        }
        return returnRec;
    }

    @Override
    public int compareTo(Rectangle o) {
        if (getLeftBottomPoint().getX() == o.getLeftBottomPoint().getX()) {
            return getLeftBottomPoint().getY() - o.getLeftBottomPoint().getY();
        }
        return getLeftBottomPoint().getX() - o.getLeftBottomPoint().getX()
                + getLeftBottomPoint().getY() - o.getLeftBottomPoint().getY();
    }

    public Point getLeftBottomPoint() {
        return leftBottomPoint;
    }

    public void setLeftBottomPoint(Point leftBottomPoint) {
        this.leftBottomPoint = leftBottomPoint;
    }

    public Point getRightTopPoint() {
        return rightTopPoint;
    }

    public void setRightTopPoint(Point rightTopPoint) {
        this.rightTopPoint = rightTopPoint;
    }

    public int getxLength() {
        return xLength;
    }

    public void setxLength(int xLength) {
        this.xLength = xLength;
    }

    public int getyLength() {
        return yLength;
    }

    public void setyLength(int yLength) {
        this.yLength = yLength;
    }
}
