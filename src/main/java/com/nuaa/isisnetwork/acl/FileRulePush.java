package com.nuaa.isisnetwork.acl;

import com.nuaa.isisnetwork.utils.InfoUtil;
import com.nuaa.isisnetwork.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-07 16:27
 * @Java-version jdk1.8
 */
//替换配置文件
@Service
@Component
public class FileRulePush {

    @Autowired
    InfoUtil infoUtil;
    @Autowired
    WriteLog writeLog;


    //将生成的配置文件替换
    public List<String> pushRule(String profilePath) throws IOException {
        List<String> cmds = new ArrayList<>();
        //获取目录下的所有拥有acl的容器名称
        List<String> lxdNameListByAcl = infoUtil.getLxdNameListByAcl(profilePath);
        for (String name:lxdNameListByAcl){
            //0.先下载防火墙
            cmds.add("lxc exec "+name+" -- apt-get install iptables -y;");
            //1. 先替换防火墙的配置文件
            cmds.add("lxc file push /home/yzx/AutoNetwork/"+name+"/iptables.rules "+name+"/etc/");
            //2. 然后替换保存防火墙的脚本文件
            cmds.add("lxc file push /home/yzx/AutoNetwork/"+name+"/rc.local "+name+"/etc/");
            //3. 最后替换开机自启脚本文件
            cmds.add("lxc file push /home/yzx/AutoNetwork/"+name+"/startUpIptables.sh "+name+"/root/");
            //4. 运行开机自启脚本
            cmds.add("lxc exec "+name+" -- bash /root/startUpIptables.sh");
            writeLog.log("成功替换容器【"+name+"】中Acl配置文件");
        }
        return cmds;
    }
}
