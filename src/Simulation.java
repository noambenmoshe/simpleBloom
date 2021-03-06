import java.util.HashMap;
import java.util.Map;

public class Simulation {
    private int n;                                  //universe size
    private int d;                            // d is the set size (how many numbers the bloomfilter should save without false positives)
    private int sizeOfSample;                       //number of numbers to check if exists
    private Map<Integer, Boolean> universeVector;   // will be true if the index is inside our set
    private int falsePositiveCounter;

    public int getFalsePositiveCounter() {
        return falsePositiveCounter;
    }

    public void falsePositiveCounterIncrement() {
        this.falsePositiveCounter++;
    }

    public void falsePositiveCounterInitialize(){
        this.falsePositiveCounter = 0;
    }
    public int getN() {
        return n;
    }

    public int getD() {
        return d;
    }

    public int getSizeOfSample() {
        return sizeOfSample;
    }

    public Simulation(int universeSize, int setSize, int sampleSize){
        n = universeSize;
        d = setSize;
        sizeOfSample = sampleSize;
        universeVector = new HashMap<>();
    }

    void insertToUniverse(int randNum){
        universeVector.put(randNum,true);
    }

    boolean searchSet(int randNum){
        return universeVector.containsKey(randNum);
    }

    double statistics(){
        double percent = 100*falsePositiveCounter/(double)n;
        //System.out.println("\nSimulation parameters: n="+ n +" set size="+sizeOfS+ " sample size="+sizeOfSample);
        System.out.println("\nNumber of false positives: " + falsePositiveCounter);
        System.out.println("Percent of false positives compared to size of sample: " + percent +"%\n");
    return percent;
    }
}
