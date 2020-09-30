import java.util.Map;
import java.util.Vector;
import java.lang.Math;

import static javafx.application.Platform.exit;

public class BFPOL implements BloomFilter {

        private int m; //filter length
        private int k; //number of hash functions n^(1/t)
        private Vector<Integer> bloomFilter;
        private POL_HF hashFunction;
        private int base;
        private int n; //universe size;

        //inputs: n - represents the size of the universe
        //        t - an integer such that the t-th root of n in a prime number
        //        d - set maximal size for FPFZ
        public BFPOL(int n, int t, int d){
            this.base = calcBase(n, t);
            this.m = ((t-1)*d + 1)*base;
            this.k = d + 1;
            this.bloomFilter = new Vector<>();
            this.hashFunction = new POL_HF(base, t, n);
            //TODO: make this error prettier
            if(((t-1)*d+1) >= base) {
                System.out.println("Error: base = "+ base +" smaller or equal to (t-1)d+1 = "+((t-1)*d+1));
                System.exit(-1);
            }
        }

        //Todo: maybe change round to ceil which will round value to the upper value
        public int calcBase(int n, int t){
            int base = (int) Math.round(Math.pow(n, 1.0/(double)t));
            if (Math.pow(base, t) == n)
                return base;
            else
                return -1;
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
