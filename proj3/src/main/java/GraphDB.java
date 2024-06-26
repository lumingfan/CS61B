/*import org.junit.Test;
import static org.junit.Assert.*;*/
import org.xml.sax.SAXException;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    Map<Long, Node> nodeMap;
    Map<Long, Node> freeNodeMap;
    Set<Way> waySet;
    private static LocationTrie trie;

    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        try {
            nodeMap = new HashMap<>();
            freeNodeMap = new HashMap<>();
            waySet = new HashSet<>();
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
        trie = generateTrieByAllLocation();
        // test();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    public Node getNode(long v) {
        return nodeMap.get(v);
    }
    public Node getFreeNode(long v) {
        return freeNodeMap.get(v);
    }
    public void addNode(Node node) {
        nodeMap.put(node.getId(), node);
    }

    public void removeNode(long v) {
        nodeMap.remove(v);
    }

    public void addWay(Way way) {
        waySet.add(way);
    }


    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // todo : Your code here.
        Set<Long> needCleaned = new HashSet<>();
        for (Map.Entry<Long, Node> entry : nodeMap.entrySet()) {
            if (!entry.getValue().isLinked()) {
                needCleaned.add(entry.getKey());
            }
        }

        for (Long key : needCleaned) {
            freeNodeMap.put(key, nodeMap.get(key));
            nodeMap.remove(key);
        }
    }

    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return new ArrayList<>(nodeMap.keySet());
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodeMap.get(v).adjacent();
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        long res = 0;
        double closestDis = Double.MAX_VALUE;
        for (long id : nodeMap.keySet()) {
            double nowDis = distance(lon, lat, lon(id), lat(id));
            if (nowDis < closestDis) {
                res = id;
                closestDis = nowDis;
            }
        }
        return res;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodeMap.get(v).getLon();
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodeMap.get(v).getLat();
    }

    // test
    // private Set<String> set = new HashSet<>();
    LocationTrie generateTrieByAllLocation() {
        LocationTrie returnTrie = new LocationTrie();
        addPlotToTrie(returnTrie, nodeMap);
        addPlotToTrie(returnTrie, freeNodeMap);
        return returnTrie;
    }


    public void addPlotToTrie(LocationTrie nowTrie, Map<Long, Node> map) {
        for (Map.Entry<Long, Node> entry : map.entrySet()) {
            Long id = entry.getKey();
            String name = entry.getValue().getName();
            if (name == null) {
                continue;
            }
            // set.add(name);
            nowTrie.add(id, name);
        }
    }

    /**
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return trie.getPrefix(prefix);
    }

    /**
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" : Number, The latitude of the node. <br>
     * "lon" : Number, The longitude of the node. <br>
     * "name" : String, The actual name of the node. <br>
     * "id" : Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        Set<Long> setId = trie.getFull(locationName);
        List<Map<String, Object>> returnMap = new LinkedList<>();
        for (Long id : setId) {
            Map<String, Object> items = new HashMap<>();
            Node node = getNode(id);
            if (node == null) {
                node = getFreeNode(id);
            }
            items.put("lat", node.getLat());
            items.put("lon", node.getLon());
            items.put("name", node.getName());
            items.put("id", node.getId());
            returnMap.add(items);
        }
        return returnMap;
    }

  /*  @Test
    public void test() {
        assertEquals(set.size(), trie.getAllName().size());
        List<Map<String, Object>> test = getLocations("sullivan countertops");
        System.out.println(getLocationsByPrefix("p"));
        System.out.println(getLocations("pav"));
        System.out.println(test.get(0));
    }*/
}
