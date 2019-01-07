package org.tlauncher.log.inspector.service;

import org.springframework.stereotype.Service;
import org.tlauncher.log.inspector.model.ClientType;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class FileService {
    private AtomicInteger count = new AtomicInteger(1);
    volatile private String oldDate;

    public void processLog(String io, ClientType version, String text) throws Exception {
        File filePath = new File("files/" + version + "/" + io + "/" + oldDate);
        filePath.mkdirs();
        Path p = Paths.get(filePath + "/" + count.getAndIncrement() + ".log");
        Files.write(p, text.getBytes(StandardCharsets.UTF_8));
    }
    void setCount(int count) {
        this.count.set(count);
    }
    void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

}





