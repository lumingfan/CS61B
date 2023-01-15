import java.util.HashSet;
import java.util.Set;

public class Node {
    private long id;
    private double lat;
    private double lon;
    private String name;
    private String wayName = null;
    private Set<Edge> edgeSet;

    public Node(long id, double lat, double lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.edgeSet = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(Edge edge) {
        edgeSet.add(edge);
    }

    public boolean isLinked() {
        return !edgeSet.isEmpty();
    }

    public Iterable<Long> adjacent() {
        Set<Long> adjNodeId = new HashSet<>();
        for (Edge edge : edgeSet) {
            Node nodeOne = edge.getNodeOne();
            Node nodeTwo = edge.getNodeTwo();
            if (nodeOne != this) {
                adjNodeId.add(nodeOne.getId());
            } else {
                adjNodeId.add(nodeTwo.getId());
            }
        }
        return adjNodeId;
    }

    public String getWayName() {
        return wayName;
    }

    public void setWayName(String wayName) {
        this.wayName = wayName;
    }
}
