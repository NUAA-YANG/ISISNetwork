package com.nuaa.isisnetwork.utils;

import com.nuaa.isisnetwork.pojo.Iptables;
import com.nuaa.isisnetwork.pojo.NetInterfaces;
import com.nuaa.isisnetwork.pojo.Routers;
import com.nuaa.isisnetwork.service.IptablesService;
import com.nuaa.isisnetwork.service.NetInterfacesService;
import com.nuaa.isisnetwork.service.RoutersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YZX
 * @Create 2023-06-04 11:07
 * @Java-version jdk1.8
 */
@Service
@Component
public class InfoUtil {

    @Autowired
    RoutersService routersService;
    @Autowired
    NetInterfacesService netInterfacesService;
    @Autowired
    IptablesService iptablesService;

    //获取指定路径下面的全部容器名称集合
    public List<String> getLxdNameList(String profilePath){
        String[] nameList = new File(profilePath).list();
        ArrayList<String> lxdNameList = new ArrayList<>();
        for (String name:nameList){
            lxdNameList.add(name.split("_")[0]);
        }
        return lxdNameList;
    }

    //获取指定路径下有Acl命令的容器名称
    public List<String> getLxdNameListByAcl(String profilePath){
        String[] nameList = new File(profilePath).list();
        ArrayList<String> lxdNameList = new ArrayList<>();
        for (String name:nameList){
            if (name.contains("Acl")){
                lxdNameList.add(name.split("_")[0]);
            }
        }
        return lxdNameList;
    }


    //获取指定路径下面的全部容器信息集合
    public List<Routers> getLxdList(String profilePath){
        String[] nameList = new File(profilePath).list();
        ArrayList<Routers> lxdList = new ArrayList<>();
        for (String name:nameList){
            Routers router = routersService.getByName(name.split("_")[0]);
            if (router!=null){
                lxdList.add(router);
            }
        }
        return lxdList;
    }


    //获取指定路径下面容器对应的全部网卡集合
    public List<NetInterfaces> getNetInterfacesList(String profilePath){
        String[] nameList = new File(profilePath).list();
        ArrayList<NetInterfaces> netInterfacesList = new ArrayList<>();
        for (String name:nameList){
            netInterfacesList.addAll(netInterfacesService.getListByLxdName(name.split("_")[0]));
        }
        return netInterfacesList;
    }

}
