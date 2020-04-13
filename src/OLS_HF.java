import java.util.ArrayList;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;

public class OLS_HF implements HashFunction {
    // the ArrayList will contain which bit should be zero for the key integer, according to our OLSs
    private Map<Integer,  ArrayList<Integer>> numbersBitMask;

    public void set(OLS ols_object) {
        for (Vector<Integer> ols: ols_object.OLS_vec){
            for (int bit: ols){ //checking one OLS
                numbersBitMask.put(bit, ols.get(bit));
            }
        }
    }
    public ArrayList<Integer> get(int number){
        return numbersBitMask.get(number);
    }
}
