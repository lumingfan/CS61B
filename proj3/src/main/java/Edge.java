public class Edge {
    private Node nodeOne;
    private Node nodeTwo;

    public Edge(Node nodeOne, Node nodeTwo) {
        this.setNodeOne(nodeOne);
        this.setNodeTwo(nodeTwo);
    }

    public Node getNodeOne() {
        return nodeOne;
    }

    public void setNodeOne(Node nodeOne) {
        this.nodeOne = nodeOne;
    }

    public Node getNodeTwo() {
        return nodeTwo;
    }

    public void setNodeTwo(Node nodeTwo) {
        this.nodeTwo = nodeTwo;
    }
}
