package lab11.graphs;

import edu.princeton.cs.algs4.IndexMinPQ;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        /* You do not have to use this method. */
        return -1;
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // todo
        IndexMinPQ<Integer> heap = new IndexMinPQ<>(maze.V());
        heap.insert(s, 0);
        while (!heap.isEmpty()) {
            s = heap.delMin();
            marked[s] = true;
            announce();
            if (s == t) {
                break;
            }
            for (int next : maze.adj(s)) {
                if (distTo[next] > distTo[s] + h(next)) {
                    distTo[next] = distTo[s] + 1;
                    edgeTo[next] = s;
                    if (heap.contains(next)) {
                        heap.changeKey(next, distTo[next] + h(next));
                    } else {
                        heap.insert(next, distTo[next] + h(next));
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

