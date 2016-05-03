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

    private final ZipOutputStream zipStream;
    private String originalDirectory;

    public ZipMaker(String dest) throws FileNotFoundException {
        //create file from destination
        File file = new File(dest);
        //initialize zip output stream with file output stream to destination
        this.zipStream = new ZipOutputStream(new FileOutputStream(file));
    }

    public void zip(String fileLocation, String origin) {
        //initialize origin of the file
        this.originalDirectory = origin;
        //create file object from file location
        File file = new File(fileLocation);
        //check if file is directory or single file
        if (file.isDirectory()) {
            //if directory create array of files recursivly add them to zip
            File[] arrayOfFiles = file.listFiles();
            for (File files : arrayOfFiles) {
                zip(files.getAbsolutePath(), originalDirectory);
            }
        } else {
            //if single file just zip file
            ZipIt(file);
        }
    }

    @SuppressWarnings("ConvertToTryWithResources")
    private void ZipIt(File file) {
        ZipEntry zipEntry;
        try {
            String trueName = new File(originalDirectory).toURI().relativize(file.toURI()).getPath();
            //create zip entry with the file name
            zipEntry = new ZipEntry(trueName);
            //place zip entry into the zip output stream 
            zipStream.putNextEntry(zipEntry);
            //create file input stream to read file object
            FileInputStream fileInputStream = new FileInputStream(file);
            //create byte array to buffer bytes read
            byte[] buf = new byte[1024];
            int bytesRead;
            //write bytes to the entry in the zip stream
            while ((bytesRead = fileInputStream.read(buf)) > 0) {
                zipStream.write(buf, 0, bytesRead);
            }//close entry to prep zip stream for new entry
            zipStream.closeEntry();
            //close file input stream           
            fileInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ZipMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeZipStream() {
        try {//close zip output stream
            zipStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ZipMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
