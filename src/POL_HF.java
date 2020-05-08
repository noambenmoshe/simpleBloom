import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;

public class POL_HF {
    //What I want to get from the bloom filter
    int base; //n^(1/t)
    int t;
    int d; //number of FPFZ set

    // each Vector (mask-Vector) will contain which bits should be 1 for the key integer, according to our polynomial
    private Map<Integer,  Vector<Integer>> numbersBitMask;

    // the constructor initializes a map with universeSize elements (=keys), and a mask-Vector
    // n is the size of the universe
    public POL_HF(int base, int t, int d) {
        numbersBitMask = new HashMap<>();
        this.base = base;
        this.t = t;
        this.d = d;
    }

    public void createBitMask(int y) {
        // need to calculate the bit mask
        numbersBitMask.put(y,new Vector<>());
        //build polynom
        Vector<Integer> coefficient = new Vector<>();
        int y_temp = y;
        for(int i=0; i < t-1; i++){
            int coe = y_temp % base;
            coefficient.add(coe);
            y_temp = (y_temp - coe)/base;
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
        return result % base;
    }

    // returns a mask-Vector for the given element
    public Vector<Integer> get(int element){
          if(!numbersBitMask.containsKey(element)) {
              createBitMask(element);
          }
          return numbersBitMask.get(element);
    }
}
