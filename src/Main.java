import java.util.Random;
//import java.Integer.parseInt()

public class Main {

    static void SetRandomizer(Simulation sim, Bloom bloomFilter){
        Random randClass = new Random();
        for(int i = 0; i < sim.getSizeOfS(); i++){
            int rand = randClass.nextInt(sim.getN());
            sim.AddToUniverse(rand);
            bloomFilter.Add(rand);
        }
    }

    static void SampleRandomizer(Simulation sim, Bloom bloomFilter){
        Random randClass = new Random();
        for(int i = 0; i < sim.getSizeOfSample(); i++){
            int rand = randClass.nextInt(sim.getN());
            boolean inSet = sim.checkIfInSet(rand);
            boolean bloomAns = bloomFilter.checkIfExists(rand);
            if (bloomAns && !inSet)
                sim.falsePositiveCounterIncrement();
        }
    }

    public static void main(String[] args) {

        // interpreting input arguments
        int uniSize = Integer.parseInt(args[1]);
        int setSize = Integer.parseInt(args[2]);
        int sampleSize = Integer.parseInt(args[3]);
        int numOfSampleRuns = Integer.parseInt(args[4]);
        int bloomVecLength = Integer.parseInt(args[5]);
        int numOfHashFuncs = Integer.parseInt(args[6]);

        // initializing bloom filter & simulation
        Bloom b1= new Bloom(bloomVecLength,numOfHashFuncs);
        Simulation simulation= new Simulation(uniSize, setSize, sampleSize);

        // randomly choosing a set, updating simulation and adding to bloom filter
        SetRandomizer(simulation,b1);

        // running all checks
        for (int i=0; i<numOfSampleRuns; i++){
            SampleRandomizer(simulation,b1);
            simulation.statistics();
            simulation.falsePositiveCounterInitialize();
        }

        System.out.println("Done!");
    }
}
