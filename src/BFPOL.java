import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.lang.Math;

public class BFPOL implements BloomFilter {

        private int m; //filter length
        private int k; //number of hash functions n^(1/t)
        private Vector<Integer> bloomFilter;
        private POL_HF hashFunction;
        private int base;
        private int n; //universe size;

        public int getM() {
          return m;
        }
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

        public BFPOL(int n, int d){
            List<Integer> primeTill1000 = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
                    73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
                    181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283,
                    293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419,
                    421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547,
                    557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661,
                    673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811,
                    821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947,
                    953, 967, 971, 977, 983, 991, 997);
            BFPOL_vals bfpol_vals = new BFPOL_vals();
            int square_root_of_n = (int) Math.ceil(Math.pow(n, 1.0/(double)2));
            if(square_root_of_n > 1000){
                System.out.println("Error: n is too big. We support max of n = 1000^2");
            }

            int first_greater_num_of_sq_root_of_n = find_first_greater_num(primeTill1000,square_root_of_n);
            //Find t,p,n
            //Start looking at the first pirme number that is bigger than square root of universe size (in this scenario t =2)
            //t can not be smaller than 2 so we know this is the "worst" t
            bfpol_vals = calc_t_p_n(n,d, primeTill1000,0,first_greater_num_of_sq_root_of_n,bfpol_vals);
            if ((bfpol_vals.t == -1) ||  (bfpol_vals.n == -1) || (bfpol_vals.p == -1)){
                System.out.print("Error: did not find right values t = "+ bfpol_vals.t +" p = "+bfpol_vals.p + " n = "+bfpol_vals.n);
                System.out.println(" original n = "+n);
                this.m = -1;
            } else {
                System.out.println("BFPOL values t = " + bfpol_vals.t + " p = " + bfpol_vals.p + " n = " + bfpol_vals.n);
                this.base = bfpol_vals.p;
                if(((bfpol_vals.t-1)*d+1) >= base) {
                    System.out.println("Error: base = "+ base +" smaller or equal to (t-1)d+1 = "+((bfpol_vals.t-1)*d+1));
                }
                this.m = ((bfpol_vals.t-1)*d + 1)*base;
                this.k = d + 1;
                this.bloomFilter = new Vector<>();
                this.hashFunction = new POL_HF(base, bfpol_vals.t, bfpol_vals.n);
            }
        }

        //Find bloom filter values t, n (universe size), p (base n^(1/t))
        //So that:
        // p is a prime number
        //t is an int (whole number)
        //d is permanent, n can be bigger than original
        // (t-1)*d+1 < p
        BFPOL_vals calc_t_p_n(int n, int d, List<Integer> primeTill1000, int left,int right,BFPOL_vals bfpolVals) {
            if (left > right) {
                return bfpolVals;
            }
            int index = (right + left) / 2;
            int prime = primeTill1000.get(index);
            int t_to_check = (int) Math.ceil(Math.log10(n) / Math.log10(prime)); //t = upper value of log_p(n)

            if (prime - (((t_to_check - 1) * d) + 1) > 0) { //condition is held
                // we need to keep these values but look if there is a better t
                // move to lower part of prime numbers
                bfpolVals.n = (int) Math.pow(prime, t_to_check);
                bfpolVals.t = t_to_check;
                bfpolVals.p = prime;
                bfpolVals = calc_t_p_n(n, d, primeTill1000, left, index-1, bfpolVals);
            } else { // prime is too small go to high prime numbers
                bfpolVals = calc_t_p_n(n, d, primeTill1000, index+1, right, bfpolVals);
                return bfpolVals;
            }
            return bfpolVals; //should not reach here
        }

    //Todo: maybe change round to ceil which will round value to the upper value
        public int calcBase(int n, int t){
            int base = (int) Math.round(Math.pow(n, 1.0/(double)t));
            if (Math.pow(base, t) == n)
                return base;
            else
                return -1;
        }
        //find the first number in arr that is greater than target
        //return the index of that number
        int find_first_greater_num(List<Integer> arr, int target){
            int start = 0, end = arr.size() - 1;
            int ans = -1;
            while (start <= end) {
                int mid = (start + end) / 2;
                // Move to right side if target is
                // greater.
                if (arr.get(mid) <= target) {
                    start = mid + 1;
                }
                // Move left side.
                else {
                    ans = mid;
                    end = mid - 1;
                }
            }
            return ans;
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
