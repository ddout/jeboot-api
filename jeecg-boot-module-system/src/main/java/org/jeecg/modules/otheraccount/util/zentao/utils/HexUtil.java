package org.jeecg.modules.otheraccount.util.zentao.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by xiaodizi on 2019/12/24.
 * 16进制字符串处理类，使用公司platform框架中的代码
 *
 * @author 狄雨晨
 */
public class HexUtil {
    private static final String HEX_CHARS = "0123456789abcdef";
    private static final int NUM_2 = 2;
    private static final int NUM_4 = 4;
    private static final int NUM_16 = 16;

    private HexUtil() {
        //静态类的私有构造方法
    }

    @NotNull
    public static String toHexString(@NotNull byte[] b) {
        StringBuilder sb = new StringBuilder();

        for (byte value : b) {
            sb.append(HEX_CHARS.charAt(value >>> 4 & 15));
            sb.append(HEX_CHARS.charAt(value & 15));
        }

        return sb.toString();
    }

    @NotNull
    public static byte[] toByteArray(@NotNull String s) {
        byte[] buf = new byte[s.length() / 2];
        int j = 0;

        for (int i = 0; i < buf.length; ++i) {
            buf[i] = (byte) (Character.digit(s.charAt(j++), 16) << 4 | Character.digit(s.charAt(j++), 16));
        }

        return buf;
    }
}
