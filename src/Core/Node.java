package Core;




import java.util.ArrayList;
import cclo.*;

public class Node implements Share {
    
    public int[] peaks = new int[PATT_SIZE];

    public Node(int[] peaks_) {
       for (int i = 0; i<peaks_.length; i++) {
           peaks[i] = peaks_[i];
       }
    }       
}
