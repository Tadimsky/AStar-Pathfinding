import java.util.List;


public class Main {

    public static void main(String[] args) {
        Map m = new Map();
        m.print();
        List<Node> nodes = AI.findpath(m, 0, 6, 7, 5);
        m.print(nodes);
        System.out.println(nodes);
    }

}
