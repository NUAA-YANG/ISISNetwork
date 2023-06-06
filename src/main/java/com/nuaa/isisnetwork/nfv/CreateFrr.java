package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.pojo.ISIS;
import com.nuaa.isisnetwork.pojo.Routers;
import com.nuaa.isisnetwork.service.ISISService;
import com.nuaa.isisnetwork.service.NetInterfacesService;
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
 * @Create 2023-06-05 19:29
 * @Java-version jdk1.8
 */
//创建frr配置文件
@Service
@Component
public class CreateFrr {
    @Autowired
    ISISService isisService;
    @Autowired
    InfoUtil infoUtil;
    @Autowired
    WriteLog writeLog;


    //根据传入的路径下对应的全部容器信息创建frr配置文件
    public List<String> touchAllConfig(String profilePath) throws IOException {
        List<String> cmds = new ArrayList<>();
        List<Routers> routersList = infoUtil.getLxdList(profilePath);
        for (Routers routers:routersList){
            String config = touchConfig(routers);
            //将创建的命令放到集合中
            cmds.add("echo \""+config+"\" > /home/yzx/AutoNetwork/"+routers.getName()+"/frr.conf");
            //写入日志
            writeLog.log("成功创建文件夹 /home/yzx/AutoNetwork/"+routers.getName()+",且写入【frr.conf】网络配置文件");
        }
        return cmds;
    }

    //根据传入的单个容器信息创建frr配置文件
    public String touchConfig(Routers routers){
        StringBuffer config = new StringBuffer();
        //添加固定头文件
        config.append("frr version 8.5-dev-MyOwnFRRVersion\n" +
                "frr defaults traditional\n" +
                "hostname "+routers.getName()+"\n" +
                "log syslog informational\n" +
                "service integrated-vtysh-config\n"+
                "!"+"\n"+
                "interface eth0\n" +
                " mpls enable\n" +
                "exit\n" +
                "!"+"\n");
        Integer isisId = routers.getIsisId();
        ISIS isis = isisService.getById(isisId);
        if (isis!=null){
            //遍历添加相关网卡信息
            String[] ethNameArray = isis.getEthName().split(";");
            for (String ethName:ethNameArray){
                if (!ethName.equals("")){
                    config.append("interface "+ethName+"\n" +
                            " ip router isis "+isis.getFlag()+"\n" +
                            "exit\n" +
                            "!"+"\n");
                }
            }
            config.append("router isis "+isis.getFlag()+"\n" +
                    " is-type "+isis.getType()+"\n" +
                    " net "+isis.getNet()+"\n" +
                    "exit\n" +
                    "!"+"\n");
        }
        //添加尾部文件
        config.append("segment-routing\n" +
                " traffic-eng\n" +
                " exit\n" +
                "exit\n" +
                "!");
        return new String(config);
    }

}
