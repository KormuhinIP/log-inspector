package org.tlauncher.log.inspector.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class Scheduler implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FileService fileService;
    @Autowired
    private MyFileVisitor myFileVisitor;
    @Autowired
    private Cleaner cleaner;

    @Scheduled(cron = "0 00 22 * * *")
    public void updateFolder() {
        LocalDate ld = LocalDate.now();
        DateTimeFormatter fOut = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        fileService.setCount(1);
        fileService.setOldDate(ld.format(fOut));
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        updateFolder();
        cleaner.cleanerResult();
        cleaner.cleanerFiles();
    }

    @Scheduled(cron = "0 10 22 * * *")
    public void sort() throws IOException {
        myFileVisitor.reader();
        Files.walkFileTree(Paths.get("files"), myFileVisitor);
        logger.info("Sorting complete.");
    }
}