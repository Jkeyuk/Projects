//reads a files and creates a duplicate txt file 
import java.util.*;
import java.lang.*;
import java.io.*;

class ReaderObject{
    String fileName;
    
    ReaderObject(String st){ //constructor
        this.fileName = st;
    }
    
    void printToConsole() throws IOException //method to print to console
    {
        String data;
        File fileToPrint = new File(fileName);
        Scanner scan = new Scanner(fileToPrint);
        
        while(scan.hasNextLine())  //loop while there is something to read
        {
            data = scan.nextLine(); //enter line into data
            System.out.println(data);//print data
        }
    }
}

public class ReadFile{

     public static void main(String []args) throws IOException
     {
        String fileName; //name of file
        
        Scanner userInput = new Scanner(System.in); //scanner to read user input
        System.out.print("Enter File Name: ");   //ask for file name
        fileName = userInput.nextLine().trim(); //read user input and trim spaces
     
        ReaderObject read = new ReaderObject(fileName);
        read.printToConsole();

     }
}
