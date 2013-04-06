import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class AI {  
    
    
    
    public AI () {
        
    }
    
    public static List<Node> findpath(Map map, int Xs, int Ys, int Xg, int Yg) {
        Node start = map.getNode(Xs, Ys);
        Node goal = map.getNode(Xg, Yg);
        
        // Nodes that have been processed
        List<Node> closedSet = new ArrayList<Node>();
        
        List<Node> openSet = new ArrayList<Node>();
        openSet.add(start);
         
        // Map what came before the key node
        HashMap<Node, Node> comesFrom = new HashMap<Node, Node>(); 
        
        // Distant from start
        HashMap<Node, Double> g_score = new HashMap<Node, Double>();
        
        // Distant to goal
        HashMap<Node, Double> f_score = new HashMap<Node, Double>();
        
        // distance from start to start is zero
        g_score.put(start, 0.0);        
        f_score.put(start, heuristic(start, goal));
        
        while (openSet.size() != 0) {
            // get lowest fscore
            Node current = getLowest(f_score, openSet);
            if (current.getValue() > 0) {
                //openSet.remove(current);                
                //continue;
            }
            if (current.equals(goal)) {
                return construct_path(comesFrom, goal);
            }
            openSet.remove(current);
            closedSet.add(current);            
            List<Node> neighbors = map.getNeighbors(current);
            for (Node neighbor : neighbors) {
                if (neighbor == null) { 
                    continue;
                }
                double tentative_g_score = g_score.get(current) + Node.SIZE;
                if (closedSet.contains(neighbor)) {
                    if (tentative_g_score >= g_score.get(neighbor)) {
                        continue;
                    }                        
                }
                if ((!openSet.contains(neighbor)) || tentative_g_score < g_score.get(neighbor)) {
                    comesFrom.put(neighbor, current);
                    g_score.put(neighbor, tentative_g_score);
                    f_score.put(neighbor, g_score.get(neighbor) + heuristic(neighbor, goal));
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    }
                }
            }
        }
        
        return null;        
    }
    
    

    private static double heuristic(Node start, Node goal) {
        if (start.getValue() > 0) { return 1000000; }
        // basic guess between current and the goal
        int dx = goal.getX() - start.getX();
        int dy = goal.getY() - start.getY();
        return Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));        
    }
    
    private static Node getLowest(HashMap<Node, Double> f_score, List<Node> fromSet) {
        Iterator<Node> i = fromSet.iterator();
        double minValue = Double.MAX_VALUE;
        Node minNode = null;
        
        while (i.hasNext())
        {            
            Node cur = i.next();
            double val = f_score.get(cur);
            
            if (val < minValue) {
                if (cur.getValue() == 0) {
                    minNode = cur;
                    minValue = val;
                }
            }
        }
        return minNode;
    }
    
    private static List<Node> construct_path (HashMap<Node, Node> comesFrom, Node goal) {
        List<Node> path = new ArrayList<Node>();
        
        if (comesFrom.containsKey(goal)) {
            path.addAll(construct_path(comesFrom, comesFrom.get(goal)));
            path.add(goal);
        }
        else
        {
            path.add(goal);
        }
        return path;
    }
}
