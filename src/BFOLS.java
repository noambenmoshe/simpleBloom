
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class BFOLS implements BloomFilter {
    private int m; //filter length
    private int k; //number of hash functions
    private Map<Integer, Boolean> bloomFilter;
    private HashFunction hashFunction;

    // regular constructor
    public BFOLS(int m, int k, Map<Integer, Boolean> bloom_Filter, HashFunction hash_Function ) {
        this.m = m;
        this.k = k;
        this.bloomFilter = new HashMap<>();
        this.hashFunction = hash_Function;
    }

    //inputs: s - represents the square root of the universe size
    //        d - represents the free zone size  / size of set you can insert with out false positives
    //        hashFunction - represents each number and it's mask bits
    public BFOLS(int s, int d, HashFunction hashFunction){
        this.m = s * (d + 1);
        this.k = d + 1;
        this.bloomFilter = new HashMap<>();
        this.hashFunction = hashFunction;
    }

    @Override
    // gets vector that says which bits need to be true in the bloomFilter,
    // then changes each relevant bit to be true in bloomFilter
    public void insert(int number) {
        Vector<Integer> bit_mask = hashFunction.get(number);
        for (int bit : bit_mask){
            bloomFilter.put(bit,true);
        }
    }

    @Override
    // gets vector that says which bits need to be true in the bloomFilter,
    // then if all relevant bits are true in bloomFilter then number is found in bloomFilter return true
    // otherwise number is not found, return false
    public boolean search(int number) {
        Vector<Integer> bit_mask = hashFunction.get(number);
        for (int bit : bit_mask){
            if(!bloomFilter.get(bit)){
                return false;
            }
        }
        return  true;
    }
}
