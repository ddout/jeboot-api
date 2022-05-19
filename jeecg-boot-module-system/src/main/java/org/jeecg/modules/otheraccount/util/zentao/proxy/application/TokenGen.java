package org.jeecg.modules.otheraccount.util.zentao.proxy.application;

import org.jeecg.modules.otheraccount.util.zentao.proxy.Constant;
import org.jeecg.modules.otheraccount.util.zentao.utils.MD5Util;
import org.jeecg.modules.otheraccount.util.zentao.utils.TimeUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Created by xiaodizi on 2019/11/21.
 * 禅道Token计算类
 *
 * @author 狄雨晨
 */
public class TokenGen {

    private TokenGen() {
        //静态类的私有构造方法
        throw new IllegalStateException("ApiAdapter不能实例化");
    }

    /**
     * 获取禅道访问的token
     *
     * @return String token
     */
    @NotNull
    public static String getToken() {
        String time = TimeUtil.getNowTimestamp();
        return getToken(time);
    }

    /**
     * 获取禅道访问的token，使用传入的时间戳字符串
     *
     * @param timeStamp 时间戳字符串
     * @return String token
     */
    @NotNull
    public static String getToken(String timeStamp) {
        return MD5Util.md5Hex(Constant.ZENTAO_APP_CODE + Constant.ZENTAO_APP_KEY + timeStamp);
    }
}
