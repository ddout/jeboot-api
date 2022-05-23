package org.jeecg.modules.otheraccount.util.vpn;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class VpnApiUtil {

  private String baseUrl;
  private String token;

  public VpnApiUtil(String url) {
    this.baseUrl = url;
  }


  public static void main(String[] args) {

    VpnApiUtil b = new VpnApiUtil("https://192.168.11.210:8800").login("", "");
    System.out.println(b);
    VpnUser u = b.getVpnUser("test001");
    System.out.println(u);
    //
    b.createVpnUser("测试账号001", "test001","Test@0001", "test@qq.com");

  }


  public VpnApiUtil login(String username, String password) {
    //
    HashMap<String, Object> paramMap = new HashMap<>();
    paramMap.put("admin_user", username);
    paramMap.put("admin_pass", password);
    //
    String result = HttpRequest.post(this.baseUrl + "/base/login")
        .header(Header.CONTENT_TYPE, "application/x-www-form-urlencoded")
        .form(paramMap)
        .timeout(20000)//超时，毫秒
        .execute().body();
    //
//    System.out.println(result);
    JSONObject json = JSONObject.parseObject(result);
    if ("0".equals(json.getString("code"))) {
      JSONObject data = json.getJSONObject("data");
      token = data.getString("token");
      if (StringUtils.isEmpty(token)) {
        throw new RuntimeException("VPN管理员登录失败!" + json.getString("msg"));
      }
    }
    return this;
  }


  /**
   * 获取VPN用户
   */
  public VpnUser getVpnUser(String username) {
//
    String result = HttpRequest.get(this.baseUrl + "/user/list?page=1&prefix=" + username)
        .header("jwt", this.token)
        .timeout(20000)//超时，毫秒
        .execute().body();
    //
//    System.out.println(result);
    JSONObject json = JSONObject.parseObject(result);
    if ("0".equals(json.getString("code"))) {
      JSONObject data = json.getJSONObject("data");
//      System.out.println(data);
      if (data.getIntValue("count") > 0) {
        JSONArray arr = data.getJSONArray("datas");
        for (int i = 0; i < arr.size(); i++) {
          JSONObject res1 = arr.getJSONObject(i);
          if (username.toLowerCase().equals(res1.getString("username").toLowerCase())) {
            VpnUser vpnUser = JSON.parseObject(res1.toJSONString(), VpnUser.class);
            return vpnUser;
          }
        }

      }
    }
    return null;
  }


  /**
   * 创建vpn用户
   */
  public VpnApiUtil createVpnUser(String nickname, String username, String password, String email) {
    //
    JSONObject paramMap = new JSONObject();
    paramMap.put("send_email", false);
    paramMap.put("status", 1);
    paramMap.put("disable_otp", true);
    paramMap.put("groups", new String[]{"智审数据"});
    paramMap.put("username", username);
    paramMap.put("nickname", nickname);
    paramMap.put("email", email);
    paramMap.put("pin_code", password);
    //
    //{"send_email":false,"status":1,"groups":["智审数据"],"username":"test0001","nickname":"你好测试001","email":"test0001@qq.com","disable_otp":true,"pin_code":"Abc@123456"}
    //
    Map<String, String> headers = new HashMap<String, String>();
    headers.put("jwt", this.token);
    //
    String result = HttpRequest.post(this.baseUrl + "/user/set")
        .header(Header.CONTENT_TYPE, "application/json;charset=UTF-8")
        .addHeaders(headers)
        .body(paramMap.toJSONString())
        .timeout(20000)//超时，毫秒
        .execute().body();
//    System.out.println(result);
    JSONObject json = JSONObject.parseObject(result);
    if (!"0".equals(json.getString("code"))) {
      //{"code":0,"msg":"success","location":"/anylink/server/admin/api_user.go:111","data":null}
      throw new RuntimeException("VPN创建账号失败!" + json.getString("msg"));
    }
    return this;
  }


}
