import java.util.*;

public class BFOLS implements BloomFilter {
    private int m; //filter length
    private int k; //number of hash functions
    private Vector<Integer> bloomFilter;
    private HashFunction hashFunction;

    // regular constructor
    public BFOLS(int m, int k, Map<Integer, Boolean> bloom_Filter, HashFunction hash_Function ) {
        this.m = m;
        this.k = k;
        this.bloomFilter = new Vector<>();
        this.hashFunction = hash_Function;
    }

    //inputs: s - represents the square root of the universe size
    //        d - represents the free zone size  / size of set you can insert with out false positives
    //        hashFunction - represents each number and it's mask bits
    public BFOLS(int s, int d, HashFunction hashFunction){
        this.m = s * (d + 1);
        this.k = d + 1;
        this.bloomFilter = new Vector<>();
        this.hashFunction = hashFunction;
    }

    //inputs: s - represents the square root of the universe size
    //        d - represents the free zone size  / size of set you can insert with out false positives
    public BFOLS(int unisize, int d){
        int s = (int) Math.ceil(Math.pow(unisize, 1.0/(double)2));
        this.m = s * (d + 1);
        this.k = d + 1;
        this.bloomFilter = new Vector<>();
        OLS_HF ols_hf = new OLS_HF(s);
        MOLS mols_data = new MOLS();
        initializeMOLSData(s, mols_data);
        ols_hf.set(mols_data);
        this.hashFunction = ols_hf;

        System.out.println("BFOLS values s = " + s);
        if (d > mols_data.choose_OLS_vec(s).size() + 1){
            System.out.println("set is bigger than BF can represent without false positives");
        }
    }

    void initializeMOLSData(int s, MOLS mols_data) {
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

    @Override
    // gets vector that says which bits need to be true in the bloomFilter,
    // then changes each relevant bit to be true in bloomFilter
    public void insert(int number) {
        Vector<Integer> bit_mask = hashFunction.get(number);
        bloomFilter.addAll(bit_mask);
    }

    @Override
    // gets vector that says which bits need to be true in the bloomFilter,
    // then if all relevant bits are true in bloomFilter then number is found in bloomFilter return true
    // otherwise number is not found, return false
    public boolean search(int number) {
        Vector<Integer> bit_mask = hashFunction.get(number);
        for (int bit : bit_mask){
            if(!bloomFilter.contains(bit)){
                return false;
            }
        }
        return  true;
    }
}
