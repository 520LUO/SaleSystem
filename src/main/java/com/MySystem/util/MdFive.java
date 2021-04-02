package com.MySystem.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

/**
 * 密码加密的工具类
 */

@Component//将MD5对象的创建交给Spring，采取注入的方式
public class MdFive {
    /**
     *
     * @param password 要加密的密码
     * @param saltValue 盐值，一般是用户名为盐值
     * @return
     */
    public String encrypt(String password,String saltValue){

        Object salt = new Md5Hash(saltValue);

        Object result = new SimpleHash("MD5", password, salt, 1024);

        return result+"";
    }

    public static void main(String[] args) {

        MdFive md=new MdFive();
        String pd =md.encrypt("123","admin");
        System.out.println(pd);
    }

}

