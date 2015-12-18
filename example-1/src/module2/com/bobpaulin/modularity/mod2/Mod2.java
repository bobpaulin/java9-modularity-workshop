package com.bobpaulin.modularity.mod2;

import com.bobpaulin.modularity.mod1.AwesomeService;
import com.bobpaulin.modularity.mod2.internal.AwesomeImpl;

public class Mod2 {
    public static void main(String[] args) {
        AwesomeService service = new AwesomeImpl();
        System.out.println(service.saySomethingAwesome());
    }
}