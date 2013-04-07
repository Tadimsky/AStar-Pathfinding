
public class TimeIt {

    private long myTime;
    
    public TimeIt () {
        myTime = System.currentTimeMillis();
    }
    
    public long getTime() {
        return System.currentTimeMillis() - myTime;                
    }
    
    public void printTime() {
        System.out.println("Time to Calculate: " + getTime());
    }

}
