package org.jeecg.modules.otheraccount.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class PinYinUtil {

  private static final HanyuPinyinOutputFormat format;

  static {
    format = new HanyuPinyinOutputFormat();
    format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    format.setVCharType(HanyuPinyinVCharType.WITH_V);
  }

  public static void main(String[] args) {
    String a = "你好互联网";

    String b = PinYinUtil.strToPinyin(a);

    System.out.println(b);

  }


  public static String strToPinyin(String str) {
    String out = "";
    if (StringUtils.isEmpty(str)) {
      return out;
    }

    char[] strArr = str.toCharArray();

    for(char c: strArr){
      if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
        try {
          String[] outStr = PinyinHelper.toHanyuPinyinStringArray(c, format);
          if(outStr != null && outStr.length>0){
            out += outStr[0];
          } else {
            out += c;
          }
        } catch (Exception e) {
          out += c;
        }
      } else {
        out += c;
      }
    }


    return out;
  }


}
