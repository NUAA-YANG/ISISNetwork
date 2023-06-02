package com.nuaa.isisnetwork.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @Author YZX
 * @Create 2023-06-02 10:08
 * @Java-version jdk1.8
 */
@Service
@Component
public class NetmaskUtil {

    public static void main(String[] args) {
        NetmaskUtil netmaskUtil = new NetmaskUtil();
        System.out.println(netmaskUtil.tenToTwo("192.168.31.104"));
        System.out.println(netmaskUtil.calculateNetmask("255.255.255.240"));
        System.out.println(netmaskUtil.calculateAclMask("0.0.0.255"));
    }


    //计算acl的掩码
    public Integer calculateAclMask(String ip){
        //获得二进制ip
        String changeIp = tenToTwo(ip);
        return calculateOne(changeIp,'0');
    }

    //计算十进制ip的掩码
    public Integer calculateNetmask(String ip){
        //获得二进制ip
        String changeIp = tenToTwo(ip);
        return calculateOne(changeIp,'1');
    }

    //将十进制的ip转换为二进制的ip
    public String tenToTwo(String ip){
        String changeIp = "";
        //基于.进行拆分【如255.255.255.253，拆分为255 255 255 253】
        String[] splitIp = ip.split("\\.");
        for (int i = 0; i<splitIp.length;i++){
            //将每一个拆分出来的数字转化为二进制
            String binaryMask = Integer.toBinaryString(Integer.parseInt(splitIp[i]));
            //不满足八位则补零
            if (binaryMask.length()<8){
                binaryMask = String.format("%08d",Integer.parseInt(binaryMask));
            }
            //补满ip
            changeIp+=binaryMask;
            //如果不是最后一位，则添加“.”来区分地址
            if (i!=splitIp.length-1){
                changeIp+=".";
            }
        }
        return changeIp;
    }


    /**
     * @description  计算字符串中有多少个连续的 1或 0
     * @date 2023/6/2 10:54
     * @params [str：传入的字符串, c：要计算的连续的字符]
     * @returns int
     */
    public int calculateOne(String str,char c){
        int result = 0 ;
        for (int i = 0 ;i<str.length();i++){
            if (str.charAt(i)==c){
                result++;
            }else if (str.charAt(i) == '.'){
                continue;
            }else {
                break;
            }
        }
        return result;
    }

}
