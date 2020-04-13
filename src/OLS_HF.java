import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

public class OLS_HF implements HashFunction {
    // the Vector will contain which bit should be zero for the key integer, according to our OLSs
    private Map<Integer,  Vector<Integer>> numbersBitMask;

    // the constructor initializes a map with universeSize elements, for each key they Vector will hold the bits that
    // need to be 1 to represent it
    public OLS_HF(int universeSize){
        numbersBitMask = new HashMap<>();
        for(int i=0; i<universeSize; i++){
            numbersBitMask.put(i,new Vector<>());
            // adding first additional matrix (all rows with the same value)

        }
    }

    // sets the Vector for each element in the universe according to the OLSs in ols_object
    public void set(OLS ols_object) {
        for (Vector<Integer> ols: ols_object.OLS_vec){
            //updating the map with one OLS at a time
            for (int bit: ols){
                numbersBitMask.get(bit).add(ols.get(bit));
            }
        }
    }

    // returns the Vector representing the equivalent
    public Vector<Integer> get(int element){
        return numbersBitMask.get(element);
    }
}
