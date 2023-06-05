package com.nuaa.isisnetwork.nfv;

import com.nuaa.isisnetwork.pojo.Bridge;
import com.nuaa.isisnetwork.pojo.NetInterfaces;
import com.nuaa.isisnetwork.service.BridgeService;
import com.nuaa.isisnetwork.utils.InfoUtil;
import com.nuaa.isisnetwork.utils.NetmaskUtil;
import com.nuaa.isisnetwork.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * @Author YZX
 * @Create 2023-06-05 11:06
 * @Java-version jdk1.8
 */
@Service
@Component
//创建网桥
public class CreateBridge {
    @Autowired
    NetmaskUtil netmaskUtil;
    @Autowired
    InfoUtil infoUtil;
    @Autowired
    WriteLog writeLog;
    @Autowired
    BridgeService bridgeService;

    public static void main(String[] args) {
        CreateBridge createBridge = new CreateBridge();
        NetInterfaces interfaces1 = new NetInterfaces(1, "1", "192.168.15.1", 30, "lxd1");
        NetInterfaces interfaces2 = new NetInterfaces(2, "1", "192.168.15.2", 30, "lxd2");
        NetInterfaces interfaces3 = new NetInterfaces(3, "1", "192.168.15.3", 30, "lxd3");
        NetInterfaces interfaces4 = new NetInterfaces(4, "1", "192.168.15.4", 30, "lxd4");
        NetInterfaces interfaces5 = new NetInterfaces(5, "1", "192.168.15.5", 30, "lxd5");
        NetInterfaces interfaces6 = new NetInterfaces(6, "1", "192.168.15.6", 30, "lxd6");
        NetInterfaces interfaces7 = new NetInterfaces(7, "1", "192.168.15.7", 30, "lxd7");
        ArrayList<NetInterfaces> list = new ArrayList<>();
        list.add(interfaces2);
        list.add(interfaces3);
        list.add(interfaces4);
        list.add(interfaces5);
        list.add(interfaces6);
        list.add(interfaces7);
        NetInterfaces find = createBridge.matchIp(interfaces1, list);
        System.out.println("找到了find为:"+find);
    }



    /**
     * @description  构建网桥以及连接网桥
     * @date 2023/6/5 20:06
     * @params [profilePath:网管信息存储位置]
     * @returns 第一个元素返回构造容器，第二个元素返回强行删除容器
     */
    public List<List<String>> CreateAndAttachBridge(String profilePath) throws Exception {
        List<List<String>> list = new ArrayList<>();
        //返回网桥的创建语句
        List<String> cmds = new ArrayList<>();
        //用来删除网桥
        List<String> delete = new ArrayList<>();
        //1. 获取当前配置文件对应的网卡集合信息
        List<NetInterfaces> netList = infoUtil.getNetInterfacesList(profilePath);
        //2. 匹配接口
        for (int i = 0 ; i<list.size() ; i++){
            //待匹配的网卡接口
            NetInterfaces searchInterfaces = netList.get(i);
            NetInterfaces findInterfaces = matchIp(searchInterfaces, netList);
            //2.1 匹配失败
            if (findInterfaces==null){
                writeLog.log("第"+i+"条:容器【"+searchInterfaces.getLxdName()+"】中端口【"+searchInterfaces.getName()+"】匹配失败");
            }else {
                //2.2 匹配成功
                writeLog.log("第"+i+"条:接口【"+searchInterfaces.getLxdName()+":"+searchInterfaces.getName()+"】匹配成功【"+findInterfaces.getLxdName()+":"+findInterfaces.getName()+"】");
                //创建网桥
                String bridgeName = searchInterfaces.getLxdName()+findInterfaces.getLxdName();
                cmds.add("lxc network create "+bridgeName+" ipv6.address=none ipv4.address=none;");
                //连接网桥(参数分别为网桥名称、容器名称以及容器网卡名称)
                cmds.add("lxc network attach "+bridgeName+" "+searchInterfaces.getLxdName()+" "+searchInterfaces.getName()+";");
                cmds.add("lxc network attach "+bridgeName+" "+findInterfaces.getLxdName()+" "+findInterfaces.getName()+";");
                delete.add("lxc network delete "+bridgeName);
                //将成功创建的网桥信息存入数据库
                bridgeService.save(new Bridge(0,bridgeName,searchInterfaces.getLxdName()+":"+searchInterfaces.getName(),findInterfaces.getLxdName()+":"+findInterfaces.getName()));
                //将查询到的接口移除
                list.remove(findInterfaces);
            }
        }
        list.add(cmds);
        list.add(delete);
        return list;
    }
    
    

    /**
     * @description  在一个列表中，令待匹配的ip地址占到匹配成功的ip地址
     * @date 2023/6/5 11:16
     * @params [ip：需要搜索的ip地址, netMask：需要搜索的ip地址的子网掩码, list：带搜索的列表]
     * @returns NetInterfaces
     */
    public NetInterfaces matchIp(NetInterfaces searchNetInterfaces, List<NetInterfaces> list){
        for (int i = 0 ; i<list.size() ; i++){
            //待比较的接口
            NetInterfaces findInterface = list.get(i);
            //掩码相同并且比较的ip不是主机本身
            if (!searchNetInterfaces.getIpAddress().equals(findInterface.getIpAddress()) && findInterface.getSubnetMask()==searchNetInterfaces.getSubnetMask()){
                //如果掩码对应的ip完全一样，那么则表示匹配成功
                if (compareIp(searchNetInterfaces.getIpAddress(),findInterface.getIpAddress(),searchNetInterfaces.getSubnetMask())==0){
                    return findInterface;
                }
            }
        }
        //否则表示没找到对应的ip地址
        return null;
    }


    /**
     * @description  比较两个指定掩码的ip地址是否在同一网段
     * @date 2023/6/5 11:10
     * @params [ip：比较ip, searchIp：待比较ip, netMask：掩码]
     * @returns int：0表示在同一个网段，-1和1表示不在同一个网段
     */
    public int compareIp(String ip,String searchIp,Integer netMask){
        //如果掩码数量小于等于8，直接切割比较
        if (netMask<=8){
            return netmaskUtil.tenToTwo(ip).substring(0,netMask).compareTo(netmaskUtil.tenToTwo(searchIp).substring(0,netMask));
        } else if (netMask>8 && netMask<=16) {
            //如果掩码数量9-16，因为要添加一个“.”，那么长度+1
            return netmaskUtil.tenToTwo(ip).substring(0,netMask+1).compareTo(netmaskUtil.tenToTwo(searchIp).substring(0,netMask+1));
        } else if (netMask>16 && netMask<=24) {
            //如果掩码数量17-24，因为要添加两个“.”，那么长度+2
            return netmaskUtil.tenToTwo(ip).substring(0,netMask+2).compareTo(netmaskUtil.tenToTwo(searchIp).substring(0,netMask+2));
        }else {
            //如果掩码数量25-32，因为要添加三个“.”，那么长度+3
            return netmaskUtil.tenToTwo(ip).substring(0,netMask+3).compareTo(netmaskUtil.tenToTwo(searchIp).substring(0,netMask+3));
        }
    }

}
