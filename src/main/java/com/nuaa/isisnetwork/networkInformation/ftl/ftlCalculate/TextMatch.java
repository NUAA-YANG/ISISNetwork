package com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate;

import com.nuaa.isisnetwork.pojo.ISIS;
import com.nuaa.isisnetwork.pojo.NetInterfaces;
import com.nuaa.isisnetwork.pojo.Routers;
import com.nuaa.isisnetwork.service.ISISService;
import com.nuaa.isisnetwork.service.NetInterfacesService;
import com.nuaa.isisnetwork.service.RoutersService;
import com.nuaa.isisnetwork.utils.NetmaskUtil;
import com.nuaa.isisnetwork.utils.WriteLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author YZX
 * @Create 2023-06-01 17:09
 * @Java-version jdk1.8
 */
//匹配不同厂商的网关信息，将其转化为Map键值对
@Service
@Component
public class TextMatch {
    @Autowired
    NetmaskUtil netmaskUtil;
    @Autowired
    ISISService isisService;
    @Autowired
    NetInterfacesService netInterfacesService;
    @Autowired
    RoutersService routersService;
    @Autowired
    WriteLog writeLog;

    public static void main(String[] args) throws IOException {
        TextMatch textMatch = new TextMatch();
        //测试华为配置文件
        File file = new File("src/main/java/com/nuaa/isisnetwork/profile/R01_HuaWei_Acl.txt");
        //测试紫光配置文件
        //File file = new File("src/main/java/com/nuaa/isisnetwork/profile/R02_UNIS_.txt");
        //测试中兴配置文件
        //File file = new File("src/main/java/com/nuaa/isisnetwork/profile/R03_ZTE_Acl.txt");

        Map<String, Object> map = textMatch.MatchFtl(file);
        Set<String> keySet = map.keySet();
        for (String key:keySet){
            Object o = map.get(key);
            System.out.println(key+":"+o);
            System.out.println("=========================");
        }
    }

    public Map<String,Object> MatchFtl(File file) throws IOException {

        //存储最后的结果
        Map<String, Object> dataMap = new HashMap<>();
        //根据文件来获取容器名称和所属厂商
        String[] lxcInfo = file.getName().split("_");
        //配置文件中获取的容器名称
        String lxdName = lxcInfo[0];
        String manufacture = lxcInfo[1];


        //读取文本文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = null;

        //存储网卡的接口
        List<NetInterfaces> netInterfacesList = new ArrayList<>();
        //定义网管文件中识别到的容器名称
        String profileLxdName = null;
        //isis编号
        String flag = null;
        //isis协议等级
        String type = null;
        //isis协议net号
        String net = null;
        //isis协议网卡的名称
        String ethName = "";
        //标识是否有isis协议
        String isisFlag = null;
        //容器拥有网卡拼接id
        String interfacesId = "";
        //isis协议id
        Integer isisId = 0;

        //定义关键字
        String keyWord = null;
        //定义匹配类
        Matcher matcher = null;

        //开始循环读取匹配
        while ((line = reader.readLine())!=null){

            //网卡标识也会出现在isis协议中，所以只需要包含关键字即可
            if (line.contains("interface")){
                keyWord = line;
            } else if (line.startsWith("isis") ) {
                //华为和紫光isis协议用“isis”开头，表示有isis协议
                keyWord = line;
                isisFlag = "yes";
                flag = keyWord.split("\\s+")[1];
            } else if (line.startsWith("router isis")) {
                //中兴isis协议用“router isis”开头，表示有isis协议
                keyWord = line;
                isisFlag = "yes";
                flag = keyWord.split("\\s+")[2];
            }

            //匹配IP地址，表示先出现三遍【数字.】的形式，再出现一遍【数字】的形式来构成ip
            String interfaceFtlRegex = "ip address" +
                    "\\s+((?:\\d{1,3}\\.){3}\\d{1,3})"+
                    "\\s+((?:\\d{1,3}\\.){3}\\d{1,3})";
            matcher = Pattern.compile(interfaceFtlRegex).matcher(line);
            //对于网卡接口来说，不同厂商都是一样的匹配模式
            if (matcher.find()){
                //排除环回地址，只要接口地址
                if (keyWord.startsWith("interface eth") ){
                    String name = keyWord.split("\\s+")[1];//获得接口名称
                    String ipAddress = matcher.group(1);//获得ip地址
                    Integer subnetMask = netmaskUtil.calculateNetmask(matcher.group(2));//获得子网掩码
                    //=============添加网卡接口信息============
                    NetInterfaces netInterfaces = new NetInterfaces(0, name, ipAddress, subnetMask, lxdName);
                    //生成ftl模板的网卡信息
                    netInterfacesList.add(netInterfaces);
                    //如果不存在相同名称的路由器，则存入
                    if(routersService.getByName(lxdName)==null){
                        //将网卡信息写入数据库
                        NetInterfaces saveNetInterfaces = netInterfacesService.save(netInterfaces);
                        //获得存储的网卡信息拼接id
                        interfacesId+=saveNetInterfaces.getId()+"_";
                    }
                }
            }

            //华为路由器和紫光路由器匹配isis协议
            //因为紫光出自华为，所以匹配规则几乎相同
            if ("HuaWei".equals(manufacture) || "UNIS".equals(manufacture)){

                //匹配网管信息中的容器名称，用来校验是否和文件名称中是否一样
                if(line.matches("(.)*sysname(.)*")){
                    //华为和紫光的匹配规则有所不同
                    if ("HuaWei".equals(manufacture)){
                        profileLxdName = line.split("\\s+")[1];
                    }else {
                        profileLxdName = line.split("\\s+")[2];
                    }

                    //如果名称不对应，则直接停止
                    if (!lxdName.equals(profileLxdName)){
                        writeLog.log("配置文件【"+file.getName()+"】中容器名称和文件名称不对应，请重新核验后导入");
                        break;
                    }
                }

                //匹配等级
                if (line.matches("(.)*is-level(.)*")){
                    String[] splitLevel = line.split("\\s+");
                    type = changeLevel(splitLevel[2]);
                }

                //匹配net号
                if (line.matches("(.)*network-entity(.)*")){
                    net = line.split("\\s+")[2];
                }

                //匹配协议生效网卡的名称
                if (line.matches("(.)*isis enable(.)*")){
                    if (keyWord.contains("interface eth")){
                        String name = keyWord.split("\\s+")[1];//获得接口名称
                        ethName+=name+";";
                    }
                }

            }else if ("ZTE".equals(manufacture)) {
                //中兴路由器

                //匹配网管信息中的容器名称，用来校验是否和文件名称中是否一样
                if(line.matches("(.)*hostname(.)*")){
                    profileLxdName = line.split("\\s+")[1];
                    //如果名称不对应，则直接停止
                    if (!lxdName.equals(profileLxdName)){
                        writeLog.log("配置文件【"+file.getName()+"】中容器名称和文件名称不对应，请重新核验后导入");
                        break;
                    }
                }


                //匹配等级
                if (line.matches("(.)*is-type(.)*")){
                    String[] splitLevel = line.split("\\s+");
                    type = changeLevel(splitLevel[2]);
                }

                //匹配net号
                //其中isis协议的net有匹配格式
                if (line.matches("(.)*system-id(.)*")){
                    net = line.split("\\s+")[2];
                    String netRegex = "[a-fA-F0-9]{2}(\\.[a-fA-F0-9]{4}){3,9}\\.[a-fA-F0-9]{2}";
                    if (!net.matches(netRegex)){
                        net = "10.7106."+net+".00";
                    }
                }

                //匹配协议生效网卡的名称
                if (line.matches("(.)*ip router isis(.)*")){
                    if (keyWord.contains("interface eth")){
                        String name = keyWord.split("\\s+")[2];//获得接口名称
                        ethName+=name+";";
                    }
                }
            }
        }

        //如果不存在相同名称的路由器，则存入
        if(routersService.getByName(lxdName)==null){
            //用于判断是否有isis协议
            dataMap.put("isisFlag",isisFlag);
            //说明配置文件中包含isis协议，添加协议相关属性
            if (isisFlag!=null && "yes".equals(isisFlag)){
                dataMap.put("flag",flag);
                dataMap.put("net",net);
                dataMap.put("ethName",ethName);
                dataMap.put("type",type);
                //将isis协议存入数据库
                ISIS isis = new ISIS(0, flag, type, net, lxdName, ethName);
                ISIS saveISIS = isisService.save(isis);
                //获取存储的isis协议信息
                isisId = saveISIS.getId();
            }

            //添加网卡接口
            if (netInterfacesList.size()>0){
                dataMap.put("netInterfacesList",netInterfacesList);
            }
            //添加容器名称
            dataMap.put("lxdName",lxdName);
            //添加厂商
            dataMap.put("manufacture",manufacture);
            //将路由器信息存入数据库
            Routers routers = new Routers(0, lxdName, interfacesId, isisId);
            routersService.save(routers);
            writeLog.log("名称为【"+lxdName+"】的容器已成功导入!");
            return dataMap;
        }else {
            //直接返回一个空的集合
            writeLog.log("名称为【"+lxdName+"】的容器已经存在，不能再次使用，请重命名后导入");
            return new HashMap<>();
        }
    }




    //根据网管信息中的等级转化为frr中的等级表示方式
    public String changeLevel(String level){
        if (level.equals("level-1")){
            return "level-1";
        }else if (level.equals("level-2") || level.equals("level-2-only")){
            return "level-2-only";
        } else{
            return "level-1-2";
        }
    }
}
