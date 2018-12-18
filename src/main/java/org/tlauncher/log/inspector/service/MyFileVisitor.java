package org.tlauncher.log.inspector.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;


@Service
public class MyFileVisitor extends SimpleFileVisitor<Path> {

    volatile private String partOfContent;

    public void setPartOfContent(String partOfContent) {
        this.partOfContent = partOfContent;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String content = new String(Files.readAllBytes(file));
        if (!partOfContent.isEmpty() && partOfContent != null && content.contains(partOfContent)) {
            File filePath = new File("result/" + file.getName(1) + "/" + file.getName(2) + "/" + partOfContent + "/" + file.getName(3));
            filePath.mkdirs();
            Path p = Paths.get(filePath + "/" + file.getFileName());
            Files.write(p, Files.readAllBytes(file));
        }
        return FileVisitResult.CONTINUE;
    }
}