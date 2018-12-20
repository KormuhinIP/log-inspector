package org.tlauncher.log.inspector.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;


@Service
public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private ArrayList<String> list = new ArrayList<String>();

    public void reader() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("error.strings.txt"));
        while (reader.ready()) {
            list.add(reader.readLine());
        }
        reader.close();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        long diff = new Date().getTime() - file.toFile().lastModified();
        String content = new String(Files.readAllBytes(file));

        for (String partOfContent : list) {
            if (content.contains(partOfContent) && diff < (long) 10 * 24 * 60 * 60 * 100) {
                File filePath = new File("result/" + file.getName(1) + "/" + file.getName(2) + "/" + partOfContent + "/" + file.getName(3));
                filePath.mkdirs();
                Path p = Paths.get(filePath + "/" + file.getFileName());
                Files.copy(file, p);
            }
        }
        return FileVisitResult.CONTINUE;
    }
}