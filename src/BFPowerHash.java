import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;

public class BFPowerHash implements BloomFilter{
    private int m; //filter length
    private int k; //number of hash functions
    private ArrayList<Boolean> bloomArray;

    public int getM() {
        return m;
    }
    public BFPowerHash(int length, int numOfFunc){
        m = length;
        k = numOfFunc;
        bloomArray = new ArrayList<>();
        //initialize bloomArray
        Collections.fill(bloomArray, Boolean.FALSE);
    }

    public void insert(int number){
        for (int i=1; i<=k; i++){
            bloomArray.add((int)Math.pow(number,i)%m,Boolean.TRUE);
        }
    }

    public boolean search(int number){
        for (int i=1; i<=k; i++){
            if (!bloomArray.get((int)Math.pow(number,i)%m))
                return false;
        }
        return true;
    }
}
