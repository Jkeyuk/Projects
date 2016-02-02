import java.util.*;
import java.lang.*;
import java.io.*;


class SortLowToHigh {
    public static void main(String[] args) throws java.lang.Exception {
        int temp1;
        int temp2;

        int[] values = {7,54,6,33,1,43,66,22};
        
        for (int j = 0; j < (values.length); j++) {
            for (int i = 0; i < (values.length - 1); i++) {
                if (values[i] > values[i + 1]) {
                    temp1 = values[i];
                    temp2 = values[i + 1];
                    values[i] = temp2;
                    values[i + 1] = temp1;
                }
            }
        }
        for (int x: values) {
            System.out.println(x);
        }

    }
}
