package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.pojo.NetInterfaces;
import com.nuaa.isisnetwork.pojo.Routers;
import com.nuaa.isisnetwork.service.NetInterfacesService;
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
 * @Create 2023-06-05 18:44
 * @Java-version jdk1.8
 */
//创建接口配置文件
@Service
@Component
public class CreateYaml {
    @Autowired
    InfoUtil infoUtil;
    @Autowired
    NetInterfacesService interfacesService;
    @Autowired
    WriteLog writeLog;


    //根据传入的路径下对应的全部容器信息创建网络接口配置文件
    public List<String> touchAllYaml(String profilePath) throws IOException {
        List<String> cmds = new ArrayList<>();
        List<Routers> routersList = infoUtil.getLxdList(profilePath);
        for (Routers routers:routersList){
            //针对每个路由器生成一个文件夹
            cmds.add("mkdir -p /home/yzx/AutoNetwork/"+routers.getName());
            String yaml = touchYaml(routers);
            //将命令放入到集合中
            cmds.add("echo \""+yaml+"\" > /home/yzx/AutoNetwork/"+routers.getName()+"/10-lxc.yaml");
            //写入日志
            writeLog.log("成功创建文件夹 /home/yzx/AutoNetwork/"+routers.getName()+",且写入接口配置文件");
        }
        return cmds;
    }

    //根据传入的单个容器信息创建网络接口配置文件
    public String touchYaml(Routers routers){
        StringBuffer yaml = new StringBuffer();
        //先写入固定的头文件
        yaml.append("network:\n" +
                "  version: 2\n" +
                "  ethernets:\n" +
                "    eth0:\n" +
                "      dhcp4: true\n" +
                "      dhcp-identifier: mac\n");
        //获取路由器中网卡的id
        String[] splitId = routers.getInterfacesId().split("_");
        //开始写入接口描述
        for (int i = 0 ; i<splitId.length;i++){
            NetInterfaces interfaces = interfacesService.getById(Integer.parseInt(splitId[i]));
            //添加dhcp和ip
            yaml.append("    "+interfaces.getName()+":\n" +
                    "      dhcp4: false\n" +
                    "      addresses: ["+interfaces.getIpAddress()+"/"+interfaces.getSubnetMask()+"]");
            //如果不是最后一个接口信息，就输入换行
            if (i!=(splitId.length-1)){
                yaml.append("\n");
            }
        }
        return new String(yaml);
    }
}
