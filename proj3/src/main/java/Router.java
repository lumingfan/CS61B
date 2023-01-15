import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Router {
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        PriorityQueue<NodeDis> heap = new PriorityQueue<>();
        Set<Long> set = new HashSet<>();
        Map<Long, Double> dist = new HashMap<>();
        Map<Long, Long> path = new HashMap<>();

        long start = g.closest(stlon, stlat);
        long end = g.closest(destlon, destlat);
        dist.put(start, 0.0);

        heap.add(new NodeDis(start, 0.0));

        while (!heap.isEmpty()) {
            NodeDis now = heap.poll();
            set.add(now.id);
            if (now.id == end) {
                break;
            }

            for (Long adj : g.adjacent(now.id)) {
                double distance = dist.get(now.id) + g.distance(now.id, adj);

                if (set.contains(adj)) {
                    continue;
                }

                if (!dist.containsKey(adj) || dist.get(adj) > distance) {
                    path.put(adj, now.id);
                    dist.put(adj, distance);
                    heap.add(new NodeDis(adj, distance + g.distance(adj, end)));
                }
            }
        }

        LinkedList<Long> returnPath = new LinkedList<>();
        while (end != start) {
            returnPath.addFirst(end);
            end = path.get(end);
        }
        returnPath.addFirst(start);
        return returnPath;
    }

    private static class NodeDis implements Comparable<NodeDis> {
        private long id;
        private double priority;
        public NodeDis(long id, double priority) {
            this.id = id;
            this.priority = priority;
        }

        @Override
        public int compareTo(NodeDis o) {
            double diff = priority - o.priority;
            if (diff < 0) {
                return -1;
            } else {
                return 1;
            }
        }
    }

   
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        return null // FIXME
    }

 
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public NavigationDirection(int direction, String way, double dis) {
            this.direction = direction;
            this.way = way;
            this.distance = dis;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
