//reads a files and creates a duplicate txt file 
import java.util.*;
import java.lang.*;
import java.io.*;

class ReaderObject{
    String fileName;
    
    ReaderObject(String st){
        this.fileName = st;
    }
    
    void printToConsole() throws IOException
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
    void outPutToTextFile() throws IOException
    {
        String data;
        File fileToRead = new File(fileName);
        Scanner scan = new Scanner(fileToRead);
        File output = new File("output.txt");
        PrintStream print = new PrintStream(output); 
        
         while(scan.hasNextLine())  //loop while there is something to read
        {
            data = scan.nextLine(); //enter line into data
            print.println(data);//print data
        }
        print.close();
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
        read.outPutToTextFile();
     }
}
