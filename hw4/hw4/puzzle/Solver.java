package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private MinPQ<Node> heap;
    private Node goalNode;
    // private Set<WorldState> set;
    private int enqueueTimes = 0;
    public Solver(WorldState initial) {
        heap = new MinPQ<>(new NodeComparator());
        // set = new HashSet<>();
        goalNode = null;
        heap.insert(new Node(initial, 0, new ArrayList<>()));

        // test
         increaseEnqueue();


        solve();
    }
    private void solve() {
        while (!heap.isEmpty()) {
            Node nowNode = heap.delMin();
            // set.add(nowNode.getState());
            if (nowNode.isGoal()) {
                goalNode = nowNode;
                return;
            }
            solveNowNeighbor(nowNode);
        }
    }

    private void increaseEnqueue() {
        ++enqueueTimes;
    }

    public int getEnqueueTimes() {
        return enqueueTimes;
    }

    private void solveNowNeighbor(Node now) {
        for (WorldState next : now.getNeighbors()) {
            if (/*!set.contains(next)*/ !next.equals(now.getParent())) {

                // test
                 increaseEnqueue();

                heap.insert(new Node(next, now.getMoves() + 1, now.getPath()));
            }
        }
    }

    public int moves() {
        if (goalNode == null) {
            return -1;
        }
        return goalNode.getMoves();
    }

    public Iterable<WorldState> solution() {
        if (goalNode == null) {
            return null;
        }
        return goalNode.getPath();
    }


    private class Node {
        private WorldState state;
        private List<WorldState> path;
        private int moves;
        private int estimatedValue;

        public Node(WorldState state, int moves, List<WorldState> path) {
            this.state = state;
            this.path = new ArrayList<>(path);
            this.moves = moves;
            this.estimatedValue = state.estimatedDistanceToGoal();
            this.path.add(state);
        }

        public boolean isGoal() {
            return state.isGoal();
        }


        public WorldState getState() {
            return state;
        }

        public int getMoves() {
            return moves;
        }

        public int getEstimatedValue() {
            return estimatedValue;
        }

        public List<WorldState> getPath() {
            return path;
        }
        public Iterable<WorldState> getNeighbors() {
            return state.neighbors();
        }

        public int getPriority() {
            return  getEstimatedValue() + getMoves();
        }


        public void addState(WorldState state) {
            path.add(state);
        }

        public void changeMoves(int movement) {
            moves += movement;
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() == Node.class) {
                Node other = (Node) o;
                return other.state.equals(this.state);
            }
            return false;
        }

        public WorldState getParent() {
            if (path.size() > 1) {
                return path.get(path.size() - 2);
            }
            return null;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        public int compare(Node lhs, Node rhs) {
            return lhs.getPriority() - rhs.getPriority();
        }
    }
}
