package org.tlauncher.log.inspector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class Scheduler implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private FileService fileService;

    @Scheduled(cron = "0 50 17 * * *")
    public void updateFolder() {
        LocalDate ld = LocalDate.now();
        DateTimeFormatter fOut = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        String date = ld.format(fOut);
        fileService.setCount(1);
        fileService.setOlddate(date);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        updateFolder();
    }
}
