public class Simulation {
    private int n; //universe size
    private int sizeOfS;
    private int sizeOfSample; //number of numbers to check if exists
    private boolean[] universeVector; // will be true if the index is inside our set
    private int falsePositive;

    public int getFalsePositive() {
        return falsePositive;
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
        universeVector = new boolean[n];
    }

    void AddToUniverse(int randNum){
        universeVector[randNum]=true;
    }


}
