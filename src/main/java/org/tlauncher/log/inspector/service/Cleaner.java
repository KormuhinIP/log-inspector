package org.tlauncher.log.inspector.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

@Service
public class Cleaner {

    public void cleanerResult() {                       //удаление папки с предыдущими результатами
        Path path = Paths.get("result");
        if (Files.exists(path)) {
            try {
                FileUtils.deleteDirectory(path.toFile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cleanerFiles() {   //удаление файлов старше 30 дней
        try {
            Files.walkFileTree(Paths.get("files"), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) {
                    long diff = new Date().getTime() - path.toFile().lastModified();
                    if (String.valueOf(path.getFileName()).length() == 10 && diff > (long) 30 * 24 * 60 * 60 * 1000) {     //условие проверки на длину имени добавил т.к. дата изменения папок files, Release и т.п. может быть больше 30 дней
                        try {
                            FileUtils.deleteDirectory(path.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
