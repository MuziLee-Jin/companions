package com.example.usercenter.utils;

import java.time.Instant;

/**
 * @author lijin
 * 计算每个模块的运行时间，多埋点，多记录。
 */
public class RunningTimeCal {
    private static final ThreadLocal<Long> threadLocal = new InheritableThreadLocal<>();

    public static void start(){
        Long curTime = threadLocal.get();
        if(curTime == null){
            threadLocal.set(Long.valueOf(Instant.now().toEpochMilli()));
        }
    }

    public static long get(){
        if(threadLocal.get() == null)
        {
            return 0;
        }
        long newTime =  Instant.now().toEpochMilli();
        return newTime - threadLocal.get();
    }
    public static void remove(){
        threadLocal.remove();
    }


}
