package csvfilesorter;

public class CSVFileSorterMain {

    public static void main(String[] args) {
        CSVFileSorter sorter = new CSVFileSorter("test.csv");
        
        sorter.sortTextualData();
    }
}
