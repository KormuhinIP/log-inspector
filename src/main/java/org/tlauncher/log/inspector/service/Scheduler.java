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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class Scheduler implements ApplicationListener<ContextRefreshedEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Lock lock = new ReentrantLock();
    @Autowired
    private FileService fileService;
    @Autowired
    private MyFileVisitor myFileVisitor;
    @Autowired
    private Cleaner cleaner;
    @Autowired
    private Zipped zipped;

    @Scheduled(cron = "0 00 01 * * *")
    public void updateFolder() {
        LocalDate ld = LocalDate.now();
        DateTimeFormatter fOut = DateTimeFormatter.ofPattern("dd.MM.uuuu");

        fileService.setOldDate(ld.format(fOut));
    }
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        updateFolder();
        cleaner.cleanerResult();
        cleaner.cleanerFiles();
    }
     @Scheduled(cron = "0 10 01 * * *")
     public void sort() {
         lock.lock();
         try {
             myFileVisitor.reader();
            Files.walkFileTree(Paths.get("files"), myFileVisitor);
            zipped.zipped("result");
            logger.info("Sorting complete.");
            lock.unlock();
         } catch (IOException e) {
             logger.error("Exception: ", e);
         } finally {
             lock.unlock();
         }
     }
      public Boolean getBlocking() {return lock.tryLock();}
}

