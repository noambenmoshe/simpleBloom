import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.lang.String;
import java.io.File;
import java.io.IOException;
public class Main {
    //Randomise numbers that will be inserted to bloomfilter and Insert them to the universe
    static void SetRandomizer(Simulation sim, BloomFilter bloomFilter){
        Random randClass = new Random();

        for(int i = 0; i < sim.getSizeOfSample(); i++){
            int rand = randClass.nextInt(sim.getN());
            sim.insertToUniverse(rand);
            bloomFilter.insert(rand);
            System.out.println("BF insert: " + rand + "\n");
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
                //System.out.println("False Positive number: " + rand + "\n");
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
            //uniSize = 24;
            //setSize = 2;
            numOfSampleRuns = 10;
        }
        // Printing Parameters
        System.out.println("Running a test with:\n\tBF type\t\t"+bfType);
        System.out.println("\tNumber of runs\t\t"+numOfSampleRuns);

        String filePOLLengthName ="output_for_graphs_pol_length.txt";
        String fileOLSLengthName ="output_for_graphs_ols_length.txt";
        String filePOLFalsePosName ="output_for_graphs_pol_FP.txt";
        String fileOLSFalsePosName ="output_for_graphs_ols_FP.txt";
        try {
            File filePOL = new File(filePOLLengthName);
            File fileOLS = new File(fileOLSLengthName);
            File filePOLFalsePos = new File(filePOLFalsePosName);
            File fileOLSFalsePos = new File(fileOLSFalsePosName);
            if (filePOL.createNewFile()) {
                System.out.println("File created: " + filePOL.getName());
            } else {
                System.out.println("File already exists.");
            }
            if (fileOLS.createNewFile()) {
                System.out.println("File created: " + fileOLS.getName());
            } else {
                System.out.println("File already exists.");
            }
            if (filePOLFalsePos.createNewFile()) {
                System.out.println("File created: " + filePOLFalsePos.getName());
            } else {
                System.out.println("File already exists.");
            }
            if (fileOLSFalsePos.createNewFile()) {
                System.out.println("File created: " + fileOLSFalsePos.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        Simulation simulation;

        if(bfType.equals("OLS"))
        {
            /* ********************************************OLS******************************************************* */
            if(false){
                try {
                    System.out.println("Calculating OLS filter length graph.");
                    FileWriter myWriter = new FileWriter(fileOLSLengthName);
                    myWriter.write("OLS\n");

                    create_data_for_graph_length(3, myWriter,bfType);
                    create_data_for_graph_length(4, myWriter,bfType);
                    create_data_for_graph_length(7, myWriter,bfType);

                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }
        else{
                try {
                    System.out.println("Calculating OLS False Positive graph.");
                    FileWriter myWriter = new FileWriter(fileOLSFalsePosName);
                    myWriter.write("OLS\n");
                    uniSize = 256;
                    int d = 3;
                    double avg;
                    myWriter.write("d = "+d+" n = "+uniSize+"\n");
                    List<Double> statistics = new java.util.ArrayList<>(Collections.emptyList());
                    for(int sampleSize = 0; sampleSize < 33; sampleSize++){
                        System.out.println("Insert "+sampleSize+" elements to BF.");
                        BFOLS bf_mols = new BFOLS(uniSize ,d);
                        simulation = new Simulation(uniSize, d, sampleSize);
                        SetRandomizer(simulation,bf_mols);
                        for (int i=0; i<numOfSampleRuns; i++){
                            ScanUniverseElements(simulation,bf_mols);
                            //System.out.println("stat double =" + simulation.statistics());
                            statistics.add(simulation.statistics());
                            simulation.falsePositiveCounterInitialize();
                        }
                        avg = calc_stats_from_simuli(statistics);
                        myWriter.write(sampleSize+","+avg+"\n");
                        statistics.clear();
                    }

                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }



        }
        else if(bfType.equals("POL"))
        {
            /* ***********************************************POL**************************************************************** */
            // initializing bloom filter & simulation
            //for graphs
            if(false){
                try {
                    System.out.println("Calculating POL filter length graph.");
                    FileWriter myWriter = new FileWriter(filePOLLengthName);
                    myWriter.write("POL\n");

                    create_data_for_graph_length(3, myWriter,bfType);
                    create_data_for_graph_length(4, myWriter,bfType);
                    create_data_for_graph_length(7, myWriter,bfType);

                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }else{
                try {
                    System.out.println("Calculating POL False Positive graph.");
                    FileWriter myWriter = new FileWriter(filePOLFalsePosName);
                    myWriter.write("POL\n");
                    uniSize = 343;
                    int d = 3;
                    double avg;
                    myWriter.write("d = "+d+" n = "+uniSize+"\n");
                    List<Double> statistics = new java.util.ArrayList<>(Collections.emptyList());
                    for(int sampleSize = 0; sampleSize < 33; sampleSize++){
                        System.out.println("Insert "+sampleSize+" elements to BF.");
                        BFPOL bf_pol = new BFPOL(uniSize ,d);
                        simulation = new Simulation(uniSize, d, sampleSize);
                        SetRandomizer(simulation,bf_pol);
                        for (int i=0; i<numOfSampleRuns; i++){
                            ScanUniverseElements(simulation,bf_pol);

                            statistics.add(simulation.statistics());
                            simulation.falsePositiveCounterInitialize();
                        }
                        avg = calc_stats_from_simuli(statistics);
                        myWriter.write(sampleSize+","+avg+"\n");
                        statistics.clear();
                    }

                    myWriter.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }


            //BFPOL bf_pol = new BFPOL(361, 3);
            //simulation = new Simulation(uniSize, setSize, 5); //TODO: either add or remove sample size
            if (false){ //Change if you want to choose the set by yourself
                // randomly choosing a set, updating simulation and adding to bloom filter
                //SetRandomizer(simulation,bf_pol);
            } else {
                //bf_pol.insert(168);
                //bf_pol.insert(50);
            }

            // running all checks
//            for (int i=0; i<numOfSampleRuns; i++){
//                ScanUniverseElements(simulation,bf_pol);
//                simulation.statistics();
//                simulation.falsePositiveCounterInitialize();
//            }
        }

        System.out.println("\nDone!");
    }

    private static void create_data_for_graph_length(int d, FileWriter myWriter, String bfType) throws IOException {
        int n;
        BloomFilter bf;
        myWriter.write(d+"\n");
        for(int i =4; i< 18; i++){
            n = (int)Math.pow(2, i);
            if(bfType.equals("POL")){
                bf = new BFPOL(n, d);
            }
            else if(bfType.equals("OLS")){
                bf =new BFOLS(n,d);
            }else return;

            if(bf.getM() != -1) {
                myWriter.write(n+","+bf.getM()+"\n");
            }
        }
    }

    private static double calc_stats_from_simuli(List<Double> stats){
        double avg = 0;
        for(double i: stats) {
            avg += i;
        }
        avg = avg/stats.size();
        return avg;
    }
}
