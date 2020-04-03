import java.util.Random;
//import java.Integer.parseInt()

public class MainCompare {

    static void SetRandomizer(Simulation sim, Simulation sim2, BloomFilter bloomFilter,  BloomFilter bloomFilter2){
        Random randClass = new Random();
        for(int i = 0; i < sim.getSizeOfS(); i++){
            int rand = randClass.nextInt(sim.getN());
            sim.insertToUniverse(rand);
            sim2.insertToUniverse(rand);
            bloomFilter.insert(rand);
            bloomFilter2.insert(rand);
        }
    }

    static void SampleRandomizer(Simulation sim, Simulation sim2, BloomFilter bloomFilter,  BloomFilter bloomFilter2){
        Random randClass = new Random();
        for(int i = 0; i < sim.getSizeOfSample(); i++){
            int rand = randClass.nextInt(sim.getN());
            boolean inSet = sim.searchSet(rand);
            boolean inSet2 = sim2.searchSet(rand);
            boolean bloomAns = bloomFilter.search(rand);
            boolean bloomAns2 = bloomFilter2.search(rand);
            if (bloomAns && !inSet)
                sim.falsePositiveCounterIncrement();
            if (bloomAns2 && !inSet2)
                sim2.falsePositiveCounterIncrement();
        }
    }

    public static void main(String[] args) {

        // interpreting input arguments
        /*int uniSize = Integer.parseInt(args[0]);
        int setSize = Integer.parseInt(args[1]);
        int sampleSize = Integer.parseInt(args[2]);
        int numOfSampleRuns = Integer.parseInt(args[3]);
        int bloomVecLength = Integer.parseInt(args[4]);
        int numOfHashFuncs = Integer.parseInt(args[5]);*/

        int uniSize = 100;
        int setSize = 10;
        int sampleSize = 50;
        int numOfSampleRuns = 5;
        int bloomVecLength = 8;
        int numOfHashFuncs = 2;
        System.out.println("Running a test with:\n\tUniverse Size\t\t"+uniSize+"\t\t Set Size\t\t\t"+setSize);
        System.out.println("\tSample Size\t\t\t"+sampleSize+"\t\t Num of Runs\t\t"+numOfSampleRuns);
        System.out.println("\tBloom Vec Length\t"+bloomVecLength+"\t\t Num of Hash funcs\t"+numOfHashFuncs);

        // initializing bloom filter & simulation
        BloomFilter b1= new Bloom(bloomVecLength,numOfHashFuncs);
        BloomFilter b2= new Bloom(bloomVecLength,numOfHashFuncs);
        Simulation simulation= new Simulation(uniSize, setSize, sampleSize);
        Simulation simulation2= new Simulation(uniSize, setSize, sampleSize);

        // randomly choosing a set, updating simulation and adding to bloom filter
        SetRandomizer(simulation,simulation2,b1,b2);

        // running all checks
        for (int i=0; i<numOfSampleRuns; i++){
            SampleRandomizer(simulation,simulation2,b1,b2);
            System.out.println("SIM1:");
            simulation.statistics();
            simulation.falsePositiveCounterInitialize();
            System.out.println("SIM2:");
            simulation2.statistics();
            simulation2.falsePositiveCounterInitialize();
        }

        System.out.println("\nDone!");
    }
}
