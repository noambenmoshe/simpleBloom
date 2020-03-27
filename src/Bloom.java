
public class Bloom {
    private int m; //filter length
    private int k; //number of hash functions
    private boolean[] bloomArray;

    public Bloom(int length, int numOfFunc){
        m = length;
        k= numOfFunc;
        boolean[] bloomArray = new boolean[m];
        for( int i=0; i<m; i++){
            bloomArray[i] = false;
        }
    }

    int hash1(int number){
        return number%10;
    }
    int hash2(int number){
        return (number*number)%10;
    }
    int hash3(int number){
        return (number*number*number)%10;
    }

    void Add(int number){
        bloomArray[hash1(number)] = true;
        bloomArray[hash2(number)] = true;
        bloomArray[hash3(number)] = true;
    }

    boolean checkIfExists(int number){
        boolean flag = false;
        if (!bloomArray[hash1(number)]) return false;
        if (!bloomArray[hash2(number)]) return false;
        if (!bloomArray[hash3(number)]) return false;
        return true;
    }

    public static void main(String[] args) {}

}

