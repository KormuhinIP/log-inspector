package org.tlauncher.log.inspector.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tlauncher.log.inspector.model.ClientType;
import sun.misc.IOUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

//программы/release/1.91/22.08.2018/id_file.log
//id обнуляется каждые сутки
//release = clientType
@Service
public class FileService  {
    AtomicInteger count=new AtomicInteger(0);
    LocalDate ld = LocalDate.now();
    DateTimeFormatter fOut = DateTimeFormatter.ofPattern("dd.MM.uuuu");
    String olddate=ld.format(fOut);

    public synchronized void processLog(String io, ClientType version, MultipartFile file) throws Exception{   //Обрабока входных данных и сохранение
            count.getAndIncrement();

            ZipInputStream zip = new ZipInputStream(file.getInputStream());
            File filePath = new File(version+"/"+io+"/"+olddate);
            filePath.mkdirs();
            File file2 = new File(filePath +"/"+String.valueOf(count)+"_"+ zip.getNextEntry().getName());
            FileOutputStream bos = new FileOutputStream(file2);
            byte[] buffer;
            buffer=IOUtils.readFully(zip,-1, true);
            bos.write(buffer, 0, buffer.length);

           bos.close();
           zip.close();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public synchronized void setOlddate(String olddate) {
        this.olddate = olddate;
    }
}





