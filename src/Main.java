import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.String;
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

    static void initializeMOLSData(int s, MOLS mols_data) {
        // 3 mols of s=5 from Ori's paper
        List<Integer> numList5_0_paper = Arrays.asList(0,1,2,3,4,1,2,3,4,0,2,3,4,0,1,3,4,0,1,2,4,0,1,2,3);
        List<Integer> numList5_1_paper = Arrays.asList(0,1,2,3,4,4,0,1,2,3,3,4,0,1,2,2,3,4,0,1,1,2,3,4,0);
        List<Integer> numList5_2_paper = Arrays.asList(0,1,2,3,4,3,4,0,1,2,1,2,3,4,0,4,0,1,2,3,2,3,4,0,1);
        // MOLS from https://users.monash.edu.au/~iwanless/data/MOLS/
        // 2 of s=3
        List<Integer> numList3_0 = Arrays.asList(1,2,3,2,3,1,3,1,2);
        List<Integer> numList3_1 = Arrays.asList(1,2,3,3,1,2,2,3,1);
        // 3 of s=4
        List<Integer> numList4_0 = Arrays.asList(1,2,3,4,2,1,4,3,3,4,1,2,4,3,2,1);
        List<Integer> numList4_1 = Arrays.asList(1,2,3,4,3,4,1,2,4,3,2,1,2,1,4,3);
        List<Integer> numList4_2 = Arrays.asList(1,2,3,4,4,3,2,1,2,1,4,3,3,4,1,2);
        // 4 of s=5
        List<Integer> numList5_0 = Arrays.asList(1,2,3,4,5,2,3,4,5,1,3,4,5,1,2,4,5,1,2,3,5,1,2,3,4);
        List<Integer> numList5_1 = Arrays.asList(1,2,3,4,5,5,1,2,3,4,4,5,1,2,3,3,4,5,1,2,2,3,4,5,1);
        List<Integer> numList5_2 = Arrays.asList(1,2,3,4,5,4,5,1,2,3,2,3,4,5,1,5,1,2,3,4,3,4,5,1,2);
        List<Integer> numList5_3 = Arrays.asList(1,2,3,4,5,3,4,5,1,2,5,1,2,3,4,2,3,4,5,1,4,5,1,2,3);
        // 5 of s=7
        List<Integer> numList7_0 = Arrays.asList(1,2,3,4,5,6,7,2,3,4,5,6,7,1,3,4,5,6,7,1,2,4,5,6,7,1,2,3,5,6,7,1,2,3,4,6,7,1,2,3,4,5,7,1,2,3,4,5,6);
        List<Integer> numList7_1 = Arrays.asList(1,2,3,4,5,6,7,7,1,2,3,4,5,6,6,7,1,2,3,4,5,5,6,7,1,2,3,4,4,5,6,7,1,2,3,3,4,5,6,7,1,2,2,3,4,5,6,7,1);
        List<Integer> numList7_2 = Arrays.asList(1,2,3,4,5,6,7,6,7,1,2,3,4,5,4,5,6,7,1,2,3,2,3,4,5,6,7,1,7,1,2,3,4,5,6,5,6,7,1,2,3,4,3,4,5,6,7,1,2);
        List<Integer> numList7_3 = Arrays.asList(1,2,3,4,5,6,7,5,6,7,1,2,3,4,2,3,4,5,6,7,1,6,7,1,2,3,4,5,3,4,5,6,7,1,2,7,1,2,3,4,5,6,4,5,6,7,1,2,3);
        List<Integer> numList7_4 = Arrays.asList(1,2,3,4,5,6,7,4,5,6,7,1,2,3,7,1,2,3,4,5,6,3,4,5,6,7,1,2,6,7,1,2,3,4,5,2,3,4,5,6,7,1,5,6,7,1,2,3,4);
        // 7 of s=8
        List<Integer> numList8_0 = Arrays.asList(1,2,3,4,5,8,6,7,2,1,4,3,8,5,7,6,3,4,1,2,7,6,8,5,4,3,2,1,6,7,5,8,5,8,7,6,1,2,4,3,8,5,6,7,2,1,3,4,6,7,8,5,4,3,1,2,7,6,5,8,3,4,2,1);
        List<Integer> numList8_1 = Arrays.asList(1,2,3,4,5,6,7,8,3,4,1,2,8,7,6,5,5,6,8,7,1,2,4,3,8,7,5,6,3,4,2,1,4,3,2,1,7,8,5,6,2,1,4,3,6,5,8,7,6,5,7,8,2,1,3,4,7,8,6,5,4,3,1,2);
        List<Integer> numList8_2 = Arrays.asList(1,2,3,4,5,6,7,8,4,3,2,1,7,8,5,6,8,7,5,6,3,4,2,1,6,5,7,8,2,1,3,4,7,8,6,5,4,3,1,2,5,6,8,7,1,2,4,3,3,4,1,2,8,7,6,5,2,1,4,3,6,5,8,7);
        List<Integer> numList8_3 = Arrays.asList(1,2,3,4,5,6,7,8,5,6,8,7,1,2,4,3,4,3,2,1,7,8,5,6,7,8,6,5,4,3,1,2,8,7,5,6,3,4,2,1,3,4,1,2,8,7,6,5,2,1,4,3,6,5,8,7,6,5,7,8,2,1,3,4);
        List<Integer> numList8_4 = Arrays.asList(1,2,3,4,5,6,7,8,6,5,7,8,2,1,3,4,2,1,4,3,6,5,8,7,5,6,8,7,1,2,4,3,3,4,1,2,8,7,6,5,7,8,6,5,4,3,1,2,8,7,5,6,3,4,2,1,4,3,2,1,7,8,5,6);
        List<Integer> numList8_5 = Arrays.asList(1,2,3,4,5,6,7,8,7,8,6,5,4,3,1,2,6,5,7,8,2,1,3,4,3,4,1,2,8,7,6,5,2,1,4,3,6,5,8,7,8,7,5,6,3,4,2,1,4,3,2,1,7,8,5,6,5,6,8,7,1,2,4,3);
        List<Integer> numList8_6 = Arrays.asList(1,2,3,4,5,6,7,8,8,7,5,6,3,4,2,1,7,8,6,5,4,3,1,2,2,1,4,3,6,5,8,7,6,5,7,8,2,1,3,4,4,3,2,1,7,8,5,6,5,6,8,7,1,2,4,3,3,4,1,2,8,7,6,5);
        // 8 of s=9
        List<Integer> numList9_0 = Arrays.asList(1,2,3,4,5,6,7,8,9,2,3,4,5,6,7,8,9,1,3,1,2,6,4,5,9,7,8,4,8,6,7,2,9,1,5,3,5,6,1,8,9,4,2,3,7,6,7,8,9,1,2,3,4,5,7,5,9,1,8,3,4,2,6,8,9,7,2,3,1,5,6,4,9,4,5,3,7,8,6,1,2);
        List<Integer> numList9_1 = Arrays.asList(1,2,3,4,5,6,7,8,9,8,6,1,2,9,4,5,3,7,7,8,9,1,2,3,4,5,6,5,3,7,8,6,1,2,9,4,6,4,5,9,7,8,3,1,2,3,1,2,6,4,5,9,7,8,9,7,8,3,1,2,6,4,5,4,5,6,7,8,9,1,2,3,2,9,4,5,3,7,8,6,1);
        List<Integer> numList9_2 = Arrays.asList(1,2,3,4,5,6,7,8,9,9,1,5,3,4,8,6,7,2,4,5,6,7,8,9,1,2,3,3,4,8,6,7,2,9,1,5,8,9,7,2,3,1,5,6,4,2,3,1,5,6,4,8,9,7,5,6,4,8,9,7,2,3,1,7,8,9,1,2,3,4,5,6,6,7,2,9,1,5,3,4,8);
        List<Integer> numList9_3 = Arrays.asList(1,2,3,4,5,6,7,8,9,7,5,6,1,8,9,4,2,3,6,4,5,9,7,8,3,1,2,9,7,2,3,1,5,6,4,8,3,1,8,6,4,2,9,7,5,4,8,9,7,2,3,1,5,6,2,9,1,5,3,4,8,6,7,5,6,4,8,9,7,2,3,1,8,3,7,2,6,1,5,9,4);
        List<Integer> numList9_4 = Arrays.asList(1,2,3,4,5,6,7,8,9,6,7,8,9,1,2,3,4,5,2,9,4,5,3,7,8,6,1,7,5,9,1,8,3,4,2,6,4,3,2,7,6,5,1,9,8,8,4,6,2,7,9,5,1,3,3,8,7,6,2,1,9,5,4,9,1,5,3,4,8,6,7,2,5,6,1,8,9,4,2,3,7);
        List<Integer> numList9_5 = Arrays.asList(1,2,3,4,5,6,7,8,9,5,9,7,8,3,1,2,6,4,8,6,1,2,9,4,5,3,7,6,1,5,9,4,8,3,7,2,2,7,9,5,1,3,8,4,6,9,5,4,3,8,7,6,2,1,4,3,2,7,6,5,1,9,8,3,4,8,6,7,2,9,1,5,7,8,6,1,2,9,4,5,3);
        List<Integer> numList9_6 = Arrays.asList(1,2,3,4,5,6,7,8,9,4,8,9,7,2,3,1,5,6,5,3,7,8,6,1,2,9,4,2,9,1,5,3,4,8,6,7,9,5,4,3,8,7,6,2,1,7,6,5,1,9,8,4,3,2,8,4,6,2,7,9,5,1,3,6,7,2,9,1,5,3,4,8,3,1,8,6,4,2,9,7,5);
        List<Integer> numList9_7 = Arrays.asList(1,2,3,4,5,6,7,8,9,3,4,2,6,7,5,9,1,8,9,7,8,3,1,2,6,4,5,8,6,4,2,9,7,5,3,1,7,8,6,1,2,9,4,5,3,5,9,7,8,3,1,2,6,4,6,1,5,9,4,8,3,7,2,2,3,1,5,6,4,8,9,7,4,5,9,7,8,3,1,2,6);
        // 3 of s=11
        List<Integer> numList11_0 = Arrays.asList(1,3,5,7,9,11,2,4,6,8,10,11,2,4,6,8,10,1,3,5,7,9,10,1,3,5,7,9,11,2,4,6,8,9,11,2,4,6,8,10,1,3,5,7,8,10,1,3,5,7,9,11,2,4,6,7,9,11,2,4,6,8,10,1,3,5,6,8,10,1,3,5,7,9,11,2,4,5,7,9,11,2,4,6,8,10,1,3,4,6,8,10,1,3,5,7,9,11,2,3,5,7,9,11,2,4,6,8,10,1,2,4,6,8,10,1,3,5,7,9,11);
        List<Integer> numList11_1 = Arrays.asList(1,4,7,10,2,5,8,11,3,6,9,10,2,5,8,11,3,6,9,1,4,7,8,11,3,6,9,1,4,7,10,2,5,6,9,1,4,7,10,2,5,8,11,3,4,7,10,2,5,8,11,3,6,9,1,2,5,8,11,3,6,9,1,4,7,10,11,3,6,9,1,4,7,10,2,5,8,9,1,4,7,10,2,5,8,11,3,6,7,10,2,5,8,11,3,6,9,1,4,5,8,11,3,6,9,1,4,7,10,2,3,6,9,1,4,7,10,2,5,8,11);
        List<Integer> numList11_2 = Arrays.asList(2,5,9,1,6,10,3,7,11,4,8,9,1,6,10,3,7,11,4,8,2,5,6,10,3,7,11,4,8,2,5,9,1,3,7,11,4,8,2,5,9,1,6,10,11,4,8,2,5,9,1,6,10,3,7,8,2,5,9,1,6,10,3,7,11,4,5,9,1,6,10,3,7,11,4,8,2,1,6,10,3,7,11,4,8,2,5,9,10,3,7,11,4,8,2,5,9,1,6,7,11,4,8,2,5,9,1,6,10,3,4,8,2,5,9,1,6,10,3,7,11);
        // 3 of s=19
        List<Integer> numList19_0 = Arrays.asList(19,2,4,6,8,10,12,14,16,18,1,3,5,7,9,11,13,15,17,1,3,5,7,9,11,13,15,17,19,2,4,6,8,10,12,14,16,18,18,1,3,5,7,9,11,13,15,17,19,2,4,6,8,10,12,14,16,17,19,2,4,6,8,10,12,14,16,18,1,3,5,7,9,11,13,15,16,18,1,3,5,7,9,11,13,15,17,19,2,4,6,8,10,12,14,15,17,19,2,4,6,8,10,12,14,16,18,1,3,5,7,9,11,13,14,16,18,1,3,5,7,9,11,13,15,17,19,2,4,6,8,10,12,13,15,17,19,2,4,6,8,10,12,14,16,18,1,3,5,7,9,11,12,14,16,18,1,3,5,7,9,11,13,15,17,19,2,4,6,8,10,11,13,15,17,19,2,4,6,8,10,12,14,16,18,1,3,5,7,9,10,12,14,16,18,1,3,5,7,9,11,13,15,17,19,2,4,6,8,9,11,13,15,17,19,2,4,6,8,10,12,14,16,18,1,3,5,7,8,10,12,14,16,18,1,3,5,7,9,11,13,15,17,19,2,4,6,7,9,11,13,15,17,19,2,4,6,8,10,12,14,16,18,1,3,5,6,8,10,12,14,16,18,1,3,5,7,9,11,13,15,17,19,2,4,5,7,9,11,13,15,17,19,2,4,6,8,10,12,14,16,18,1,3,4,6,8,10,12,14,16,18,1,3,5,7,9,11,13,15,17,19,2,3,5,7,9,11,13,15,17,19,2,4,6,8,10,12,14,16,18,1,2,4,6,8,10,12,14,16,18,1,3,5,7,9,11,13,15,17,19);
        List<Integer> numList19_1 = Arrays.asList(18,2,5,8,11,14,17,1,4,7,10,13,16,19,3,6,9,12,15,1,4,7,10,13,16,19,3,6,9,12,15,18,2,5,8,11,14,17,16,19,3,6,9,12,15,18,2,5,8,11,14,17,1,4,7,10,13,14,17,1,4,7,10,13,16,19,3,6,9,12,15,18,2,5,8,11,12,15,18,2,5,8,11,14,17,1,4,7,10,13,16,19,3,6,9,10,13,16,19,3,6,9,12,15,18,2,5,8,11,14,17,1,4,7,8,11,14,17,1,4,7,10,13,16,19,3,6,9,12,15,18,2,5,6,9,12,15,18,2,5,8,11,14,17,1,4,7,10,13,16,19,3,4,7,10,13,16,19,3,6,9,12,15,18,2,5,8,11,14,17,1,2,5,8,11,14,17,1,4,7,10,13,16,19,3,6,9,12,15,18,19,3,6,9,12,15,18,2,5,8,11,14,17,1,4,7,10,13,16,17,1,4,7,10,13,16,19,3,6,9,12,15,18,2,5,8,11,14,15,18,2,5,8,11,14,17,1,4,7,10,13,16,19,3,6,9,12,13,16,19,3,6,9,12,15,18,2,5,8,11,14,17,1,4,7,10,11,14,17,1,4,7,10,13,16,19,3,6,9,12,15,18,2,5,8,9,12,15,18,2,5,8,11,14,17,1,4,7,10,13,16,19,3,6,7,10,13,16,19,3,6,9,12,15,18,2,5,8,11,14,17,1,4,5,8,11,14,17,1,4,7,10,13,16,19,3,6,9,12,15,18,2,3,6,9,12,15,18,2,5,8,11,14,17,1,4,7,10,13,16,19);
        List<Integer> numList19_2 = Arrays.asList(17,2,6,10,14,18,3,7,11,15,19,4,8,12,16,1,5,9,13,1,5,9,13,17,2,6,10,14,18,3,7,11,15,19,4,8,12,16,14,18,3,7,11,15,19,4,8,12,16,1,5,9,13,17,2,6,10,11,15,19,4,8,12,16,1,5,9,13,17,2,6,10,14,18,3,7,8,12,16,1,5,9,13,17,2,6,10,14,18,3,7,11,15,19,4,5,9,13,17,2,6,10,14,18,3,7,11,15,19,4,8,12,16,1,2,6,10,14,18,3,7,11,15,19,4,8,12,16,1,5,9,13,17,18,3,7,11,15,19,4,8,12,16,1,5,9,13,17,2,6,10,14,15,19,4,8,12,16,1,5,9,13,17,2,6,10,14,18,3,7,11,12,16,1,5,9,13,17,2,6,10,14,18,3,7,11,15,19,4,8,9,13,17,2,6,10,14,18,3,7,11,15,19,4,8,12,16,1,5,6,10,14,18,3,7,11,15,19,4,8,12,16,1,5,9,13,17,2,3,7,11,15,19,4,8,12,16,1,5,9,13,17,2,6,10,14,18,19,4,8,12,16,1,5,9,13,17,2,6,10,14,18,3,7,11,15,16,1,5,9,13,17,2,6,10,14,18,3,7,11,15,19,4,8,12,13,17,2,6,10,14,18,3,7,11,15,19,4,8,12,16,1,5,9,10,14,18,3,7,11,15,19,4,8,12,16,1,5,9,13,17,2,6,7,11,15,19,4,8,12,16,1,5,9,13,17,2,6,10,14,18,3,4,8,12,16,1,5,9,13,17,2,6,10,14,18,3,7,11,15,19);

        if(s <= 3) {
            mols_data.insert_ols(3, numList3_0);
            mols_data.insert_ols(3, numList3_1);
        } else if(s == 4){
            mols_data.insert_ols(4, numList4_0);
            mols_data.insert_ols(4, numList4_1);
            mols_data.insert_ols(4, numList4_2);
        } else if(s == 5) {
            mols_data.insert_ols(5, numList5_0);
            mols_data.insert_ols(5, numList5_1);
            mols_data.insert_ols(5, numList5_2);
            mols_data.insert_ols(5, numList5_3);
        } else if(s <= 7){
            mols_data.insert_ols(7, numList7_0);
            mols_data.insert_ols(7, numList7_1);
            mols_data.insert_ols(7, numList7_2);
            mols_data.insert_ols(7, numList7_3);
            mols_data.insert_ols(7, numList7_4);
        } else if(s == 8){
            mols_data.insert_ols(8, numList8_0);
            mols_data.insert_ols(8, numList8_1);
            mols_data.insert_ols(8, numList8_2);
            mols_data.insert_ols(8, numList8_3);
            mols_data.insert_ols(8, numList8_4);
            mols_data.insert_ols(8, numList8_5);
            mols_data.insert_ols(8, numList8_6);
        } else if(s == 9) {
            mols_data.insert_ols(9, numList9_0);
            mols_data.insert_ols(9, numList9_1);
            mols_data.insert_ols(9, numList9_2);
            mols_data.insert_ols(9, numList9_3);
            mols_data.insert_ols(9, numList9_4);
            mols_data.insert_ols(9, numList9_5);
            mols_data.insert_ols(9, numList9_6);
            mols_data.insert_ols(9, numList9_7);
        } else if(s <= 11) {
            mols_data.insert_ols(11, numList11_0);
            mols_data.insert_ols(11, numList11_1);
            mols_data.insert_ols(11, numList11_2);
        } else if(s <= 19){
            mols_data.insert_ols(19, numList19_0);
            mols_data.insert_ols(19, numList19_1);
            mols_data.insert_ols(19, numList19_2);
        }
    }

    public static void main(String[] args) {
        int uniSize, setSize, numOfSampleRuns;
        String bfType;
//        int bloomVecLength, numOfHashFuncs;
        if(args.length == 4){
            // interpreting input arguments
            bfType = args[0];
            uniSize = Integer.parseInt(args[1]);
            setSize = Integer.parseInt(args[2]);
            numOfSampleRuns = Integer.parseInt(args[3]);
//            bloomVecLength = Integer.parseInt(args[3]);
//            numOfHashFuncs = Integer.parseInt(args[4]);
        } else {
            bfType = "POL";
            uniSize = 24;
            setSize = 2;
            numOfSampleRuns = 1;
//            bloomVecLength = 5*(3+1);
//            numOfHashFuncs = (3+1);
        }
        // Printing Parameters
        System.out.println("Running a test with:\n\tUniverse Size\t\t"+uniSize+"\t\t Set Size\t\t\t"+setSize);
        System.out.println("\tNumber of runs\t\t"+numOfSampleRuns);
//        System.out.println("\tBloom Vec Length\t"+bloomVecLength+"\t\t Num of Hash funcs\t"+numOfHashFuncs);

        Simulation simulation= new Simulation(uniSize, setSize, 5); //TODO: either add or remove sample size

        if(bfType.equals("OLS"))
        {
            /* ********************************************OLS******************************************************* */
            // initializing the MOLS data structure that will hold all the ols of different sizes
            MOLS mols_data = new MOLS();
            int s = 5;

            initializeMOLSData(s, mols_data);

            BFOLS bf_mols = new BFOLS(s,4,mols_data);

            if(s*s < uniSize) {
                System.out.println("Universe size is bigger than ols can represent");
                System.exit(0);
            }
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
            BFPOL bf_pol = new BFPOL(uniSize, 2);

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
                ScanUniverseElements(simulation,bf_pol);
                simulation.statistics();
                simulation.falsePositiveCounterInitialize();
            }
        }

        System.out.println("\nDone!");
    }
}
