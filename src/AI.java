import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class AI {
    
    List<Node> closedSet;
    List<Node> openSet;
    HashMap<Node, Node> comesFrom;
    HashMap<Node, Double> g_score;
    HashMap<Node, Double> f_score;
    
    private double Fmax;
    private double Gmax;
    
    public AI () {
        
    }
    
    public List<Node> findpath(Map map, int Xs, int Ys, int Xg, int Yg) {
        Node start = map.getNode(Xs, Ys);
        Node goal = map.getNode(Xg, Yg);
        
        // Nodes that have been processed
        closedSet = new ArrayList<Node>();
        
        openSet = new ArrayList<Node>();
        openSet.add(start);
         
        // Map what came before the key node
        comesFrom = new HashMap<Node, Node>(); 
        
        // Distant from start
        g_score = new HashMap<Node, Double>();
        
        // Distant to goal
        f_score = new HashMap<Node, Double>();
        
        // distance from start to start is zero
        g_score.put(start, 0.0);        
        f_score.put(start, heuristic(start, goal));
        
        int countLoop = 0;
        
        while (openSet.size() != 0) {
            // get lowest fscore
            countLoop++;
            Node current = getLowest(f_score, openSet);
            if (current == null) { return null; }
            if (current.getValue() > 1) {
                System.out.println("Why you here?");
                openSet.remove(current);                
                continue;
            }
            if (current.equals(goal)) {
                System.out.println("\t Calculating Max");
                TimeIt time = new TimeIt();
                Fmax = getMin(f_score);
                Gmax = getMin(g_score);
                time.printTime();
                
                System.out.println("\t Generating Path");
                time = new TimeIt();
                List<Node> v = construct_path(comesFrom, goal);
                time.printTime();
                System.out.println(countLoop);
                return v;
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
                        if (!(neighbor.getValue() > 1)) {
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }
        
        return null;        
    }
    
    private double getMin(HashMap<Node, Double> f) {    
        double max = Collections.max(f.values(), new Comparator<Double>(){
            @Override
            public int compare (Double o1, Double o2) {
                if (o1 > 1000000) {
                    return -1;
                }
                if (o2 > 1000000) {
                    return 1;
                }
                return o1.compareTo(o2);
            }            
        });
        //System.out.println(max);
        return max;
    }
    
    
    public void paint(Graphics2D pen, Node n) {
        
        if (n.getValue() > 1) {
            n.paint(pen, new Color(0, 0, 0));
            return;
        }
        
        Double val = g_score.get(n);        
        if (val == null) { val = 0.0; }        
        double green = val / Gmax;        
        green = (green * 255);
        
        val = f_score.get(n);        
        if (val == null) { val = 0.0; }        
        if (val > Fmax) { val = Fmax; }
        double red = val / Fmax;        
        red = (red * 255);
        
        n.paint(pen, new Color((int)red, (int)green, 0));
    }
    
    private double heuristic(Node start, Node goal) {
        if (start == null || goal == null) return 100000;
        if (start.getValue() > 1) { return 1000000; }
        return manhattan(start, goal);
        /return euclidean(start, goal);
    }
    
    private double euclidean(Node start, Node goal) {
        
        // basic guess between current and the goal
        int dx = Node.SIZE * (goal.getX() - start.getX());
        int dy = Node.SIZE * (goal.getY() - start.getY());
        return Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));   
    }
    
    private double manhattan(Node start, Node goal) {
        int dx = Math.abs(goal.getX() - start.getX());
        int dy = Math.abs(goal.getY() - start.getY());
        return Node.SIZE * (dx + dy);
    }
    
    private Node getLowest(HashMap<Node, Double> f_score, List<Node> fromSet) {
        Iterator<Node> i = fromSet.iterator();
        double minValue = Double.MAX_VALUE;
        Node minNode = null;
        
        while (i.hasNext())
        {            
            Node cur = i.next();
            double val = f_score.get(cur);
            
            if (val < minValue) {
                if (cur.getValue() == 1) {
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
