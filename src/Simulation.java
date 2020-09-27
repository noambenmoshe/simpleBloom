import java.util.HashMap;
import java.util.Map;

public class Simulation {
    private int n;                                  //universe size
    private int sizeOfS;                            // S is the set size
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

    public int getSizeOfS() {
        return sizeOfS;
    }

    public int getSizeOfSample() {
        return sizeOfSample;
    }

    public Simulation(int universeSize, int setSize, int sampleSize){
        n = universeSize;
        sizeOfS = setSize;
        sizeOfSample = sampleSize;
        universeVector = new HashMap<>();
    }

    void insertToUniverse(int randNum){
        universeVector.put(randNum,true);
    }

    boolean searchSet(int randNum){
        return universeVector.containsKey(randNum);
    }

    void statistics(){
        double percent = 100*falsePositiveCounter/(double)sizeOfSample;
        //System.out.println("\nSimulation parameters: n="+ n +" set size="+sizeOfS+ " sample size="+sizeOfSample);
        System.out.println("\nNumber of false positive:" + falsePositiveCounter);
        System.out.println("Percent of false positives compered to size of sample %" + percent+"\n");
    }
}
