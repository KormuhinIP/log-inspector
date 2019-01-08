package org.tlauncher.log.inspector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class Zipped {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public  void zipped(String folder) {
        File directoryToZip = new File(folder);
        List<File> fileList = new ArrayList<>();
        getAllFiles(directoryToZip, fileList);
        writeZipFile(directoryToZip, fileList);
    }

    public  void getAllFiles(File dir, List<File> fileList) {
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                fileList.add(file);
                if (file.isDirectory()) { getAllFiles(file, fileList); }
            }
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
    }

    public  void writeZipFile(File directoryToZip, List<File> fileList) {
        try {
            FileOutputStream fos = new FileOutputStream(directoryToZip.getName() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (File file : fileList) {
                if (!file.isDirectory()) { addToZip(directoryToZip, file, zos); }
            }
            zos.close();
            fos.close();
        } catch (Exception e) {
            logger.error("Exception: ", e);
        }
    }

    public  void addToZip(File directoryToZip, File file, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1);
        ZipEntry zipEntry = new ZipEntry(zipFilePath);
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {  zos.write(bytes, 0, length);}
        zos.closeEntry();
        fis.close();
    }
}
