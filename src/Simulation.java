public class Simulation {
    private int n; //universe size
    private int sizeOfS;
    private int sizeOfSample; //number of numbers to check if exists
    private boolean[] universeVector; // will be true if the index is inside our set

    public Simulation(int universeSize, int setSize, int sampleSize){
        n = universeSize;
        sizeOfS = setSize;
        sizeOfSample = sampleSize;
        universeVector = new boolean[n];
    }
}
