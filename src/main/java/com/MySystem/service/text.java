package com.MySystem.service;

import java.util.ArrayList;
import java.util.List;

public class text {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.size(); j++) {
                if (true) {
                    System.out.println(list.get(j));
                    list.remove(j);
                }
            }
        }

    }
}
