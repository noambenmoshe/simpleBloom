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

    static void ScanUniverseElements(Simulation sim, BloomFilter bloomFilter) {
        for (int i = 0; i < sim.getN(); i++) {
            boolean inSet = sim.searchSet(i);
            boolean bloomAns = bloomFilter.search(i);
            if (bloomAns && !inSet) {
                sim.falsePositiveCounterIncrement();
                System.out.println("False Positive number: " + i + "\n");
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

        // 3 mols of s=5 from Ori's paper
        List<Integer> numList1 = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
        List<Integer> numList2 = Arrays.asList(0,1,2,3,4,4,0,1,2,3,3,4,0,1,2,2,3,4,0,1,1,2,3,4,0);
        List<Integer> numList3 = Arrays.asList(0,1,2,3,4,3,4,0,1,2,1,2,3,4,0,4,0,1,2,3,2,3,4,0,1);
        // 4 of s=3
        List<Integer>numList3_0 = Arrays.asList(1,2,3,2,3,1,3,1,2);
        List<Integer>numList3_1 = Arrays.asList(1,2,3,3,1,2,2,3,1);
        List<Integer>numList3_2 = Arrays.asList(1,3,2,2,1,3,3,2,1);
        List<Integer>numList3_3 = Arrays.asList(2,1,3,1,3,2,3,2,1);
        // 3 of s=11
        List<Integer>numList11_4 = Arrays.asList(1,3,5,7,9,11,2,4,6,8,10,11,2,4,6,8,10,1,3,5,7,9,10,1,3,5,7,9,11,2,4,6,8,9,11,2,4,6,8,10,1,3,5,7,8,10,1,3,5,7,9,11,2,4,6,7,9,11,2,4,6,8,10,1,3,5,6,8,10,1,3,5,7,9,11,2,4,5,7,9,11,2,4,6,8,10,1,3,4,6,8,10,1,3,5,7,9,11,2,3,5,7,9,11,2,4,6,8,10,1,2,4,6,8,10,1,3,5,7,9,11);
        List<Integer>numList11_5 = Arrays.asList(1,4,7,10,2,5,8,11,3,6,9,10,2,5,8,11,3,6,9,1,4,7,8,11,3,6,9,1,4,7,10,2,5,6,9,1,4,7,10,2,5,8,11,3,4,7,10,2,5,8,11,3,6,9,1,2,5,8,11,3,6,9,1,4,7,10,11,3,6,9,1,4,7,10,2,5,8,9,1,4,7,10,2,5,8,11,3,6,7,10,2,5,8,11,3,6,9,1,4,5,8,11,3,6,9,1,4,7,10,2,3,6,9,1,4,7,10,2,5,8,11);
        List<Integer>numList11_6 = Arrays.asList(2,5,9,1,6,10,3,7,11,4,8,9,1,6,10,3,7,11,4,8,2,5,6,10,3,7,11,4,8,2,5,9,1,3,7,11,4,8,2,5,9,1,6,10,11,4,8,2,5,9,1,6,10,3,7,8,2,5,9,1,6,10,3,7,11,4,5,9,1,6,10,3,7,11,4,8,2,1,6,10,3,7,11,4,8,2,5,9,10,3,7,11,4,8,2,5,9,1,6,7,11,4,8,2,5,9,1,6,10,3,4,8,2,5,9,1,6,10,3,7,11);
        // 7 of s=9
        List<Integer>numList9_7 = Arrays.asList(1,2,3,4,5,6,7,8,9,2,3,4,5,6,7,8,9,1,3,1,2,6,4,5,9,7,8,4,8,6,7,2,9,1,5,3,5,6,1,8,9,4,2,3,7,6,7,8,9,1,2,3,4,5,7,5,9,1,8,3,4,2,6,8,9,7,2,3,1,5,6,4,9,4,5,3,7,8,6,1,2);
        List<Integer>numList9_8 = Arrays.asList(1,2,3,4,5,6,7,8,9,8,6,1,2,9,4,5,3,7,7,8,9,1,2,3,4,5,6,5,3,7,8,6,1,2,9,4,6,4,5,9,7,8,3,1,2,3,1,2,6,4,5,9,7,8,9,7,8,3,1,2,6,4,5,4,5,6,7,8,9,1,2,3,2,9,4,5,3,7,8,6,1);
        List<Integer>numList9_9 = Arrays.asList(1,2,3,4,5,6,7,8,9,9,1,5,3,4,8,6,7,2,4,5,6,7,8,9,1,2,3,3,4,8,6,7,2,9,1,5,8,9,7,2,3,1,5,6,4,2,3,1,5,6,4,8,9,7,5,6,4,8,9,7,2,3,1,7,8,9,1,2,3,4,5,6,6,7,2,9,1,5,3,4,8);
        List<Integer>numList9_10 = Arrays.asList(1,2,3,4,5,6,7,8,9,7,5,6,1,8,9,4,2,3,6,4,5,9,7,8,3,1,2,9,7,2,3,1,5,6,4,8,3,1,8,6,4,2,9,7,5,4,8,9,7,2,3,1,5,6,2,9,1,5,3,4,8,6,7,5,6,4,8,9,7,2,3,1,8,3,7,2,6,1,5,9,4);
        List<Integer>numList9_11 = Arrays.asList(1,2,3,4,5,6,7,8,9,6,7,8,9,1,2,3,4,5,2,9,4,5,3,7,8,6,1,7,5,9,1,8,3,4,2,6,4,3,2,7,6,5,1,9,8,8,4,6,2,7,9,5,1,3,3,8,7,6,2,1,9,5,4,9,1,5,3,4,8,6,7,2,5,6,1,8,9,4,2,3,7);
        List<Integer>numList9_12 = Arrays.asList(1,2,3,4,5,6,7,8,9,5,9,7,8,3,1,2,6,4,8,6,1,2,9,4,5,3,7,6,1,5,9,4,8,3,7,2,2,7,9,5,1,3,8,4,6,9,5,4,3,8,7,6,2,1,4,3,2,7,6,5,1,9,8,3,4,8,6,7,2,9,1,5,7,8,6,1,2,9,4,5,3);
        List<Integer>numList9_13 = Arrays.asList(1,2,3,4,5,6,7,8,9,4,8,9,7,2,3,1,5,6,5,3,7,8,6,1,2,9,4,2,9,1,5,3,4,8,6,7,9,5,4,3,8,7,6,2,1,7,6,5,1,9,8,4,3,2,8,4,6,2,7,9,5,1,3,6,7,2,9,1,5,3,4,8,3,1,8,6,4,2,9,7,5);

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
        // from paper:
        mols_data.insert_ols(s,numList1);
        mols_data.insert_ols(s,numList2);
        mols_data.insert_ols(s,numList3);
        // from script (Monash):
        mols_data.insert_ols(3, numList3_0);
        mols_data.insert_ols(3, numList3_1);
        mols_data.insert_ols(3, numList3_2);
        mols_data.insert_ols(3, numList3_3);
        mols_data.insert_ols(11, numList11_4);
        mols_data.insert_ols(11, numList11_5);
        mols_data.insert_ols(11, numList11_6);
        mols_data.insert_ols(9, numList9_7);
        mols_data.insert_ols(9, numList9_8);
        mols_data.insert_ols(9, numList9_9);
        mols_data.insert_ols(9, numList9_10);
        mols_data.insert_ols(9, numList9_11);
        mols_data.insert_ols(9, numList9_12);
        mols_data.insert_ols(9, numList9_13);


        BFOLS bf_mols = new BFOLS(s,4,mols_data);

        if(s*s < uniSize) {
            System.out.println("Universe size is bigger than ols can represent, might be an error");
        }
        SetRandomizer(simulation,bf_mols);
        for (int i=0; i<numOfSampleRuns; i++){
            ScanUniverseElements(simulation,bf_mols);
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
