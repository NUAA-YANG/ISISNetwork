package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.utils.InfoUtil;
import com.nuaa.isisnetwork.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-05 20:05
 * @Java-version jdk1.8
 */
//替换创建的配置文件
@Service
@Component
public class FilePush {
    @Autowired
    InfoUtil infoUtil;
    @Autowired
    WriteLog writeLog;

    //将接口配置文件【10-yaml】和协议配置文件【frr.config】覆盖原来的配置文件
    public List<String> pushYamlAndFrr(String profilePath) throws IOException {
        List<String> cmds = new ArrayList<>();
        List<String> lxdNameList = infoUtil.getLxdNameList(profilePath);
        for (String name:lxdNameList){
            cmds.add("lxc file push /home/yzx/AutoNetwork/"+name+"/10-lxc.yaml "+name+"/etc/netplan/");
            cmds.add("lxc file push /home/yzx/AutoNetwork/"+name+"/frr.conf "+name+"/etc/frr/");
            writeLog.log("成功将容器【"+name+"】中的文件进行替换");
        }
        return cmds;
    }
}
