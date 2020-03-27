
public class Bloom {
    private int m; //filter length
    private int k; //number of hash functions
    private boolean[] bloomArray;

    public Bloom(int length, int numOfFunc){
        m = length;
        k= numOfFunc;
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

    void Add(int length){

    }

    public static void main(String[] args) {}

}

