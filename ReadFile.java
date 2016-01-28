import java.util.*;
import java.lang.*;
import java.io.*;


public class ReadFile{

     public static void main(String []args) throws IOException
     {
        String data;
        File myFile = new File("ReadFile.java");
        Scanner scan = new Scanner(myFile);
        
        while(scan.hasNextLine())
        {
            data = scan.nextLine();
            System.out.println(data);
        }
        
     }
}
