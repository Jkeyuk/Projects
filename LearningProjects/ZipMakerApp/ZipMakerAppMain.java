package zipmakerapp;

public class ZipMakerAppMain {

    public static void main(String[] args) {
        //create zip maker object and point it to destination
        ZipMaker zipper = new ZipMaker(
                "C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\test.zip");
        //add directory or file to zip, must add directory twice to maintain...
        //proper directory structure 
        zipper.zip("C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\Testrectory",
                "C:\\Users\\Public\\JavascriptApps\\Temp\\Netbeans\\Testrectory");
        //must close stream 
        zipper.closeZipStream();

    }

}
