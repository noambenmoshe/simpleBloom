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

    static void SampleRandomizer(Simulation sim, BloomFilter bloomFilter) {
        Random randClass = new Random();
        for (int i = 0; i < sim.getSizeOfSample(); i++) {
            int rand = randClass.nextInt(sim.getN());
            boolean inSet = sim.searchSet(rand);
            boolean bloomAns = bloomFilter.search(rand);
            if (bloomAns && !inSet) {
                sim.falsePositiveCounterIncrement();
                System.out.println("False Positive number: " + rand + "\n");
            }
        }
    }

    public static void main(String[] args) {
        boolean UsingInputArgs = false;
        int uniSize, setSize, sampleSize;
        int numOfSampleRuns, bloomVecLength, numOfHashFuncs;
        if (UsingInputArgs){
            // interpreting input arguments
            uniSize = Integer.parseInt(args[0]);
            setSize = Integer.parseInt(args[1]);
            sampleSize = Integer.parseInt(args[2]);
            numOfSampleRuns = Integer.parseInt(args[3]);
            bloomVecLength = Integer.parseInt(args[4]);
            numOfHashFuncs = Integer.parseInt(args[5]);
        } else {
            uniSize = 121;
            setSize = 2;
            sampleSize = 10;
            numOfSampleRuns = 10;
            bloomVecLength = 5*(3+1);
            numOfHashFuncs = (3+1);
        }
        // Printing Parameters
        System.out.println("Running a test with:\n\tUniverse Size\t\t"+uniSize+"\t\t Set Size\t\t\t"+setSize);
        System.out.println("\tSample Size\t\t\t"+sampleSize+"\t\t Num of Runs\t\t"+numOfSampleRuns);
        System.out.println("\tBloom Vec Length\t"+bloomVecLength+"\t\t Num of Hash funcs\t"+numOfHashFuncs);

/* ***********************************************OLS**************************************************************** */
        //building the OLSs from the first example from the paper
//        List<Integer> numList1 = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
//        Vector<Integer> ols1 = new Vector<>(numList1);
//        List<Integer> numList2 = Arrays.asList(0,1,2,3,4,2,3,4,0,1,4,0,1,2,3,1,2,3,4,0,3,4,0,1,2);
//        Vector<Integer> ols2 = new Vector<>(numList2);

        //building the OLSs from the sets found online
        List<Integer> numList1 = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
        Vector<Integer> ols1 = new Vector<>(numList1);
        List<Integer> numList2 = Arrays.asList(0,1,2,3,4,4,0,1,2,3,3,4,0,1,2,2,3,4,0,1,1,2,3,4,0);
        Vector<Integer> ols2 = new Vector<>(numList2);
        List<Integer> numList3 = Arrays.asList(0,1,2,3,4,3,4,0,1,2,1,2,3,4,0,4,0,1,2,3,2,3,4,0,1);
        Vector<Integer> ols3 = new Vector<>(numList3);

        Vector<Vector<Integer>> ols_vector = new Vector<Vector<Integer>>();
        ols_vector.add(ols1);
        ols_vector.add(ols2);
        ols_vector.add(ols3);

        OLS ols_example = new OLS(3,5,ols_vector);

        OLS_HF ols_hf = new OLS_HF(5);

        ols_hf.set(ols_example);

        BFOLS_vec bf_ols = new BFOLS_vec(5,4,ols_hf);
/* ***********************************************POL**************************************************************** */
        // initializing bloom filter & simulation
        BFPOL bf_pol = new BFPOL(uniSize, 2, 2);
        Simulation simulation= new Simulation(uniSize, setSize, sampleSize);
        boolean randomSet = true;
        if (randomSet){
            // randomly choosing a set, updating simulation and adding to bloom filter
            SetRandomizer(simulation,bf_pol);
        } else {
            bf_pol.insert(168);
            bf_pol.insert(50);
        }

        // running all checks
        for (int i=0; i<numOfSampleRuns; i++){
            SampleRandomizer(simulation,bf_pol);
            simulation.statistics();
            simulation.falsePositiveCounterInitialize();
        }

        System.out.println("\nDone!");
    }
}
