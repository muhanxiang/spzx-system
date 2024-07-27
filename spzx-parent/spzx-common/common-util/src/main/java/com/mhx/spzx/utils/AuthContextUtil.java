package com.mhx.spzx.utils;

import com.mhx.spxz.model.entity.system.SysUser;

public class AuthContextUtil {
    private static  final ThreadLocal<SysUser> threadLocal=new ThreadLocal<>();
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }
    public static SysUser get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
