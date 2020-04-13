
import java.util.ArrayList;
import java.util.Map;

public class BFOLS implements BloomFilter {
    private int m; //filter length
    private int k; //number of hash functions
    private Map<Integer, Boolean> bloomFilter;
    private HashFunction hashFunction;

    public BFOLS(int m, int k, Map<Integer, Boolean> bloom_Filter, HashFunction hash_Function ) {
        this.m = m;
        this.k = k;
        this.bloomFilter = bloom_Filter;
        this.hashFunction = hash_Function;
    }

    @Override
    public void insert(int number) {
        ArrayList<Integer> bit_mask = hashFunction.get(number);
        for (int bit : bit_mask){
            bloomFilter.put(bit,true);
        }
    }

    @Override
    public boolean search(int number) {
        ArrayList<Integer> bit_mask = hashFunction.get(number);
        for (int bit : bit_mask){
            if(!bloomFilter.get(bit)){
                return false;
            }
        }
        return  true;
    }
}
