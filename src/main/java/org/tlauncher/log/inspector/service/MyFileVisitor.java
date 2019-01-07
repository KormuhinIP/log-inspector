package org.tlauncher.log.inspector.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class MyFileVisitor extends SimpleFileVisitor<Path> {

    public ArrayList<String> list = new ArrayList<>();

    public void reader() throws IOException {
        list.clear();
        BufferedReader reader = new BufferedReader(new FileReader("error.strings.txt"));
        while (reader.ready()) {
            list.add(reader.readLine());
                   }
        reader.close();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String content = new String(Files.readAllBytes(file));
        boolean presence=false;
        for (String partOfContent : list) {
            if (content.contains(partOfContent) && !FileUtils.isFileOlder(file.toFile(), Date.from(LocalDate.now().minusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant()))) {
                File filePath = new File("result/" + file.getName(1) + "/" + file.getName(2) + "/" + partOfContent + "/" + file.getName(3));
                filePath.mkdirs();
                Path p = Paths.get(filePath + "/" + file.getFileName());
                Files.copy(file, p, REPLACE_EXISTING);
                presence=true;
            }
        }
                  if(!presence)  {
                      File filePath = new File("result/" + file.getName(1) + "/" + file.getName(2) + "/not_found/" + file.getName(3));
                filePath.mkdirs();
                Path p = Paths.get(filePath + "/" + file.getFileName());
                Files.copy(file, p, REPLACE_EXISTING);
            }
        return FileVisitResult.CONTINUE;
    }
}