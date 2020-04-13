public interface BloomFilter {
     int m = 0; //filter length
     int k = 0; //number of hash functions

    public void insert(int number);

    public boolean search(int number);

}
