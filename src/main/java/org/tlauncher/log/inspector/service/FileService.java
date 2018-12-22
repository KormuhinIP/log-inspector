package org.tlauncher.log.inspector.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.tlauncher.log.inspector.model.ClientType;
import sun.misc.IOUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipInputStream;

@Service
public class FileService {
    private AtomicInteger count = new AtomicInteger(1);
    volatile private String oldDate;

    public void processLog(String io, ClientType version, MultipartFile file) throws Exception {
        ZipInputStream zip = new ZipInputStream(file.getInputStream());
        File filePath = new File("files/" + version + "/" + io + "/" + oldDate);
        filePath.mkdirs();
        zip.getNextEntry();
        Path p = Paths.get(filePath + "/" + count.getAndIncrement() + ".log");
        Files.write(p, IOUtils.readFully(zip, -1, true));
        zip.close();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

}





