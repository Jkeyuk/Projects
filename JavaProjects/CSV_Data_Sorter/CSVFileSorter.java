package csvfilesorter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFileSorter {

    private final File file;
    private final ArrayList<String> data = new ArrayList<>();

    public CSVFileSorter(String fileName) {
        this.file = new File(fileName);
        getDataFromFile();
    }

    private void getDataFromFile() {//create buffered reader
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String input;//add each line to array
            while ((input = reader.readLine()) != null) {
                data.add(input);
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVFileSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sortNumericalData() {
        //arrays to hold stuff
        ArrayList<Integer> parsedDataList = new ArrayList<>();
        ArrayList<String> finalFormatedList = new ArrayList<>();
        //build string from data in array
        String dataFromFile = buildStringFromArray(data);
        //parse string by commas
        String[] parsedData = dataFromFile.split(",");
        //convert string to ints add to array
        for (String string : parsedData) {
            int x = Integer.parseInt(string);
            parsedDataList.add(x);
        }//sort int data using natural order
        parsedDataList.sort(null);
        //convert int to strings add to array
        for (Integer y : parsedDataList) {
            finalFormatedList.add(y.toString());
        }//call write to file method
        writeDataToFile(finalFormatedList);
    }

    public void sortTextualData() {
        //array to hold data after parse
        ArrayList<String> parsedDataList = new ArrayList<>();
        //build string from data in array
        String dataFromFile = buildStringFromArray(data);
        //parse string by commas
        String[] parsedData = dataFromFile.split(",");
        //add parsed data to array
        for (String string : parsedData) {
            parsedDataList.add(string);
        }
        //sort array and write data to file
        parsedDataList.sort(null);
        writeDataToFile(parsedDataList);
    }

    private void writeDataToFile(ArrayList<String> dataa) {
        //get appropriate row size
        int sizeOfRow = data.get(0).split(",").length;
        //make buffered writer, write data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < dataa.size(); i++) {
                if (i % sizeOfRow == 0 && i != 0) {//<--adjust size of row
                    writer.newLine();
                    writer.write(dataa.get(i) + ",");
                } else {
                    writer.write(dataa.get(i) + ",");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(CSVFileSorter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String buildStringFromArray(ArrayList<String> x) {
        String stuffToReturn = "";
        for (String string : x) {//add data to string
            stuffToReturn += string + ",";
        }
        return stuffToReturn;
    }
}
