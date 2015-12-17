package com.bobpaulin.modularity.mod4;

import org.apache.commons.lang3.StringUtils;

public class Mod4 {
    public static void main(String[] args) {
        Mod4 module1 = new Mod4();
        module1.doSomthing(args[0]);
    }
    
    public void doSomthing(String name)
    {
        System.out.println(StringUtils.reverse(name));
    }
}