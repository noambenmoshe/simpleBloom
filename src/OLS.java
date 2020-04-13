import java.util.Vector;

public class OLS {
     int  number_of_OLS;/// d-1
     int  size;  // square of universe
     Vector<Vector<Integer>> OLS_vec;

     public OLS(int number_of_ols, int size_of_square, Vector<Vector<Integer>> vector_of_OLS) {
          number_of_OLS = number_of_ols;
          size = size_of_square;
          OLS_vec = vector_of_OLS;
     }

}
