package com.tms.utils;

import java.util.Date;

public class test {
    public static void main(String[] args) {
        Date date=new Date(System.currentTimeMillis());
        System.out.println(date);
        System.out.println(new Date().getTime());
    }
}
