package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.pojo.Routers;
import com.nuaa.isisnetwork.service.RoutersService;
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
 * @Create 2023-06-02 17:20
 * @Java-version jdk1.8
 */
//获取容器信息
@Service
@Component
public class CreateLxd {
    @Autowired
    InfoUtil infoUtil;
    @Autowired
    WriteLog writeLog;

    //根据网管信息文件构造容器
    public List<String> createLxd(String profilePath) throws IOException {
        List<String> cmds = new ArrayList<>();
        //查询当前配置文件对应的容器名称
        List<String> lxdNameList = infoUtil.getLxdNameList(profilePath);
        //构造
        for (String lxdName:lxdNameList){
            //将创建容器的命令放入到集合中
            cmds.add("lxc copy MouldLxd "+lxdName);
            writeLog.log("创建容器【"+lxdName+"】");
        }
        return cmds;
    }



}
