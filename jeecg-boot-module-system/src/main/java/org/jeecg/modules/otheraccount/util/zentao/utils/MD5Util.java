package org.jeecg.modules.otheraccount.util.zentao.utils;

import org.jetbrains.annotations.NotNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xiaodizi on 2019/12/24.
 * md5摘要计算处理类，使用公司platform框架中的代码
 *
 * @author 狄雨晨
 */
public class MD5Util {

    private MD5Util() {
        //静态类的私有构造方法
    }

    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var1) {
            throw new SecurityException(var1);
        }
    }

    public static byte[] md5(byte[] data) {
        return getDigest().digest(data);
    }

    public static byte[] md5(@NotNull String data) {
        return md5(data.getBytes());
    }

    @NotNull
    public static String md5Hex(byte[] data) {
        return HexUtil.toHexString(md5(data));
    }

    @NotNull
    public static String md5Hex(String data) {
        return HexUtil.toHexString(md5(data));
    }
}
