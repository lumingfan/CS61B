package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private MinPQ<Node> heap;
    private Node goalNode;

    public Solver(WorldState initial) {
        heap = new MinPQ<>(new NodeComparator());
        goalNode = null;
        heap.insert(new Node(initial, 0, new ArrayList<>()));
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

    private void solveNowNeighbor(Node now) {
        for (WorldState next : now.getNeighbors()) {
            if (!next.equals(now.getParent())) {
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

        Node(WorldState state, int moves, List<WorldState> path) {
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
            return getEstimatedValue() + getMoves();
        }

        @Override
        public boolean equals(Object o) {
            if (o.getClass() == Node.class) {
                Node other = (Node) o;
                return other.state.equals(this.state);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return path.hashCode() * 31 * 31 * 31 + state.hashCode() * 31 * 31
                    + moves * 31 + estimatedValue;

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
