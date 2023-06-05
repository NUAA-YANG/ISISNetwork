package com.nuaa.isisnetwork.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-05 16:38
 * @Java-version jdk1.8
 */
//创建日志文件
@Service
@Component
public class WriteLog {

    public void log(List<String> info) throws IOException {
        File file = new File("src/main/java/com/nuaa/isisnetwork/Create.log");
        //追加内容
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
        for (String line:info){
            writer.write(line+"\n");
        }
        writer.flush();
        writer.close();
    }

    public void log(String info) throws IOException {
        File file = new File("src/main/java/com/nuaa/isisnetwork/Create.log");
        //追加内容
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
        writer.write(info+"\n");
        writer.flush();
        writer.close();
    }

    public void logBash(List<String> info) throws IOException {
        File file = new File("src/main/java/com/nuaa/isisnetwork/CreateBash.sh");
        //追加内容
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
        for (String line:info){
            writer.write(line+"\n");
        }
        writer.flush();
        writer.close();
    }
}
