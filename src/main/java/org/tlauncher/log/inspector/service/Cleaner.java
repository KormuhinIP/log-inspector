package org.tlauncher.log.inspector.service;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class Cleaner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void cleanerResult() {                       //удаление папки с предыдущими результатами
        Path path = Paths.get("result");
        if (Files.exists(path)) {
            try {
                FileUtils.deleteDirectory(path.toFile());
            } catch (Exception e) {
                logger.error("Exception: ", e);
            }
        }
    }

    public void cleanerFiles() {                        //удаление файлов старше 30 дней
        try {
            Files.walkFileTree(Paths.get("files"), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes) {
                    if (FileUtils.isFileOlder(path.toFile(), Date.from(LocalDate.now().minusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                        path.toFile().delete();
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) {
                    if (path.toFile().list().length == 0) {
                        path.toFile().delete();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Throwable e) {
            logger.error("Exception: ", e);
        }
    }
}