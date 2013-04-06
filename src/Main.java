import java.awt.Graphics;
import java.util.List;
import javax.swing.JFrame;


public class Main extends JFrame {
    
    public static void main(String[] args) {        
        Map m = new Map();
        m.print();
        List<Node> nodes = AI.findpath(m, 0, 6, 7, 5);
        m.print(nodes);
        System.out.println(nodes);
    }

}
