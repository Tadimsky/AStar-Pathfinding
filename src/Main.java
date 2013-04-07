import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Main extends JPanel {
    
    private static int width = 128;
    private static int height = 128;
    private static int area = 4;
    
    private Map m;
    private List<Node> path;
    private List<Node> currentpath;
    
    Timer loop; 
    
    private AI ai;
    public Main() {
        
        currentpath = new ArrayList<Node>();
        
        m = new Map(width, height, 10);
        ai = new AI();
                
        Random r = new Random();
        do {
            int Xs = r.nextInt(width / area);
            int Ys = r.nextInt(height/ area);
            int Xg = width - r.nextInt(width / area);
            int Yg = height - r.nextInt(height / area);
            System.out.println(String.format("Finding path for (%d,%d), (%d,%d): ", Xs,Ys,Xg,Yg));
            
            long start = System.currentTimeMillis();
            path = ai.findpath(m,Xs, Ys, Xg, Yg);
            start = System.currentTimeMillis() - start;
            if (path == null) {
                System.out.println(String.format("No path exists"));
            }
            else
            {
                System.out.println(String.format("Success!"));
            }
            System.out.println("Time: " + start);
        }
        while (path == null);
        loop = new Timer();
        loop.schedule(new TimerTask() {            
            @Override
            public void run () {
                update();
                repaint();
                
            }
        }, 40, 40);   
        
       }
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        JPanel jp = new Main();
        jp.setPreferredSize(new Dimension(width * Node.SIZE, height * Node.SIZE));// changed it to preferredSize, Thanks!              
        frame.getContentPane().add( jp );// adding to content pane will work here. Please read the comment bellow.       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
    
    public void update() {
        if (path != null) {
            if (path.size() > 0) {
                currentpath.add(path.remove(0));
            }
            else
            {
                //loop.cancel();
            }
        }
    }
    
    public void paint(Graphics g) {        
        if (currentpath != null) {
            m.paint((Graphics2D)g, currentpath, ai);
        }   
    }
}
