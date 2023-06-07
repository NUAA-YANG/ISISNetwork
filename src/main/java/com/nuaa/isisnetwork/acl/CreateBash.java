package com.nuaa.isisnetwork.acl;

import com.nuaa.isisnetwork.pojo.Iptables;
import com.nuaa.isisnetwork.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author YZX
 * @Create 2023-06-06 20:42
 * @Java-version jdk1.8
 */
//完整的转化，将ACL文本命令转化为类存入数据库并且生成cmd命令提供执行
@Service
@Component
public class CreateBash {
    @Autowired
    IptablesToLine iptablesToLine;
    @Autowired
    WriteLog writeLog;


    //对于每个容器都生成rule和bash文件
    public List<String> createAllBash(String profilePath) {
        List<String> cmds = new ArrayList<>();
        //获得容器生成的rule规则，其中key为容器名称，value为rule规则
        Map<String, String> map = iptablesToLine.turnAllIptablesToLine(profilePath);
        map.forEach((lxdName,iptablesRule)->{
            //1. 生成防火墙配置文件
            cmds.add("echo \""+iptablesRule+"\" >> /root/AutoNetwork/"+lxdName+"/iptables.rules;");
            //2. 生成保存防火墙的脚本文件
            cmds.add("echo \""+createRcLocal()+"\" >> /root/AutoNetwork/"+lxdName+"/rc.local;");
            //3. 为防火墙的脚本文件设置权限
            cmds.add("chmod 777 /root/AutoNetwork/"+lxdName+"/rc.local");
            //4. 生成开机自启脚本文件
            cmds.add("echo \""+createBash()+"\" >> /root/AutoNetwork/"+lxdName+"/startUpIptables.sh;");
            try {
                writeLog.log("成功替换容器【"+lxdName+"】中Acl配置文件");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return cmds;
    }


    //创建自启动脚本文件同时设置防火墙读取文件信息
    public String createBash(){
        String resultBash = "#!/bin/bash\n"+
                "chmod 777 /lib/systemd/system/rc-local.service\n"+
                "systemctl enable /lib/systemd/system/rc-local.service\n"+
                "iptables-restore < /etc/iptables.rules;";
        return resultBash;
    }

    //保证每次重启都会读取防火墙配置文件
    public String createRcLocal(){
        String RcLocal = "#!/bin/sh\n" +
                "iptables-restore < /etc/iptables.rules\n" +
                "exit 0";
        return RcLocal;
    }


}
