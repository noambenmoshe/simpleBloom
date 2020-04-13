import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
//import java.Integer.parseInt()

public class Main {

    static void SetRandomizer(Simulation sim, BloomFilter bloomFilter){
        Random randClass = new Random();
        for(int i = 0; i < sim.getSizeOfS(); i++){
            int rand = randClass.nextInt(sim.getN());
            sim.insertToUniverse(rand);
            bloomFilter.insert(rand);
        }
    }

    static void SampleRandomizer(Simulation sim, BloomFilter bloomFilter){
        Random randClass = new Random();
        for(int i = 0; i < sim.getSizeOfSample(); i++){
            int rand = randClass.nextInt(sim.getN());
            boolean inSet = sim.searchSet(rand);
            boolean bloomAns = bloomFilter.search(rand);
            if (bloomAns && !inSet)
                sim.falsePositiveCounterIncrement();
        }
    }

    public static void main(String[] args) {
    /*
        // interpreting input arguments
        int uniSize = Integer.parseInt(args[0]);
        int setSize = Integer.parseInt(args[1]);
        int sampleSize = Integer.parseInt(args[2]);
        int numOfSampleRuns = Integer.parseInt(args[3]);
        int bloomVecLength = Integer.parseInt(args[4]);
        int numOfHashFuncs = Integer.parseInt(args[5]);
    */
        int uniSize = 25;
        int setSize = 3;
        int sampleSize = 1;
        int numOfSampleRuns = 1;
        int bloomVecLength = 5*(3+1);
        int numOfHashFuncs = (3+1);
        /*

        System.out.println("Running a test with:\n\tUniverse Size\t\t"+uniSize+"\t\t Set Size\t\t\t"+setSize);
        System.out.println("\tSample Size\t\t\t"+sampleSize+"\t\t Num of Runs\t\t"+numOfSampleRuns);
        System.out.println("\tBloom Vec Length\t"+bloomVecLength+"\t\t Num of Hash funcs\t"+numOfHashFuncs);

     */
        //building the OLSs form the first example from the paper
        List<Integer> numList1 = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
        Vector<Integer> ols1 = new Vector<>(numList1);

        List<Integer> numList2 = Arrays.asList(0,1,2,3,4,2,3,4,0,1,4,0,1,2,3,1,2,3,4,0,3,4,0,1,2);
        Vector<Integer> ols2 = new Vector<>(numList2);

        Vector<Vector<Integer>> ols_vector = new Vector<Vector<Integer>>();
        ols_vector.add(ols1);
        ols_vector.add(ols2);

        OLS ols_example = new OLS(2,5,ols_vector);

        OLS_HF ols_hf = new OLS_HF(5);

        ols_hf.set(ols_example);

        BFOLS bf_ols = new BFOLS(5,3,ols_hf);
        // initializing bloom filter & simulation
        //BloomFilter b1= new Bloom(bloomVecLength,numOfHashFuncs);
        Simulation simulation= new Simulation(uniSize, setSize, sampleSize);

        // randomly choosing a set, updating simulation and adding to bloom filter
        SetRandomizer(simulation,bf_ols);

        // running all checks
        for (int i=0; i<numOfSampleRuns; i++){
            SampleRandomizer(simulation,bf_ols);
            simulation.statistics();
            simulation.falsePositiveCounterInitialize();
        }

        System.out.println("\nDone!");






    }
}
