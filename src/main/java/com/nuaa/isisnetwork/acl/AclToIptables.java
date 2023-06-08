package com.nuaa.isisnetwork.acl;

import com.nuaa.isisnetwork.pojo.Iptables;
import com.nuaa.isisnetwork.service.IptablesService;
import com.nuaa.isisnetwork.utils.InfoUtil;
import com.nuaa.isisnetwork.utils.NetmaskUtil;
import com.nuaa.isisnetwork.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author YZX
 * @Create 2023-06-06 16:03
 * @Java-version jdk1.8
 */
//将ACl语句转化为Iptables类
@Service
@Component
public class AclToIptables {

    @Autowired
    NetmaskUtil netmaskUtil;
    @Autowired
    IptablesService iptablesService;
    @Autowired
    WriteLog writeLog;



    /**
     * @description  将不同厂商的单条ACL语句转化为Iptables类
     * @date 2023/6/6 19:56
     * @params [line：单条ACl语句, lxdName：容器名称, manufacturer：厂商名称]
     * @returns Iptables
     */
    public Iptables turnLineToIptables(String line, String lxdName, String manufacturer){
        //中兴匹配规则
        String ZTE_Regex = "\\s+rule\\s+\\d*\\s*(permit|deny)(?:\\s+(tcp|udp|icmp|ip))?" +
                //匹配源ip地址 或 “any” 关键字
        "(?:\\s+((?:\\d{1,3}\\.){3}\\d{1,3}|\\w+))?" +
                //匹配源子网掩码
        "(?:\\s+((?:\\d{1,3}\\.){3}\\d{1,3}))?" +
                //匹配源端口“range”关键字、起始端口和结束端口  或   端口“eq”关键字和端口值
        "(?:\\s+(range)\\s+(\\d+)-(\\d+)|\\s+(eq|ge|le)\\s+(\\d+))?" +
                //匹配目的ip地址 或 “any” 关键字
        "(?:\\s+((?:\\d{1,3}\\.){3}\\d{1,3}|\\w+))?" +
                //匹配目的子网掩码
        "(?:\\s+((?:\\d{1,3}\\.){3}\\d{1,3}))?" +
                //匹配目的端口“range”关键字、起始端口和结束端口  或   端口“eq”关键字和端口值
        "(?:\\s+(range)\\s+(\\d+)-(\\d+)|\\s+(eq|ge|le)\\s+(\\d+))?" +
                //匹配两个特殊关键字
        "(?:\\s+(established))?" +
        "(?:\\s+precedence\\s+\\d+)?";

        String HuaWei_Regex = "\\s+rule\\s+\\d*\\s*(permit|deny)(?:\\s+(tcp|udp|icmp|ip))?" +
                "(?:\\s+source\\s((?:\\d{1,3}\\.){3}\\d{1,3}|any))?" +
                "(?:\\s+((?:\\d{1,3}\\.){3}\\d{1,3}))?" +
                "(?:\\s+destination\\s((?:\\d{1,3}\\.){3}\\d{1,3}|any))?" +
                "(?:\\s+((?:\\d{1,3}\\.){3}\\d{1,3}))?" +
                "(?:\\s+(range)\\s+(\\d+)\\s(\\d+)|\\s+source-port\\s+(eq|ge|le)\\s(\\d+))?" +
                "(?:\\s+(range)\\s+(\\d+)\\s(\\d+)|\\s+destination-port\\s+(eq|ge|le)\\s(\\d+))?" +
                "(?:\\s+(established))?" +//tcp group(17)
                "(?:\\s+precedence\\s+\\d+)?";

        //定义匹配
        Pattern pattern = null;
        //要不用中兴匹配规则，默认使用华为匹配规则
        if ("ZTE".equals(manufacturer)){
            pattern = Pattern.compile(ZTE_Regex);
        } else{
            pattern = Pattern.compile(HuaWei_Regex);
        }
        Matcher matcher = pattern.matcher(line);
        //用于记录属性
        List<String> proList = new ArrayList<>();
        //起始为null，只有匹配成功才创建类
        Iptables iptables = null;
        //匹配成功才封装属性
        if (matcher.find()){
            iptables = new Iptables();
            //将属性封装到集合当中
            for (int i = 1 ; i<17 ; i++){
                if (matcher.group(i)!=null){
                    proList.add(matcher.group(i));
                }else {
                    proList.add(null);
                }
            }

            //将集合中的属性转化为实体类
            iptables.setId(0);
            iptables.setManufacturer(manufacturer);
            iptables.setLxdName(lxdName);
            iptables.setTableName("filter");
            iptables.setRule("A");
            iptables.setChain("FORWARD");

            if (proList.size()>0){
                iptables.setJudge(proList.get(0));
                iptables.setProtocol(proList.get(1));

                iptables.setSIp(proList.get(2));
                if (proList.get(3)!=null){
                    //将二进制的子网掩码转化为数字
                    iptables.setSNetmask(netmaskUtil.calculateAclMask(proList.get(3)));
                }
                iptables.setSRange(proList.get(4));
                iptables.setSStartPort(proList.get(5));
                iptables.setSDestPort(proList.get(6));
                iptables.setSEq(proList.get(7));
                iptables.setSPort(proList.get(8));

                iptables.setDIp(proList.get(9));
                if (proList.get(10)!=null){
                    //将二进制的子网掩码转化为数字
                    iptables.setDNetmask(netmaskUtil.calculateAclMask(proList.get(10)));
                }
                iptables.setDRange(proList.get(11));
                iptables.setDStartPort(proList.get(12));
                iptables.setDDestPort(proList.get(13));
                iptables.setDEq(proList.get(14));
                iptables.setDPort(proList.get(15));
            }

        }
        return iptables;
    }


    //处理一个ACL文本文件，将符合的rule全部转化为iptables类
    public List<Iptables> turnTxtToIptables(File file) throws IOException {
        //用来返回结果
        List<Iptables> iptablesList = new ArrayList<>();
        //获得容器名称
        String[] splitInfo = file.getName().split("_");
        String lxdName = splitInfo[0];
        //获得厂商名称
        String manufacturer = splitInfo[1];
        //创建读取流
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;
        while ((line=reader.readLine())!=null && !"".equals(line)){
            Iptables iptables = turnLineToIptables(line, lxdName, manufacturer);
            if (iptables!=null){
                iptablesList.add(iptables);
            }
        }
        return iptablesList;
    }


    //处理文件夹下面的所有ACL文本，将其存入数据库
    public void turnToIptables(String profilePath) throws IOException {
        //获取目录下的拥有acl的文件名称
        File[] listFiles = new File(profilePath).listFiles();
        for (File file:listFiles){
            //只有包含acl的才处理
            if (file.getName().contains("Acl")){
                //用来返回类对象
                List<Iptables> iptablesList = turnTxtToIptables(file);
                //存入数据库中
                iptablesService.saveList(iptablesList);
                writeLog.log("成功将【"+file.getName()+"】中转化完毕的ACL存储到数据库中");
            }
        }
    }
}
