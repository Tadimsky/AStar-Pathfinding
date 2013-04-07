import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Map {
private int[][] mapLayout; 
   /*
   = {
      {0,0,0,0,0,0,0,0},
      {0,1,1,1,1,0,1,1},
      {0,0,1,0,0,0,0,0},
      {0,0,0,1,0,1,1,1},
      {0,0,0,1,0,0,0,0},
      {1,1,0,1,0,0,0,0},
      {0,1,0,0,1,0,0,0},
      {0,0,0,0,1,0,0,0}                                             
      };
      */
    
   private Node[][] map;
   private int width;
   private int height;
   
   
       
   
    public Map (int w, int h, int prob) {
        
        width = w;
        height = h;
        
        ranGen(width, height, prob);
        map = new Node[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                map[row][col] = new Node(col, row, mapLayout[row][col]); 
            }
        }
    }
    
    
    private void ranGen(int width, int height, int prob) {
        Random r = new Random();
        mapLayout = new int[width][height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (r.nextInt(100) < prob) {
                    mapLayout[row][col] = 5;
                }
                else
                { 
                    mapLayout[row][col] = 1; 
                }
            }
        }
    }
    
    public void print() {
        print(new ArrayList<Node>());        
    }
    
    public void print(List<Node> path) {        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (path.contains(map[row][col])) {
                    System.out.print("X ");
                }
                else
                {
                    map[row][col].print();
                }
            }
            System.out.println();
        }
    }
    
    public Node getNode(int x, int y) {
        if (x >= 0 && y >= 0)
        {
            if (x < width && y < height)
            {
                return map[y][x];
            }
        }
        return null;
    }
    
    public List<Node> getNeighbors(Node current) {
        List<Node> lists = new ArrayList<Node>();
        int x = current.getX();
        int y = current.getY();
        lists.add(getNode(x - 1 , y));
        lists.add(getNode(x + 1 , y));
        lists.add(getNode(x , y - 1));
        lists.add(getNode(x , y + 1));
        
        return lists;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    
    public void paint(Graphics2D pen, List<Node> path, AI ai) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Node f = map[row][col];                
                if (path.contains(map[row][col])) {
                    f.paint(pen,Color.blue);                    
                }
                else
                {
                    ai.paint(pen, f);
                }
            }            
        }
        path.get(0).paint(pen, Color.GREEN);
        path.get(path.size() - 1).paint(pen, Color.RED);
    }    

}
