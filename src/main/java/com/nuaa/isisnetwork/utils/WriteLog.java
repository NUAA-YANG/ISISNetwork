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

    //写入日志
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

    //写入日志
    public void log(String info) throws IOException {
        File file = new File("src/main/java/com/nuaa/isisnetwork/generateFile/Create.log");
        //追加内容
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
        writer.write(info+"\n");
        writer.flush();
        writer.close();
    }


    //写入创建或删除过程脚本
    public void createOrDeleteBash(List<String> info,String type) throws IOException {
        File file = null;
        //写入创建过程
        if ("create".equals(type)){
            file = new File("src/main/java/com/nuaa/isisnetwork/generateFile/CreateBash.sh");
        }else if ("delete".equals(type)){
            //写入删除过程
            file = new File("src/main/java/com/nuaa/isisnetwork/generateFile/DeleteBash.sh");
        }
        //追加内容
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
        //writer.write("#!/bin/bash");
        for (String line:info){
            writer.write(line+"\n");
        }
        writer.flush();
        writer.close();
    }
}
