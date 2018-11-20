package org.tlauncher.log.inspector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tlauncher.log.inspector.model.ClientType;

@Controller
public class LogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/save/log/test", method = RequestMethod.GET)
    ResponseEntity<String> test() {
        System.out.println("test");
        logger.info("received test");
        return ResponseEntity.ok("ok");
    }

    @RequestMapping(value = "/save/log", method = RequestMethod.POST, headers = "content-type=multipart/*")
    ResponseEntity<String> saveLog(@RequestParam("version") String version, @RequestParam("clientType") ClientType clientType,
                                   @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok("ok\n");
    }
}
