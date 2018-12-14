package org.tlauncher.log.inspector.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tlauncher.log.inspector.model.ClientType;
import org.tlauncher.log.inspector.service.FileService;

@Controller
public class LogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
@Autowired
private FileService fileService;

    @RequestMapping(value = "/save/log", method = RequestMethod.POST, headers = "content-type=multipart/*")
    ResponseEntity<String> saveLog(@RequestParam("version") String version, @RequestParam("clientType") ClientType clientType,
                                   @RequestParam("file") MultipartFile file) throws Exception
    {
        fileService.processLog(version, clientType, file);
        return ResponseEntity.ok("ok\n");
    }
}
