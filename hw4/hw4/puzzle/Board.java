package hw4.puzzle;

import java.util.LinkedList;
import java.util.List;

public class Board implements WorldState {
    private int[][] tiles;
    private int size;

    private Point blank = null;

    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.tiles = copy(tiles);
        this.blank = findBlank();
    }

    private static int[][] copy(int[][] origin) {
        int N = origin.length;
        int[][] returnArr = new int[N][N];
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                returnArr[i][j] = origin[i][j];
            }
        }
        return returnArr;
    }

    private Point findBlank() {
        int N = tiles.length;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (tiles[i][j] == 0) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    public int tileAt(int i, int j) {
        Point p = new Point(i, j);
        return p.getTile();
    }

    public int size() {
        return size;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        List<WorldState> returnIter = new LinkedList<>();
        Iterable<Point> neighborPoints = blank.neighbors();
        for (Point neighbor : neighborPoints) {
            int[][] newTiles = Board.copy(tiles);
            swap(newTiles, blank, neighbor);
            returnIter.add(new Board(newTiles));
        }
        return returnIter;
    }

    private void swap(int[][] newTiles, Point lhs, Point rhs) {
        if (lhs.inBound() && rhs.inBound()) {
            int tile = lhs.getTile();
            newTiles[lhs.getX()][lhs.getY()] = rhs.getTile();
            newTiles[rhs.getX()][rhs.getY()] = tile;
        }
    }

    public int hamming() {
        int returnHamCnt = 0;
        int N = size();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (tileAt(i, j) != 0 && !new Point(i, j).correctPos()) {
                    returnHamCnt++;
                }
            }
        }
        return returnHamCnt;
    }

    public int manhattan() {
        int returnDis = 0;
        int N = size();
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (tileAt(i, j) != 0) {
                    returnDis += new Point(i, j).correctDis();
                }
            }
        }
        return returnDis;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public int hashCode() {
        return tiles.hashCode() * 31 * 31 + size * 31 + blank.hashCode();
    }

    @Override
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }
        if (y.getClass() == Board.class) {
            Board other = (Board) y;
            int N = other.size();
            if (N != size()) {
                return false;
            }
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (tileAt(i, j) != other.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }


    private class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }


        public boolean inBound() {
            return inBound(x) && inBound(y);
        }

        private boolean inBound(int i) {
            return i >= 0 && i < size();
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
        public int getTile() {
            if (!inBound()) {
                throw new IndexOutOfBoundsException();
            }
            return tiles[x][y];
        }

        public Point upper() {
            if (inBound(y + 1)) {
                return new Point(x, y + 1);
            }
            return null;
        }

        public Point down() {
            if (inBound(y - 1)) {
                return new Point(x, y - 1);
            }
            return null;
        }

        public Point left() {
            if (inBound(x - 1)) {
                return new Point(x - 1, y);
            }
            return null;
        }

        public Point right() {
            if (inBound(x + 1)) {
                return new Point(x + 1, y);
            }
            return null;
        }

        public Iterable<Point> neighbors() {
            List<Point> points = new LinkedList<>();
            if (upper() != null) {
                points.add(upper());
            }
            if (down() != null) {
                points.add(down());
            }
            if (left() != null) {
                points.add(left());
            }
            if (right() != null) {
                points.add(right());
            }
            return points;
        }

        public boolean correctPos() {
            return x * size() + y + 1 == getTile();
        }

        public int correctDis() {
            int tile = getTile();
            int corrX = correctX(tile);
            int corrY = correctY(tile);
            return Math.abs(x - corrX) + Math.abs(y - corrY);
        }

        public int correctX(int tile) {
            return (tile - 1) / size();
        }

        public int correctY(int tile) {
            return (tile - 1) % size();
        }
    }

}
