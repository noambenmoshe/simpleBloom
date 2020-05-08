import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

public class POLS_HF {
    //What I want to get from the bloom filter
    int base; //n^(1/t)
    int t;
    int d; //number of FPFZ set

    // each Vector (mask-Vector) will contain which bits should be 1 for the key integer, according to our polynomial
    private Map<Integer,  Vector<Integer>> numbersBitMask;

    // the constructor initializes a map with universeSize elements (=keys), and a mask-Vector
    // n is the size of the universe
    public POLS_HF(int n){
        numbersBitMask = new HashMap<>();
//        for(int i=0; i< n; i++){
//            numbersBitMask.put(i,new Vector<>());
//        }
    }

    public void set(int y) {
        if(numbersBitMask.containsKey(y)) {
            return;
        }
        // need to calculate the bit mask
        numbersBitMask.put(y,new Vector<>());
        //build polynom
        Vector<Integer> coefficient = new Vector<>();
        for(int i=0; i < t-1; i++){
            int coe = y % base;
            coefficient.add(coe);
            y = (y - coe)/base;
        }

        for(int j=0; j < (t-1)*d; j++){
            numbersBitMask.get(y).add(j*base+getPolinomValue(j,coefficient));
        }
    }

    public int getPolinomValue(int j, Vector<Integer> coefficient){
        int result = 0;
        int i = 0;
        for(int coe : coefficient){
              result= (int) (result + coe*Math.pow(j,i));
        i++;
        }
        return result;
    }

}
