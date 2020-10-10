import java.util.List;
import java.util.Vector;

public class MOLS {
     int  number_of_OLS;                // d-1 as for d+1 needed for a set of size d minus additional matrices
     int s;                             // square of universe
     Vector<Vector<Integer>> OLS_vector;   // each OLS is represented in a vector of size s^2

     Vector<Vector<Integer>> ols_vector_s_3;
     Vector<Vector<Integer>> ols_vector_s_4;
     Vector<Vector<Integer>> ols_vector_s_5;
     Vector<Vector<Integer>> ols_vector_s_7;
     Vector<Vector<Integer>> ols_vector_s_8;
     Vector<Vector<Integer>> ols_vector_s_9;
     Vector<Vector<Integer>> ols_vector_s_11;
     Vector<Vector<Integer>> ols_vector_s_19;

     public MOLS(int number_of_ols, int size_of_square, Vector<Vector<Integer>> vector_of_OLS) {
          number_of_OLS = number_of_ols;
          s = size_of_square;
          OLS_vector = vector_of_OLS;
     }

     //Here we need to initialize an ols_vector_s_* foreach different size of ols
     //We need to have only one intialization of this object.
     //All the OLSs need to be kept here
     public MOLS(){
          // all Vector<Vector<Integer>>
          this.ols_vector_s_3 = new Vector<>();
          this.ols_vector_s_4 = new Vector<>();
          this.ols_vector_s_5 = new Vector<>();
          this.ols_vector_s_7 = new Vector<>();
          this.ols_vector_s_8 = new Vector<>();
          this.ols_vector_s_9 = new Vector<>();
          this.ols_vector_s_11 = new Vector<>();
          this.ols_vector_s_19 = new Vector<>();
     }

     //Each boomfilter has an OLS size (s) it is working with. To choose the right kind of OLS we use this function
     //Depending on s we return the right vector
     Vector<Vector<Integer>> choose_OLS_vec (int s){
          if(s <= 3)
                    return ols_vector_s_3;
          else if(s == 4)
                    return ols_vector_s_4;
          else if(s == 5)
               return ols_vector_s_5;
          else if(s <= 7)
               return ols_vector_s_7;
          else if(s == 8)
               return ols_vector_s_8;
          else if(s == 9)
               return ols_vector_s_9;
          else if(s <= 11)
               return ols_vector_s_11;
          else if(s <= 19)
               return ols_vector_s_19;
          else return null;
     }
     void insert_ols(int s, List<Integer> num_list){
          Vector<Integer> ols = new Vector<>(num_list);
          Vector<Vector<Integer>> OLS_vec = choose_OLS_vec(s);
          OLS_vec.add(ols);
     }
}
