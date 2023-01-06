package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private Maze graph;
    private int source;
    private int target;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        graph = m;
        source = maze.xyTo1D(sourceX, sourceY);
        target = maze.xyTo1D(targetX, targetY);
        distTo[source] = 0;
        edgeTo[source] = source;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // todo: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new LinkedList<>();
        marked[source] = true;
        queue.add(source);
        while (!queue.isEmpty()) {
            announce();
            int now = queue.poll();
            if (now == target) {
                break;
            }
            for (int next : graph.adj(now)) {
                if (!marked[next]) {
                    marked[next] = true;
                    edgeTo[next] = now;
                    distTo[next] = distTo[now] + 1;
                    queue.add(next);

                    if (next == target) {
                        break;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

