package utility;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

	//for debugging purpose
//	public static void main(String[] args) {
//        String zipFilePath = "C:/Users/xiaoyu/Desktop/G1.zip";
//        
//        String destDir = "C:/Users/xiaoyu/Desktop/output";
//        
//        unzipFile(zipFilePath, destDir);
//    }

	 public static void unzipFile(File zipFile, String destDir){

    	   File dir = new File(destDir);
           // create output directory if it doesn't exist
           if(!dir.exists()) dir.mkdirs();
           FileInputStream fis;
           //buffer for read and write data to file
           byte[] buffer = new byte[1024];
           try {
        	   
               fis = new FileInputStream(zipFile);
               ZipInputStream zis = new ZipInputStream(fis);
               ZipEntry ze = zis.getNextEntry();
               while(ze != null){
                   String fileName = ze.getName();
                   File newFile = new File(destDir + File.separator + fileName);
              
                   //create directories for sub directories in zip
                   new File(newFile.getParent()).mkdirs();
                   FileOutputStream fos = new FileOutputStream(newFile);
                   int len;
                   while ((len = zis.read(buffer)) > 0) {
                   fos.write(buffer, 0, len);
                   }
                   fos.close();
                   //close this ZipEntry
                   zis.closeEntry();
                   ze = zis.getNextEntry();
               }
               //close last ZipEntry
               zis.closeEntry();
               zis.close();
               fis.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
    }
}

