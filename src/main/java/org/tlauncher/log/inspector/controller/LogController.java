package org.tlauncher.log.inspector.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.tlauncher.log.inspector.model.ClientType;
import org.tlauncher.log.inspector.service.FileService;
import org.tlauncher.log.inspector.validator.VersionValidator;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

@Controller
public class LogController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FileService fileService;
    @Autowired
    private VersionValidator validator;


    @RequestMapping(value = "/save/log", method = RequestMethod.POST)
    ResponseEntity<String> saveLog(@RequestParam(value = "version") String version, @RequestParam(value = "clientType") ClientType clientType,
                                   @RequestBody byte[] payload) throws Exception
    {
        if (!validator.isValid(version)) {
            logger.warn("not proper version " + version);
        } else {
            String res = IOUtils.toString(new GZIPInputStream(new ByteArrayInputStream(payload)), StandardCharsets.UTF_8);
            fileService.processLog(version, clientType, res);
        }
        return ResponseEntity.ok("ok");
    }
}
