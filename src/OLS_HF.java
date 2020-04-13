import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

public class OLS_HF implements HashFunction {
    // the Vector will contain which bit should be zero for the key integer, according to our OLSs
    private Map<Integer,  Vector<Integer>> numbersBitMask;
    // the constructor initializes a map with universeSize elements, for each key they Vector will hold the bits that
    // need to be 1 to represent it
    // input s it the square root of the universe size
    public OLS_HF(int s){
        numbersBitMask = new HashMap<>();
        for(int i=0; i<s*s; i++){
            numbersBitMask.put(i,new Vector<>());
            // adding first additional matrix (all rows with the same value)
            numbersBitMask.get(i).add(i/s);
            // adding second additional matrix (all columns with the same value)
            // this will correspond to the next set of values in the H matrix (second rectangle)
            numbersBitMask.get(i).add((i%s)+s);
        }
    }

    // sets the Vector for each element in the universe according to the OLSs in ols_object
    public void set(OLS ols_object) {
        int ols_num =  2;
        for (Vector<Integer> ols: ols_object.OLS_vec){
            //updating the map with one OLS at a time
            int i = 0;
            for (int bit: ols){
                numbersBitMask.get(i).add(bit+(ols_object.s *ols_num));
                i++;

            }
            ols_num++;
        }
    }

    // returns the Vector representing the equivalent
    public Vector<Integer> get(int element){
        return numbersBitMask.get(element);
    }
}
