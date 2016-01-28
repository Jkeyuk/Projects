import java.util.*;
import java.lang.*;
import java.io.*;


public class ReadFile{

     public static void main(String []args) throws IOException
     {
        String data; //data from file
        String fileName; //name of file
        
        Scanner userInput = new Scanner(System.in); //scanner to read user input
        System.out.print("Enter File Name: ");   //ask for file name
        fileName = userInput.nextLine().trim(); //read user input and trim spaces
        
        File myFile = new File(fileName); //create file with user inputed name
        Scanner scan = new Scanner(myFile); //scanner to read file
        
        while(scan.hasNextLine())  //loop while there is something to read
        {
            data = scan.nextLine(); //enter line into data
            System.out.println(data);//print data
        }
        
     }
}
