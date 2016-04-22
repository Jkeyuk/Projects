package unzipper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipper {

    private final String OUT_PUT_FOLDER;

    public UnZipper(String outputFolder) {
        this.OUT_PUT_FOLDER = outputFolder;
    }

    @SuppressWarnings("ConvertToTryWithResources")
    public void unzip(String zipFilePath) {
        //make output folder
        makeOutputFolder();
        try {//create zip input stream
            ZipInputStream zipInputStream
                    = new ZipInputStream(new FileInputStream(zipFilePath));
            //get zip entry from input stream
            ZipEntry zEntery = zipInputStream.getNextEntry();
            //while zip input stream has entries do..
            while (zEntery != null) {
                //make new file with path to output folder and zentry name
                File file = new File(
                        OUT_PUT_FOLDER + File.separator + zEntery.getName());
                //create parent folder
                File newFolder = new File(file.getParent());
                newFolder.mkdirs();
                //create file output stream to write to file
                FileOutputStream fileOutput = new FileOutputStream(file);
                //create byte array to buffer bytes read
                byte[] buffer = new byte[1024];
                //read zip input stream and write to file
                int x;
                while ((x = zipInputStream.read(buffer)) > 0) {
                    fileOutput.write(buffer, 0, x);
                }//close file output and get next entry in zip stream
                fileOutput.close();
                zEntery = zipInputStream.getNextEntry();
            }
            //close zip entry and stream
            zipInputStream.closeEntry();
            zipInputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UnZipper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UnZipper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void makeOutputFolder() {
        File file = new File(OUT_PUT_FOLDER);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
