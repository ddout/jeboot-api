package org.jeecg.modules.otheraccount.util.zentao.proxy.application;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.modules.otheraccount.util.zentao.proxy.Constant;
import org.jeecg.modules.otheraccount.util.zentao.proxy.Proxy;
import org.jeecg.modules.otheraccount.util.zentao.utils.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * 禅道接口适配器
 * <p>禅道请求有两种Api,登录请求走带session的,其他获取数据请求走第三方应用</p>
 *
 * @author xiaodizi
 */
public class ApiAdapter {

    public static final String ZENTAO_MODULE_TAG = "m";

    public static final String ZENTAO_FUNCTION_TAG = "f";

    private ApiAdapter() {
        throw new IllegalStateException("ApiAdapter不能实例化");
    }

    /**
     * 使用禅道第三方应用接口的get请求
     * 参数中包含了指定的模块和方法,不需要额外指定url
     *
     * @param paramMap 请求参数
     * @return JSONObject返回数据
     */
    public static JSONObject get(@NotNull Map<String, String> paramMap) {

        //检查是否包含请求的参数
        assert paramMap.containsKey(ZENTAO_MODULE_TAG);//必需有m参数标识请求的模块
        assert paramMap.containsKey(ZENTAO_FUNCTION_TAG);//必需有f参数标识请求的方法


        //检查是否包含请求的参数
        if (!paramMap.containsKey(ZENTAO_MODULE_TAG)) {
            return new JSONObject();
        }
        if (!paramMap.containsKey(ZENTAO_FUNCTION_TAG)) {
            return new JSONObject();
        }

        //本次禅道请求需要的时间戳
        final String nowTimeStamp = TimeUtil.getNowTimestamp();

        //补充请求禅道需要的后续字段
        paramMap.put("code", Constant.ZENTAO_APP_CODE);
        paramMap.put("time", nowTimeStamp);
        paramMap.put("token", TokenGen.getToken(nowTimeStamp));
        return Proxy.get("/api.php", paramMap);
    }
}
