import java.util.LinkedList;
import java.util.List;

public class Way {
    private List<Node> way;
    private String name;

    private boolean flag;
    public Way() {
        flag = false;
        this.way = new LinkedList<>();
    }


    public List<Node> getWay() {
        return way;
    }

    public void addNextStop(Node node) {
        way.add(node);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
