package com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate;

/**
 * @Author YZX
 * @Create 2023-06-06 10:18
 * @Java-version jdk1.8
 */
public class test {
    public static void main(String[] args) {
        String netRegex = "[a-fA-F0-9]{2}(\\.[a-fA-F0-9]{4}){3,9}\\.[a-fA-F0-9]{2}";
        System.out.println("1921.6801.5065".matches(netRegex));
        System.out.println("10.7307.1921.6801.5022.00".matches(netRegex));

    }
}
