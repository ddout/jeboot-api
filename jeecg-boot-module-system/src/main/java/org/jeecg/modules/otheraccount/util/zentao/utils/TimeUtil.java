package org.jeecg.modules.otheraccount.util.zentao.utils;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaodizi on 2019/2/19.
 * 时间帮助函数
 *
 * @author 狄雨晨
 */
public class TimeUtil {
    private TimeUtil() {
        //静态类的私有构造方法
    }

    /**
     * 获取当前日期时间
     *
     * @return String 日期字符串 示例2020-01-07
     */
    @NotNull
    public static String getNowTime() {
        Date utilDate = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(utilDate);
    }

    /**
     * 获取当前时间毫秒对应的UnixTimeStamp
     *
     * @return String 当前时间毫秒之的UnixTimeStamp
     */
    @NotNull
    public static String getNowTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }


}
