package com.nuaa.isisnetwork;

import com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate.FtlXml;
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


        System.out.println("==============1.测试生成xml文件============");
        FtlXml ftlXml = context.getBean(FtlXml.class);
        ftlXml.createXml(profilePath,xmlPath);



    }

}
