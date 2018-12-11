package org.tlauncher.log.inspector.service;

import org.omg.CORBA.DATA_CONVERSION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.tlauncher.log.inspector.model.ClientType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class  Scheduler implements ApplicationListener<ContextRefreshedEvent> {
   @Autowired
    FileService fileService;
   @Scheduled(cron = "0 50 17 * * *")
   public void updateFolder(){
    fileService.setCount(0);
    LocalDate ld = LocalDate.now();
    DateTimeFormatter fOut = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    String date = ld.format(fOut);
    fileService.setOlddate(date);
}
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

    }
}

