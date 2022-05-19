package org.jeecg.modules.otheraccount.util.zentao.proxy;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jeecg.modules.otheraccount.util.zentao.proxy.application.ApiAdapter;

/**
 * 禅道登录鉴权类
 * <p>禅道请求有两种Api,登录请求走带session的,其他获取数据请求走第三方应用</p>
 *
 * @author xiaodizi
 */
public class Authentication {

    /**
     * 获取请求的session
     *
     * @return 请求禅道的session
     */
    private String getSession() {
        String getSessionIdUrl = "/api-getsessionid.json";
        return Proxy.get(getSessionIdUrl).getString("sessionID");
    }

    /**
     * 使用用户名密码登录禅道
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否登录成功
     */
    public boolean login(String username, String password) {
        String session = getSession();
        String loginUrl = "/user-login.json?zentaosid=" + session;

        Map<String, String> loginDataMap = new HashMap<>(2);
        loginDataMap.put(Constant.ZENTAO_USERNAME, username);
        loginDataMap.put(Constant.ZENTAO_PASSWORD, password);

        JSONObject loginReturnJsonData = Proxy.post(loginUrl, loginDataMap, Constant.ZENTAO_USER_NODE);
        return !loginReturnJsonData.isEmpty() && loginReturnJsonData.getString(Constant.ZENTAO_USERNAME).equals(username);
    }

    /**
     * 根据用户账户获取用户详细信息
     *
     * @param username 用户名
     * @return JSON数据
     */
    public JSONObject getUserDetail(String username) {
        Map<String, String> getUserDetailRequestMap = new LinkedHashMap<>(5);
        getUserDetailRequestMap.put(ApiAdapter.ZENTAO_MODULE_TAG, "portal");
        getUserDetailRequestMap.put(ApiAdapter.ZENTAO_FUNCTION_TAG, "getAllDataByAccount");
        getUserDetailRequestMap.put(Constant.ZENTAO_USERNAME, username);
        return ApiAdapter.get(getUserDetailRequestMap);
    }
}
