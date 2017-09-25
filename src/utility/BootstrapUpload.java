package utility;

import java.io.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 * Clear file directory
 */
public class BootstrapUpload {
    /**
     * This method cleans all cached files stored in uploads folder
     */
    public static void cleanAll(String dir) {
        File folder = new File(dir);
        try {
            FileUtils.cleanDirectory(folder);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * This method cleans all cached files stored in updates folder
     */
    public static void cleanAll2() {
        String dir = System.getenv("OPENSHIFT_DATA_DIR") + "updates";
        File folder = new File(dir);
        try {

            FileUtils.cleanDirectory(folder);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
