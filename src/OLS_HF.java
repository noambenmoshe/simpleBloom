import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

public class OLS_HF implements HashFunction {
    // each Vector (mask-Vector) will contain which bits should be 1 for the key integer, according to our OLSs
    private Map<Integer,  Vector<Integer>> numbersBitMask;

    // the constructor initializes a map with universeSize elements (=keys), and a mask-Vector
    // input s it the square root of the universe size
    public OLS_HF(int s){
        numbersBitMask = new HashMap<>();
        for(int i=0; i<s*s; i++){
            numbersBitMask.put(i,new Vector<>());
            // adding first additional matrix (all rows with the same value). corresponds to first S bits.
            numbersBitMask.get(i).add(i/s);
            // adding second additional matrix (all columns with the same value). corresponds to second S bits.
            numbersBitMask.get(i).add((i%s)+s);
        }
    }

    // sets the Vector for each element in the universe according to the OLSs in ols_object
    public void set(OLS ols_object) {
        int ols_num =  2;
        for (Vector<Integer> ols: ols_object.OLS_vec){
            int i = 0;
            //updating the map with one OLS at a time
            for (int bit: ols){
                // multiplying by ols_num in order to correspond to the ols_num (3rd,4th,...) S bits
                numbersBitMask.get(i).add(bit+(ols_object.s *ols_num));
                i++;
            }
            ols_num++;
        }
    }

    // returns a mask-Vector for the given element
    public Vector<Integer> get(int element){
        return numbersBitMask.get(element);
    }
}
