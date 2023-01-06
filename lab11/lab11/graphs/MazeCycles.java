package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

import java.nio.file.Paths;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    private Maze graph;
    public MazeCycles(Maze m) {
        super(m);
        graph = m;
    }

    @Override
    public void solve() {
        // todo: Your code here!
        dfs(graph);
    }

    // Helper methods go here
    private void dfs(Maze maze) {
        Stack<Integer> stack = new Stack<>();

        stack.push(0);
        while (!stack.isEmpty()) {
            int now = stack.pop();
            marked[now] = true;
            announce();
            int cnt = 0;
            for (int next : maze.adj(now)) {
                if (!marked[next]) {
                    stack.push(now);
                    stack.push(next);
                    break;
                } else {
                    if (++cnt == 2) {
                        drawCircle(stack, now);
                        return;
                    }
                }
            }
        }
    }

    private void drawCircle(Stack<Integer> stack, int now) {
        int cnt = 2;
        int origin = now;
        while (cnt != 0) {
            int next = stack.pop();
            edgeTo[now] = next;
            for (int neighbor : maze.adj(origin)) {
                if (next == neighbor) {
                    --cnt;
                    break;
                }
            }
            now = next;
        }
        edgeTo[now] = origin;
        announce();
    }
}

