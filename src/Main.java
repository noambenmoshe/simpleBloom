import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
//import java.Integer.parseInt()

public class Main {
    //Randomise numbers that will be inserted to bloomfilter
    //Insert numbers to universe
    static void SetRandomizer(Simulation sim, BloomFilter bloomFilter){
        Random randClass = new Random();

        for(int i = 0; i < sim.getSizeOfS(); i++){
            int rand = randClass.nextInt(sim.getN());
            sim.insertToUniverse(rand);
            bloomFilter.insert(rand);
        }
    }

    //Randomize numbers to check if the bloomfilter returns a correct answer
    //Todo: add or change a function that will check if the bloomfilter returns a correct answer about all the numbers in the universe
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
            uniSize = 24;
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

        Simulation simulation= new Simulation(uniSize, setSize, sampleSize);

        List<Integer> numList1 = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
        List<Integer> numList2 = Arrays.asList(0,1,2,3,4,4,0,1,2,3,3,4,0,1,2,2,3,4,0,1,1,2,3,4,0);
        List<Integer> numList3 = Arrays.asList(0,1,2,3,4,3,4,0,1,2,1,2,3,4,0,4,0,1,2,3,2,3,4,0,1);
/* ***********************************************OLS**************************************************************** */
        //building the OLSs from the first example from the paper
//        List<Integer> numList1 = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
//        Vector<Integer> ols1 = new Vector<>(numList1);
//        List<Integer> numList2 = Arrays.asList(0,1,2,3,4,2,3,4,0,1,4,0,1,2,3,1,2,3,4,0,3,4,0,1,2);
//        Vector<Integer> ols2 = new Vector<>(numList2);

        //building the OLSs from the sets found online

//        Vector<Integer> ols1 = new Vector<>(numList1);
//        Vector<Integer> ols2 = new Vector<>(numList2);
//        Vector<Integer> ols3 = new Vector<>(numList3);
//
//        Vector<Vector<Integer>> ols_vector = new Vector<Vector<Integer>>();
//        ols_vector.add(ols1);
//        ols_vector.add(ols2);
//        ols_vector.add(ols3);
//
//        MOLS MOLS_example = new MOLS(3,5,ols_vector);
//
//        OLS_HF ols_hf = new OLS_HF(5);
//
//        ols_hf.set(MOLS_example);
//
//        BFOLS bf_ols = new BFOLS(5,4,ols_hf);
//
//        SetRandomizer(simulation,bf_ols);
//        for (int i=0; i<numOfSampleRuns; i++){
//            SampleRandomizer(simulation,bf_ols);
//            simulation.statistics();
//            simulation.falsePositiveCounterInitialize();
//        }

/* ***********************************************OLS**************************************************************** */
     //trying to use the MOLS() initializer and insert through the function
     //intialize the MOLS data structure that will hold all the ols of different sizes
        int s = 5;
        MOLS mols_data = new MOLS();
        mols_data.insert_ols(s,numList1);
        mols_data.insert_ols(s,numList2);
        mols_data.insert_ols(s,numList3);

        BFOLS bf_mols = new BFOLS(s,4,mols_data);

        if(s*s < uniSize) {
            System.out.println("Universe size is bigger than ols can represent, might be an error");
        }
        SetRandomizer(simulation,bf_mols);
        for (int i=0; i<numOfSampleRuns; i++){
            SampleRandomizer(simulation,bf_mols);
            simulation.statistics();
            simulation.falsePositiveCounterInitialize();
        }

/* ***********************************************POL**************************************************************** */
        // initializing bloom filter & simulation
//        BFPOL bf_pol = new BFPOL(uniSize, 2, 2);

//        boolean randomSet = true;
//        if (randomSet){
//            // randomly choosing a set, updating simulation and adding to bloom filter
//            SetRandomizer(simulation,bf_pol);
//        } else {
//            bf_pol.insert(168);
//            bf_pol.insert(50);
//        }
//
//        // running all checks
//        for (int i=0; i<numOfSampleRuns; i++){
//            SampleRandomizer(simulation,bf_pol);
//            simulation.statistics();
//            simulation.falsePositiveCounterInitialize();
//        }

        System.out.println("\nDone!");
    }
}
