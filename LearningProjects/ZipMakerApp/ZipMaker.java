package zipmakerapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipMaker {

    //file to hold path to zip file
    private final File file;
    //holds zip stream object
    private ZipOutputStream zipStream;
    //hold original working directory
    private String originalDirectory;

    public ZipMaker(String dest) {
        this.file = new File(dest);
        openStream();
    }

    //checks if file or directory then recursively zips 
    public void zip(String fileLocation, String origin) {
        //initialize origin of the file
        this.originalDirectory = origin;
        //create file object from file location
        File filez = new File(fileLocation);
        //check if file is directory or single file
        if (filez.isDirectory()) {
            //if directory create array of files recursivly add them to zip
            File[] arrayOfFiles = filez.listFiles();
            for (File files : arrayOfFiles) {
                zip(files.getAbsolutePath(), originalDirectory);
            }
        } else {//if single file just zip file         
            ZipIt(filez);
        }
    }

    //zip files
    @SuppressWarnings("ConvertToTryWithResources")
    private void ZipIt(File fileToZip) {
        try {//make actual name for file with proper path name in place
            String trueName = new File(originalDirectory).toURI().relativize(fileToZip.toURI()).getPath();
            //create zip entry with true name
            ZipEntry zipEntry = new ZipEntry(trueName);
            //place zip entry into the zip output stream 
            zipStream.putNextEntry(zipEntry);
            //create file input stream to file given as parameter
            FileInputStream fileInputStream = new FileInputStream(fileToZip);
            //buffer to hold bytes to read
            byte[] buf = new byte[1024];
            int bytesRead;
            //write bytes from input file stream to the entry in the zip stream
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                zipStream.write(buf, 0, bytesRead);
            }//close zip entry to make room for new one
            zipStream.closeEntry();
            //close file input stream 
            fileInputStream.close();
        } catch (IOException ex) {//if error post error message and keep looping
            Logger.getLogger(ZipMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //opens zip output stream 
    private void openStream() {
        try {//initialize zip output stream with file output stream to destination
            this.zipStream = new ZipOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZipMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //close zip output stream
    public void closeZipStream() {
        try {//close zip output stream
            zipStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ZipMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
