
public class Node {

    public static int SIZE = 10;
    
    private int myValue;
    private int X;
    private int Y;
    
    public Node (int x, int y, int val) {
        myValue = val;
        X = x;
        Y = y;
    }
    
    public int getValue()
    {
        return myValue;                
    }
    
    public void print() {
        System.out.print(myValue + " ");
    }
    
    public int getX() {
        return X;        
    }
    
    public int getY() {
        return Y;        
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + X;
        result = prime * result + Y;
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (X != other.X)
            return false;
        if (Y != other.Y)
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString () {
        return "Node [X=" + X + ", Y=" + Y + "]";
    }
}
