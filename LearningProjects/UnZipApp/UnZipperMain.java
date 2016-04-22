package unzipper;

public class UnZipperMain {

    public static void main(String[] args) {
        //create unzipper object with path to output folder
        UnZipper unzipper = new UnZipper("C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\practise");
        //call unzip method pointing to file to unzip
        unzipper.unzip("C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\test.zip");
    }

}
