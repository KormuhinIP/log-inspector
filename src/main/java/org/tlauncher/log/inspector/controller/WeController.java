package org.tlauncher.log.inspector.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tlauncher.log.inspector.service.Cleaner;
import org.tlauncher.log.inspector.service.Scheduler;
import java.io.IOException;


@Controller
public class WeController {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    Cleaner cleaner;

    @RequestMapping(value = "/sort", method = RequestMethod.GET)
    ResponseEntity<String> saveLog() throws IOException {
       if (scheduler.getBlocking()) {
           cleaner.cleanerResult();
           scheduler.sort();
           return ResponseEntity.ok("Sorting complete");
       }else{ return ResponseEntity.ok("Sorting is performed");}
    }
}

