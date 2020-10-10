import java.util.Random;
import java.lang.String;

public class Main {
    //Randomise numbers that will be inserted to bloomfilter and Insert them to the universe
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
        int uniSize, setSize, numOfSampleRuns;
        String bfType;

        if(args.length == 10){ //TODO: change to 4 when we actually want to use input args
            // interpreting input arguments
            bfType = args[0];
            uniSize = Integer.parseInt(args[1]);
            setSize = Integer.parseInt(args[2]);
            numOfSampleRuns = Integer.parseInt(args[3]);
        } else {
            bfType = "OLS";
            uniSize = 24;
            setSize = 2;
            numOfSampleRuns = 1;
        }
        // Printing Parameters
        System.out.println("Running a test with:\n\tUniverse Size\t\t"+uniSize+"\t\t Set Size\t\t\t"+setSize);
        System.out.println("\tNumber of runs\t\t"+numOfSampleRuns);

        Simulation simulation= new Simulation(uniSize, setSize, 5); //TODO: either add or remove sample size

        if(bfType.equals("OLS"))
        {
            /* ********************************************OLS******************************************************* */
            // initializing the MOLS data structure that will hold all the ols of different sizes

            BFOLS bf_mols = new BFOLS(uniSize ,setSize);

            SetRandomizer(simulation,bf_mols);
            for (int i=0; i<numOfSampleRuns; i++){
                ScanUniverseElements(simulation,bf_mols);
                simulation.statistics();
                simulation.falsePositiveCounterInitialize();
            }
        }
        else if(bfType.equals("POL"))
        {
            /* ***********************************************POL**************************************************************** */
            // initializing bloom filter & simulation
            BFPOL bf_pol = new BFPOL(uniSize, setSize);

            if (true){ //Change if you want to choose the set by yourself
                // randomly choosing a set, updating simulation and adding to bloom filter
                SetRandomizer(simulation,bf_pol);
            } else {
                bf_pol.insert(168);
                bf_pol.insert(50);
            }

            // running all checks
            for (int i=0; i<numOfSampleRuns; i++){
                ScanUniverseElements(simulation,bf_pol);
                simulation.statistics();
                simulation.falsePositiveCounterInitialize();
            }
        }

        System.out.println("\nDone!");
    }
}
