import java.util.Vector;

public class OLS {
     int  number_of_OLS;                // d-1 as for d+1 needed for a set of size d minus additional matrices
     int s;                             // square of universe
     Vector<Vector<Integer>> OLS_vec;   // each OLS is represented in a vector of size s^2

     public OLS(int number_of_ols, int size_of_square, Vector<Vector<Integer>> vector_of_OLS) {
          number_of_OLS = number_of_ols;
          s = size_of_square;
          OLS_vec = vector_of_OLS;
     }

}
