package org.tlauncher.log.inspector.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tlauncher.log.inspector.model.ClientType;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipInputStream;

@Service
public class FileService  {
    AtomicInteger count = new AtomicInteger(1);
    String olddate;

    public void processLog(String io, ClientType version, MultipartFile file) throws Exception {   //Обрабока входных данных и сохранение
            ZipInputStream zip = new ZipInputStream(file.getInputStream());
            File filePath = new File(version+"/"+io+"/"+olddate);
            filePath.mkdirs();
        File file2 = new File(filePath + "/" + count.getAndIncrement() + "_" + zip.getNextEntry().getName());
            FileOutputStream bos = new FileOutputStream(file2);
            byte[] buffer;
            buffer=IOUtils.readFully(zip,-1, true);
            bos.write(buffer, 0, buffer.length);
           bos.close();
           zip.close();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public void setOlddate(String olddate) {
        this.olddate = olddate;
    }
}





