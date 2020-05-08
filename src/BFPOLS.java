import java.util.Map;
import java.util.Vector;
import java.lang.Math;

public class BFPOLS implements BloomFilter {

        private int m; //filter length
        private int k; //number of hash functions n^(1/t)
        private Vector<Integer> bloomFilter;
        private POLS_HF hashFunction;

        private int n; //universe size;

        // regular constructor
        public BFPOLS(int m, int k, Map<Integer, Boolean> bloom_Filter) {
            this.m = m;
            this.k = k;
            this.bloomFilter = new Vector<>();
            this.hashFunction = new POLS_HF(n);
        }

        //inputs: s - represents the square root of the universe size
        //        d - represents the free zone size  / size of set you can insert with out false positives
        //        hashFunction - represents each number and it's mask bits
        public BFPOLS(int n, int t, int d){
            this.m = ((t-1)*d + 1)*;
            this.k = d + 1;
            this.bloomFilter = new Vector<>();
            this.hashFunction = hashFunction;
        }

//        @Override
//        // gets vector that says which bits need to be true in the bloomFilter,
//        // then changes each relevant bit to be true in bloomFilter
//        public void insert(int number) {
//            Vector<Integer> bit_mask = hashFunction.get(number);
//            bloomFilter.addAll(bit_mask);
//        }
//
//        @Override
//        // gets vector that says which bits need to be true in the bloomFilter,
//        // then if all relevant bits are true in bloomFilter then number is found in bloomFilter return true
//        // otherwise number is not found, return false
//        public boolean search(int number) {
//            Vector<Integer> bit_mask = hashFunction.get(number);
//            for (int bit : bit_mask){
//                if(!bloomFilter.contains(bit)){
//                    return false;
//                }
//            }
//            return  true;
//        }

}
