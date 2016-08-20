package SandBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
//takes a file compresses it and adds it to a zip archive
public class ZipFileMaker {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args)
            throws FileNotFoundException, IOException {
        
        File file = new File("test.txt");

        FileOutputStream outStream = new FileOutputStream("test.zip");

        ZipOutputStream zipStream = new ZipOutputStream(outStream);

        ZipEntry zipEntry = new ZipEntry(file.getName());
        
        
        zipStream.putNextEntry(zipEntry);
        
        FileInputStream fileInputStream = new FileInputStream(file);
        
        byte[] buf = new byte[1024];
        int bytesRead;
        
        while ((bytesRead = fileInputStream.read(buf)) > 0) {            
            zipStream.write(buf, 0, bytesRead);
        }
        
        zipStream.closeEntry();
        
        zipStream.close();
        fileInputStream.close();
    }

}
