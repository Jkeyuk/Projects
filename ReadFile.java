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
        
        File myFile = new File(fileName); //setup file with user inputed name
        Scanner scan = new Scanner(myFile); //scanner to read file
        
        File outputFile = new File("output.txt"); //create outputf file
        PrintStream print = new PrintStream(outputFile);//connect stream to output file
        
        while(scan.hasNextLine())  //loop while there is something to read
        {
            data = scan.nextLine(); //enter line into data
            System.out.println(data);//print data
            print.println(data);//print data to output file
        }
        print.close();
     }
}
