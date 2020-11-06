public interface BloomFilter {
     int m = 0; //filter length
     int k = 0; //number of hash functions

     void insert(int number);
     boolean search(int number);
     int getSize();
     int getM();

}
