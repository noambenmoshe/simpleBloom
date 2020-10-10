import java.lang.Math;

public class BloomPrimitive implements BloomFilter {
    private int m; //filter length
    private int k; //number of hash functions
    private boolean[] bloomArray;

    public int getM() {
        return m;
    }
    public BloomPrimitive(int length, int numOfFunc){
        m = length;
        k = numOfFunc;
        bloomArray = new boolean[m];
        //initialize bloomArray
        for( int i=0; i<m; i++){
            bloomArray[i] = false;
        }
    }

    /*int hash1(int number){
        return number%m;
    }
    int hash2(int number){
        return (number*number)%m;
    }
    int hash3(int number){
        return (number*number*number)%m;
    }*/

    public void insert(int number){
        for (int i=1; i<=k; i++){
            bloomArray[(int)Math.pow(number,i)%m] = true;
        }
        /*bloomArray[hash1(number)] = true;
        bloomArray[hash2(number)] = true;
        bloomArray[hash3(number)] = true;*/
    }

    public boolean search(int number){
        for (int i=1; i<=k; i++){
            if (!bloomArray[(int)Math.pow(number,i)%m])
                return false;
        }
        return true;
        /*if (!bloomArray[hash1(number)]) return false;
        if (!bloomArray[hash2(number)]) return false;
        return bloomArray[hash3(number)];*/
    }
}

