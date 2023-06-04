package com.nuaa.isisnetwork;

import com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate.FtlXml;
import com.nuaa.isisnetwork.service.MangeDaoService;
import freemarker.template.TemplateException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@EnableTransactionManagement
public class IsisNetworkApplication {

    public static void main(String[] args) throws TemplateException, IOException {

        SpringApplication.run(IsisNetworkApplication.class, args);
        ApplicationContext context = SpringUtil.getApplicationContext();

        //定义文件位置
        String profilePath = "src/main/java/com/nuaa/isisnetwork/profile";
        String xmlPath = "src/main/java/com/nuaa/isisnetwork/networkInformation/networkXml";


        System.out.println("==============1.生成xml文件并写入数据库============");
        FtlXml ftlXml = context.getBean(FtlXml.class);
        ftlXml.createXml(profilePath,xmlPath);



    }

}
