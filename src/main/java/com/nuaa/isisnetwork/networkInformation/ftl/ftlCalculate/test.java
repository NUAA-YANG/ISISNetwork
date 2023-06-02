package com.nuaa.isisnetwork.networkInformation.ftl.ftlCalculate;

import java.util.Arrays;

/**
 * @Author YZX
 * @Create 2023-06-02 11:25
 * @Java-version jdk1.8
 */
public class test {
    public static void main(String[] args) {
        String[] s = " is-level level-2".split(" ");
        System.out.println(Arrays.toString(s));

        System.out.println("  interface eth1".contains("interface"));
    }
}
