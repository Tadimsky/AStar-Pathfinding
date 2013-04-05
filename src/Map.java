import java.util.ArrayList;
import java.util.List;


public class Map {
    
   private static final int[][] mapLayout = {
                                             {0,0,0,0,0,0,0,0},
                                             {0,1,1,1,1,0,1,1},
                                             {0,0,1,0,0,0,0,0},
                                             {0,0,0,1,0,1,1,1},
                                             {0,0,0,1,0,0,0,0},
                                             {1,1,0,1,0,0,0,0},
                                             {0,1,0,0,1,0,0,0},
                                             {0,0,0,0,1,0,0,0}                                             
                                             };
    
   private Node[][] map;
   private int width;
   private int height;
   
    public Map () {
        width = mapLayout[0].length;
        height = mapLayout.length;
        map = new Node[width][height];
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                map[row][col] = new Node(col, row, mapLayout[row][col]); 
            }
        }
    }
    
    public void print() {
        print(new ArrayList<Node>());        
    }
    
    public void print(List<Node> path) {        
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
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
    
    

}
