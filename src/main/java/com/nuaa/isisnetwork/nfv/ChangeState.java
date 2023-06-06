package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.utils.InfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-06 9:36
 * @Java-version jdk1.8
 */
//重启或启动容器 亦或 启动或重启容器服务
@Service
@Component
public class ChangeState {
    @Autowired
    InfoUtil infoUtil;

    //启动所有容器
    public List<String> startAllLxd(String profilePath){
        List<String> cmds = new ArrayList<>();
        //查询当前配置文件对应的容器名称
        List<String> lxdNameList = infoUtil.getLxdNameList(profilePath);
        //遍历
        for (String name:lxdNameList){
            //启动容器
            cmds.add("lxc start "+name);
        }
        return cmds;
    }

    //重启所有网络
    public List<String> netplanApply(String profilePath){
        List<String> cmds = new ArrayList<>();
        //查询当前配置文件对应的容器名称
        List<String> lxdNameList = infoUtil.getLxdNameList(profilePath);
        //遍历
        for (String name:lxdNameList){
            //重启网络
            cmds.add("lxc exec "+name+" netplan apply");
        }
        return cmds;
    }

    //重启frr软件
    public List<String> restartFrr(String profilePath){
        List<String> cmds = new ArrayList<>();
        //查询当前配置文件对应的容器名称
        List<String> lxdNameList = infoUtil.getLxdNameList(profilePath);
        //遍历
        for (String name:lxdNameList){
            //重启frr
            cmds.add("lxc exec "+name+" systemctl restart frr");
        }
        return cmds;
    }
}
