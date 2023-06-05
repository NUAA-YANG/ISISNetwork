package com.nuaa.isisnetwork;

import com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate.FtlXml;
import com.nuaa.isisnetwork.nfv.CreateBridge;
import com.nuaa.isisnetwork.nfv.CreateLxd;
import com.nuaa.isisnetwork.service.MangeDaoService;
import com.nuaa.isisnetwork.utils.WriteLog;
import freemarker.template.TemplateException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
public class IsisNetworkApplication {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(IsisNetworkApplication.class, args);
        ApplicationContext context = SpringUtil.getApplicationContext();


        //定义文件位置
        String profilePath = "src/main/java/com/nuaa/isisnetwork/profile";
        String xmlPath = "src/main/java/com/nuaa/isisnetwork/networkInformation/networkXml";
        //定义日志
        WriteLog writeLog = context.getBean(WriteLog.class);

        System.out.println("==============1.网管信息生成xml文件并写入数据库============");
        FtlXml ftlXml = context.getBean(FtlXml.class);
        ftlXml.createXml(profilePath,xmlPath);


        System.out.println("==============2.创建LXD容器============");
        CreateLxd createLxd = context.getBean(CreateLxd.class);
        List<String> cmd2 = createLxd.createLxd(profilePath).get(0);
        List<String> delete2 = createLxd.createLxd(profilePath).get(1);
        writeLog.createOrDeleteBash(cmd2,"create");
        writeLog.createOrDeleteBash(delete2,"delete");



        System.out.println("==============3.创建并连接网桥============");
        CreateBridge createBridge = context.getBean(CreateBridge.class);
        List<String> cmd3 = createBridge.CreateAndAttachBridge(profilePath).get(0);
        List<String> delete3 = createBridge.CreateAndAttachBridge(profilePath).get(1);
        writeLog.createOrDeleteBash(cmd3,"create");
        writeLog.createOrDeleteBash(delete3,"delete");
    }

}
