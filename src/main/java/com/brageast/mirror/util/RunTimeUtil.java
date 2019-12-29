package com.brageast.mirror.util;

import com.brageast.mirror.function.SimpleFunction;

/**
 * 运行代码测试时间工具
 *
 */
public class RunTimeUtil {

    public static long test(SimpleFunction simpleFunction) {
        long startTime=System.currentTimeMillis();   //获取开始时间
        simpleFunction.run();
        long endTime=System.currentTimeMillis(); //获取结束时间
        return endTime - startTime;
    }
}
