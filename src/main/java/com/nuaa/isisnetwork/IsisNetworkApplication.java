package com.nuaa.isisnetwork;

import com.jcraft.jsch.Session;
import com.nuaa.isisnetwork.acl.AclToIptables;
import com.nuaa.isisnetwork.acl.CreateBash;
import com.nuaa.isisnetwork.acl.FileRulePush;
import com.nuaa.isisnetwork.linuxCommand.ExecLinuxCommands;
import com.nuaa.isisnetwork.linuxCommand.LinuxConnection;
import com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate.FtlXml;
import com.nuaa.isisnetwork.nfv.*;
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

        System.out.println("==============0.创建远程连接============");
        writeLog.log("==============0.创建远程连接============");
        LinuxConnection connection = context.getBean(LinuxConnection.class);
        Session session = connection.getJSchSession("192.168.31.104", 22, "root", "root");
        ExecLinuxCommands execLinuxCommands = context.getBean(ExecLinuxCommands.class);

        System.out.println("==============1.网管信息生成xml文件并写入数据库============");
        writeLog.log("==============1.网管信息生成xml文件并写入数据库============");
        FtlXml ftlXml = context.getBean(FtlXml.class);
        ftlXml.createXml(profilePath,xmlPath);

        System.out.println("==================2.创建LXD容器================");
        writeLog.log("==================2.创建LXD容器================");
        CreateLxd createLxd = context.getBean(CreateLxd.class);
        List<List<String>> list2 = createLxd.createLxd(profilePath);
        List<String> cmd2 = list2.get(0);
        List<String> delete2 = list2.get(1);
        execLinuxCommands.getCmdResult(session,cmd2);
        writeLog.createOrDeleteBash("#========2.创建LXD容器=========",cmd2,"create");
        writeLog.createOrDeleteBash("#========2.删除LXD容器=========",delete2,"delete");

        System.out.println("=================3.创建并连接网桥===============");
        writeLog.log("=================3.创建并连接网桥===============");
        CreateBridge createBridge = context.getBean(CreateBridge.class);
        List<List<String>> list3 = createBridge.createBridge(profilePath);
        List<String> cmd3 = list3.get(0);
        List<String> delete3 = list3.get(1);
        execLinuxCommands.getCmdResult(session,cmd3);
        writeLog.createOrDeleteBash("#========3.创建并连接网桥=========",cmd3,"create");
        writeLog.createOrDeleteBash("#========3.删除网桥=========",delete3,"delete");


        System.out.println("=================4.创建接口配置文件===============");
        writeLog.log("=================4.创建接口配置文件===============");
        CreateYaml createYaml = context.getBean(CreateYaml.class);
        List<String> cmd4 = createYaml.touchAllYaml(profilePath);
        execLinuxCommands.getCmdResult(session,cmd4);
        writeLog.createOrDeleteBash("#========4.创建接口配置文件=========",cmd4,"create");

        System.out.println("=================5.创建网络配置文件===============");
        writeLog.log("=================5.创建网络配置文件===============");
        CreateFrr createFrr = context.getBean(CreateFrr.class);
        List<String> cmd5 = createFrr.touchAllConfig(profilePath);
        execLinuxCommands.getCmdResult(session,cmd5);
        writeLog.createOrDeleteBash("#========5.创建网络配置文件=========",cmd5,"create");

        System.out.println("==============6.启动所有容器============");
        writeLog.log("==============6.启动所有容器============");
        ChangeState changeState6 = context.getBean(ChangeState.class);
        List<String> cmd6 = changeState6.startAllLxd(profilePath);
        execLinuxCommands.getCmdResult(session,cmd6);
        writeLog.createOrDeleteBash("#========6.启动所有容器=========",cmd6,"create");

        System.out.println("==============7.替换配置文件============");
        writeLog.log("==============7.替换配置文件============");
        FilePush filePush = context.getBean(FilePush.class);
        List<String> cmd7 = filePush.pushYamlAndFrr(profilePath);
        execLinuxCommands.getCmdResult(session,cmd7);
        writeLog.createOrDeleteBash("#========7.替换配置文件=========",cmd7,"create");

        System.out.println("==============8.重启接口配置以及网络服务============");
        writeLog.log("==============8.重启接口配置以及网络服务============");
        ChangeState changeState8 = context.getBean(ChangeState.class);
        List<String> cmd8 = changeState8.netplanApply(profilePath);
        execLinuxCommands.getCmdResult(session,cmd8);
        writeLog.createOrDeleteBash("#========8.重启网络=========",cmd8,"create");
        List<String> cmd9 = changeState8.restartFrr(profilePath);
        execLinuxCommands.getCmdResult(session,cmd9);
        writeLog.createOrDeleteBash("#========9.重启Frr=========",cmd9,"create");


//        System.out.println("==============10.存储ACL============");
//        writeLog.log("==============10.存储ACL============");
//        AclToIptables aclToIptables = context.getBean(AclToIptables.class);
//        aclToIptables.turnToIptables(profilePath);
//
//        System.out.println("==============11.生成ACL规则============");
//        writeLog.log("==============11.生成ACL规则============");
//        CreateBash createBash = context.getBean(CreateBash.class);
//        List<String> cmd11 = createBash.createAllBash(profilePath);
//        execLinuxCommands.getCmdResult(session,cmd11);
//        writeLog.createOrDeleteBash("#========11.生成ACL规则=========",cmd11,"create");
//
//        System.out.println("==============12.替换并加载ACL============");
//        writeLog.log("==============12.替换并加载ACL============");
//        FileRulePush fileRulePush = context.getBean(FileRulePush.class);
//        List<String> cmd12 = fileRulePush.pushRule(profilePath);
//        execLinuxCommands.getCmdResult(session,cmd12);
//        writeLog.createOrDeleteBash("#========12.替换并加载ACL=========",cmd12,"create");

        writeLog.log("==============关闭服务器连接============");
        connection.closeJSchSession(session);
    }

}
