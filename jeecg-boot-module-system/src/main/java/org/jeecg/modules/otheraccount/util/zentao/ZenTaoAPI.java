package org.jeecg.modules.otheraccount.util.zentao;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jeecg.modules.otheraccount.util.zentao.proxy.Constant;

public class ZenTaoAPI {

  private static final String admin_account = "";
  private static final String admin_psw = "";
  private static final String default_dept = "18";
  private static final String default_psw = "Abc@123456";
  private static final String default_role = "dev";
  private static final String default_group = "24";



  public static void main(String[] args) {
    System.out.println(createZentaoUser("test0010", "测试创建账号-001"));
  }

  /**
   * ⽤来存取cookies信息的变量.
   */
  private static CookieStore cookieStore;
  private static String jsScript = "/*!\n"
      + " * Joseph Myer's md5() algorithm wrapped in a self-invoked function to prevent\n"
      + " * global namespace polution, modified to hash unicode characters as UTF-8.\n"
      + " *  \n"
      + " * Copyright 1999-2010, Joseph Myers, Paul Johnston, Greg Holt, Will Bond <will@wbond.net>\n"
      + " * http://www.myersdaily.org/joseph/javascript/md5-text.html\n"
      + " * http://pajhome.org.uk/crypt/md5\n"
      + " * \n"
      + " * Released under the BSD license\n"
      + " * http://www.opensource.org/licenses/bsd-license\n"
      + " */\n"
      + "function md5cycle(x, k) {\n"
      + "var a = x[0], b = x[1], c = x[2], d = x[3];\n"
      + "\n"
      + "a = ff(a, b, c, d, k[0], 7, -680876936);\n"
      + "d = ff(d, a, b, c, k[1], 12, -389564586);\n"
      + "c = ff(c, d, a, b, k[2], 17,  606105819);\n"
      + "b = ff(b, c, d, a, k[3], 22, -1044525330);\n"
      + "a = ff(a, b, c, d, k[4], 7, -176418897);\n"
      + "d = ff(d, a, b, c, k[5], 12,  1200080426);\n"
      + "c = ff(c, d, a, b, k[6], 17, -1473231341);\n"
      + "b = ff(b, c, d, a, k[7], 22, -45705983);\n"
      + "a = ff(a, b, c, d, k[8], 7,  1770035416);\n"
      + "d = ff(d, a, b, c, k[9], 12, -1958414417);\n"
      + "c = ff(c, d, a, b, k[10], 17, -42063);\n"
      + "b = ff(b, c, d, a, k[11], 22, -1990404162);\n"
      + "a = ff(a, b, c, d, k[12], 7,  1804603682);\n"
      + "d = ff(d, a, b, c, k[13], 12, -40341101);\n"
      + "c = ff(c, d, a, b, k[14], 17, -1502002290);\n"
      + "b = ff(b, c, d, a, k[15], 22,  1236535329);\n"
      + "\n"
      + "a = gg(a, b, c, d, k[1], 5, -165796510);\n"
      + "d = gg(d, a, b, c, k[6], 9, -1069501632);\n"
      + "c = gg(c, d, a, b, k[11], 14,  643717713);\n"
      + "b = gg(b, c, d, a, k[0], 20, -373897302);\n"
      + "a = gg(a, b, c, d, k[5], 5, -701558691);\n"
      + "d = gg(d, a, b, c, k[10], 9,  38016083);\n"
      + "c = gg(c, d, a, b, k[15], 14, -660478335);\n"
      + "b = gg(b, c, d, a, k[4], 20, -405537848);\n"
      + "a = gg(a, b, c, d, k[9], 5,  568446438);\n"
      + "d = gg(d, a, b, c, k[14], 9, -1019803690);\n"
      + "c = gg(c, d, a, b, k[3], 14, -187363961);\n"
      + "b = gg(b, c, d, a, k[8], 20,  1163531501);\n"
      + "a = gg(a, b, c, d, k[13], 5, -1444681467);\n"
      + "d = gg(d, a, b, c, k[2], 9, -51403784);\n"
      + "c = gg(c, d, a, b, k[7], 14,  1735328473);\n"
      + "b = gg(b, c, d, a, k[12], 20, -1926607734);\n"
      + "\n"
      + "a = hh(a, b, c, d, k[5], 4, -378558);\n"
      + "d = hh(d, a, b, c, k[8], 11, -2022574463);\n"
      + "c = hh(c, d, a, b, k[11], 16,  1839030562);\n"
      + "b = hh(b, c, d, a, k[14], 23, -35309556);\n"
      + "a = hh(a, b, c, d, k[1], 4, -1530992060);\n"
      + "d = hh(d, a, b, c, k[4], 11,  1272893353);\n"
      + "c = hh(c, d, a, b, k[7], 16, -155497632);\n"
      + "b = hh(b, c, d, a, k[10], 23, -1094730640);\n"
      + "a = hh(a, b, c, d, k[13], 4,  681279174);\n"
      + "d = hh(d, a, b, c, k[0], 11, -358537222);\n"
      + "c = hh(c, d, a, b, k[3], 16, -722521979);\n"
      + "b = hh(b, c, d, a, k[6], 23,  76029189);\n"
      + "a = hh(a, b, c, d, k[9], 4, -640364487);\n"
      + "d = hh(d, a, b, c, k[12], 11, -421815835);\n"
      + "c = hh(c, d, a, b, k[15], 16,  530742520);\n"
      + "b = hh(b, c, d, a, k[2], 23, -995338651);\n"
      + "\n"
      + "a = ii(a, b, c, d, k[0], 6, -198630844);\n"
      + "d = ii(d, a, b, c, k[7], 10,  1126891415);\n"
      + "c = ii(c, d, a, b, k[14], 15, -1416354905);\n"
      + "b = ii(b, c, d, a, k[5], 21, -57434055);\n"
      + "a = ii(a, b, c, d, k[12], 6,  1700485571);\n"
      + "d = ii(d, a, b, c, k[3], 10, -1894986606);\n"
      + "c = ii(c, d, a, b, k[10], 15, -1051523);\n"
      + "b = ii(b, c, d, a, k[1], 21, -2054922799);\n"
      + "a = ii(a, b, c, d, k[8], 6,  1873313359);\n"
      + "d = ii(d, a, b, c, k[15], 10, -30611744);\n"
      + "c = ii(c, d, a, b, k[6], 15, -1560198380);\n"
      + "b = ii(b, c, d, a, k[13], 21,  1309151649);\n"
      + "a = ii(a, b, c, d, k[4], 6, -145523070);\n"
      + "d = ii(d, a, b, c, k[11], 10, -1120210379);\n"
      + "c = ii(c, d, a, b, k[2], 15,  718787259);\n"
      + "b = ii(b, c, d, a, k[9], 21, -343485551);\n"
      + "\n"
      + "x[0] = add32(a, x[0]);\n"
      + "x[1] = add32(b, x[1]);\n"
      + "x[2] = add32(c, x[2]);\n"
      + "x[3] = add32(d, x[3]);\n"
      + "\n"
      + "}\n"
      + "\n"
      + "function cmn(q, a, b, x, s, t) {\n"
      + "a = add32(add32(a, q), add32(x, t));\n"
      + "return add32((a << s) | (a >>> (32 - s)), b);\n"
      + "}\n"
      + "\n"
      + "function ff(a, b, c, d, x, s, t) {\n"
      + "return cmn((b & c) | ((~b) & d), a, b, x, s, t);\n"
      + "}\n"
      + "\n"
      + "function gg(a, b, c, d, x, s, t) {\n"
      + "return cmn((b & d) | (c & (~d)), a, b, x, s, t);\n"
      + "}\n"
      + "\n"
      + "function hh(a, b, c, d, x, s, t) {\n"
      + "return cmn(b ^ c ^ d, a, b, x, s, t);\n"
      + "}\n"
      + "\n"
      + "function ii(a, b, c, d, x, s, t) {\n"
      + "return cmn(c ^ (b | (~d)), a, b, x, s, t);\n"
      + "}\n"
      + "\n"
      + "function md51(s) {\n"
      + "txt = '';\n"
      + "var n = s.length,\n"
      + "state = [1732584193, -271733879, -1732584194, 271733878], i;\n"
      + "for (i=64; i<=s.length; i+=64) {\n"
      + "md5cycle(state, md5blk(s.substring(i-64, i)));\n"
      + "}\n"
      + "s = s.substring(i-64);\n"
      + "var tail = [0,0,0,0, 0,0,0,0, 0,0,0,0, 0,0,0,0];\n"
      + "for (i=0; i<s.length; i++)\n"
      + "tail[i>>2] |= s.charCodeAt(i) << ((i%4) << 3);\n"
      + "tail[i>>2] |= 0x80 << ((i%4) << 3);\n"
      + "if (i > 55) {\n"
      + "md5cycle(state, tail);\n"
      + "for (i=0; i<16; i++) tail[i] = 0;\n"
      + "}\n"
      + "tail[14] = n*8;\n"
      + "md5cycle(state, tail);\n"
      + "return state;\n"
      + "}\n"
      + "\n"
      + "/* there needs to be support for Unicode here,\n"
      + " * unless we pretend that we can redefine the MD-5\n"
      + " * algorithm for multi-byte characters (perhaps\n"
      + " * by adding every four 16-bit characters and\n"
      + " * shortening the sum to 32 bits). Otherwise\n"
      + " * I suggest performing MD-5 as if every character\n"
      + " * was two bytes--e.g., 0040 0025 = @%--but then\n"
      + " * how will an ordinary MD-5 sum be matched?\n"
      + " * There is no way to standardize text to something\n"
      + " * like UTF-8 before transformation; speed cost is\n"
      + " * utterly prohibitive. The JavaScript standard\n"
      + " * itself needs to look at this: it should start\n"
      + " * providing access to strings as preformed UTF-8\n"
      + " * 8-bit unsigned value arrays.\n"
      + " */\n"
      + "function md5blk(s) { /* I figured global was faster.   */\n"
      + "var md5blks = [], i; /* Andy King said do it this way. */\n"
      + "for (i=0; i<64; i+=4) {\n"
      + "md5blks[i>>2] = s.charCodeAt(i)\n"
      + "+ (s.charCodeAt(i+1) << 8)\n"
      + "+ (s.charCodeAt(i+2) << 16)\n"
      + "+ (s.charCodeAt(i+3) << 24);\n"
      + "}\n"
      + "return md5blks;\n"
      + "}\n"
      + "\n"
      + "var hex_chr = '0123456789abcdef'.split('');\n"
      + "\n"
      + "function rhex(n)\n"
      + "{\n"
      + "var s='', j=0;\n"
      + "for(; j<4; j++)\n"
      + "s += hex_chr[(n >> (j * 8 + 4)) & 0x0F]\n"
      + "+ hex_chr[(n >> (j * 8)) & 0x0F];\n"
      + "return s;\n"
      + "}\n"
      + "\n"
      + "function hex(x) {\n"
      + "for (var i=0; i<x.length; i++)\n"
      + "x[i] = rhex(x[i]);\n"
      + "return x.join('');\n"
      + "}\n"
      + "\n"
      + "function md5(s) {\n"
      + "return hex(md51(s));\n"
      + "}\n"
      + "\n"
      + "/* this function is much faster,\n"
      + "so if possible we use it. Some IEs\n"
      + "are the only ones I know of that\n"
      + "need the idiotic second function,\n"
      + "generated by an if clause.  */\n"
      + "\n"
      + "function add32(a, b) {\n"
      + "return (a + b) & 0xFFFFFFFF;\n"
      + "}\n"
      + "\n"
      + "if (md5('hello') != '5d41402abc4b2a76b9719d911017c592') {\n"
      + "function add32(x, y) {\n"
      + "var lsw = (x & 0xFFFF) + (y & 0xFFFF),\n"
      + "msw = (x >> 16) + (y >> 16) + (lsw >> 16);\n"
      + "return (msw << 16) | (lsw & 0xFFFF);\n"
      + "}\n"
      + "}\n";

  public static String getHtml(String url) {
    URL URL = null;
    HttpURLConnection conn = null;
    InputStream in = null;
    BufferedReader br = null;
    String html = null;
    try {
      URL = new URL(url);
      conn = (HttpURLConnection) URL.openConnection();
      conn.setRequestMethod("GET");
      String code = getCode(conn.getContentType());
      System.out.println(code);
      in = conn.getInputStream();
      br = new BufferedReader(new InputStreamReader(in, code));
      // 得到HTML⽂档
      String data = "";
      while ((data = br.readLine()) != null) {
        html += data + "\n";
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      try {
        br.close();
        in.close();
        conn.disconnect();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    return html;
  }


  private static String getCode(String contentType) {
    int star = contentType.indexOf("charset=");
    return contentType.substring(star + 8);
  }


  public static String createZentaoUser(String account,String realname){
    String session = doGet(Constant.ZENTAO_ADDRESS + "/api-getsessionid.json", null, false);
    JsonParser parse = new JsonParser();
    JsonObject jsonSession = (JsonObject) parse.parse(session);
    System.out.println("Session: " + jsonSession);
    JsonObject jsonObj = (JsonObject) parse
        .parse(jsonSession.get("data").getAsJsonPrimitive().getAsString());
    String sessionID = jsonObj.get("sessionID").toString();
    System.out.println("sessionID: " + jsonObj.get("sessionID"));
    Map<String, String> map = new HashMap<String, String>();
    map.put("account", admin_account);
    map.put("password", admin_psw);
    map.put("zentaosid", sessionID);
    String login = doGet(Constant.ZENTAO_ADDRESS + "/user-login.json", map, true);
    System.out.println("login :" + login);
    String user = doGet(Constant.ZENTAO_ADDRESS + "/user-view-admin.json", null, true);
    System.out.println("user :" + user);
    System.out.println("userLength : " + user.length());
    String rand = getHtmlRand(Constant.ZENTAO_ADDRESS + "/user-create-0.html");
// System.out.println(html);
// http://127.0.0.1/zentao/js/md5.js?v=12.3
//    jsScript = doGet(Constant.ZENTAO_ADDRESS + "/js/md5.js?v=12.5.3", null, true);
// System.out.println(js);
    Map<String, String> params = new HashMap<String, String>();
    params.put("dept", default_dept);
    params.put("account", account);
    params.put("password1", getJsMd5(default_psw) + rand);
    params.put("password2", getJsMd5(default_psw) + rand);
    try{
      params.put("realname", realname);
    } catch (Exception e) {
      params.put("realname", account);
    }
    params.put("join", new SimpleDateFormat("YYYY-MM-dd").format(new Date()));
    params.put("role", default_role);
    params.put("group", default_group);
    params.put("email", "123@qq.com");
    params.put("commiter", "");
    params.put("gender", "m");
    params.put("verifyPassword", getJsMd5(getJsMd5(admin_psw) + rand));
    params.put("passwordStrength", "1");
    System.out.println(params);
    String result = doPost(Constant.ZENTAO_ADDRESS + "/user-create-0.json", params, true);
    System.out.println(result);

    return result;
  }





  /*
   * @date: 2020年4⽉30⽇下午5:1208
   * @author: 宋
   * 向指定url发起请求，可携带参数，并选择是否携带cookie
   */
  public static String doGet(String url, Map<String, String> params, boolean isCookie) {
//获取httpclient客户端
    CloseableHttpClient httpclient = HttpClients.createDefault();
    String resultString = "";
    CloseableHttpResponse response = null;
    try {
      URIBuilder builder = new URIBuilder(url);
      if (null != params) {
        for (String key : params.keySet()) {
          builder.setParameter(key, params.get(key));
        }
      }
      HttpGet get = new HttpGet(builder.build());
      if (!isCookie) {
        response = httpclient.execute(get);
        System.out.println(response.getStatusLine());
        if (200 == response.getStatusLine().getStatusCode()) {
          HttpEntity entity = response.getEntity();
          resultString = EntityUtils.toString(entity, "utf-8");
        }
      } else {
        resultString = GetCookies(get);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != response) {
        try {
          response.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (null != httpclient) {
        try {
          httpclient.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return resultString;
  }


  public static String getHtmlRand(String url) {
    String result = doGetHtml(url, true);
    int star = result.indexOf("<input type='hidden' name='verifyRand' id='verifyRand' value='");
    int end = result.indexOf("' />", star);
    System.out.println("start: " + star);
    System.out.println("end: " + end);
    String verifyRand = result.substring(star + 62, star + 72);
    verifyRand = verifyRand.replaceAll("'", "");
    System.out.println("verifyRand: " + verifyRand);
    System.out.println("verifyRandlength: " + verifyRand.length());
    return verifyRand;
  }


  public static String getJsMd5(String pwd) {
    Object invoke = null;
    try {
      ScriptEngineManager manager = new ScriptEngineManager();
      ScriptEngine engine = manager.getEngineByName("javascript");
      engine.eval(jsScript);
      Invocable in = (Invocable) engine;
      invoke = in.invokeFunction("md5", pwd);
    } catch (Exception e) {
// TODO: handle exception
      e.printStackTrace();
    }
    System.out.println(invoke);
    return invoke.toString();
  }

  public static String doPost(String url, Map<String, String> params, boolean isCookie){
    return doPost(url, params, isCookie, "UTF-8");
  }

  public static String doPost(String url, Map<String, String> params, boolean isCookie, String encode) {
/**
 * 在4.0及以上httpclient版本中，post需要指定重定向的策略，如果不指定则按默认的重定向策略。
 *
 * 获取httpclient客户端
 */
    CloseableHttpClient httpclient = HttpClientBuilder.create()
        .setRedirectStrategy(new LaxRedirectStrategy()).build();
    String resultString = "";
    CloseableHttpResponse response = null;
    try {
      HttpPost post = new HttpPost(url);
      List<NameValuePair> paramaters = new ArrayList<NameValuePair>();
      if (null != params) {
        for (String key : params.keySet()) {
          paramaters.add(new BasicNameValuePair(key, params.get(key)));
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramaters,encode);
        post.setEntity(formEntity);
      }
    /**
     * HTTP/1.1 403 Forbidden
     * 原因：
     * 有些⽹站，设置了反爬⾍机制
     * 解决的办法：
     * 设置请求头，伪装浏览器
     */
      post.addHeader("user-agent",
          "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
      post.addHeader("Accept-Language",
          "zh-CN,zh;q=0.9");
      post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
      if (!isCookie) {
        response = httpclient.execute(post);
        System.out.println(response.getStatusLine());
        if (200 == response.getStatusLine().getStatusCode()) {
          HttpEntity entity = response.getEntity();
          resultString = EntityUtils.toString(entity, encode);
        }
      } else {
        resultString = PostCookies(post);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != response) {
        try {
          response.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (null != httpclient) {
        try {
          httpclient.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return resultString;
  }


  /*
   * @date: 2020年4⽉30⽇下午5:0957
   * @author: 宋
   * 获取登录过后的cookie 存⼊ cookieStore对象
   */
  public static String GetCookies(HttpGet get) throws IOException {
    String result = null;
    try {
      if (null == cookieStore) {
        cookieStore = new BasicCookieStore();
      }
// 获取 响应
      CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
          .build();
      CloseableHttpResponse response = httpClient.execute(get);
      result = EntityUtils.toString(response.getEntity(), "utf-8");
// // 获取cookies信息
// List<Cookie> cookies = cookieStore.getCookies();
// for (Cookie cookie : cookies) {
// String name = cookie.getName();
// String value = cookie.getValue();
// System.out.println("cookies: key= "+ name + " value= " + value);
// }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }


  /*
   * @date: 2020年4⽉30⽇下午5:1544
   * @author: 宋
   * 获取html⻚⾯，并选择是否携带cookie发出请求
   */
  public static String doGetHtml(String url, boolean isCookie) {
//获取httpclient客户端
    CloseableHttpClient httpclient = HttpClients.createDefault();
    String resultString = "";
    CloseableHttpResponse response = null;
    try {
      HttpGet get = new HttpGet(url);
      if (!isCookie) {
        response = httpclient.execute(get);
        System.out.println(response.getStatusLine());
        if (200 == response.getStatusLine().getStatusCode()) {
          HttpEntity entity = response.getEntity();
          resultString = EntityUtils.toString(entity, "utf-8");
        }
      } else {
        resultString = GetCookies(get);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (null != response) {
        try {
          response.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (null != httpclient) {
        try {
          httpclient.close();
        } catch (IOException e) {
// TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    return resultString;
  }


  public static String PostCookies(HttpPost Post) throws IOException {
    String result = null;
    try {
      if (null == cookieStore) {
        cookieStore = new BasicCookieStore();
      }
// 获取 响应
      CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
          .build();
      CloseableHttpResponse response = httpClient.execute(Post);
      result = EntityUtils.toString(response.getEntity(), "utf-8");
// // 获取cookies信息
// List<Cookie> cookies = cookieStore.getCookies();
// for (Cookie cookie : cookies) {
// String name = cookie.getName();
// String value = cookie.getValue();
// System.out.println("cookies: key= "+ name + " value= " + value);
// }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }


  /**
   * 字符串转换unicode
   * @param string
   * @return
   */
  public static String string2Unicode(String string) {
    StringBuffer unicode = new StringBuffer();
    for (int i = 0; i < string.length(); i++) {
      // 取出每一个字符
      char c = string.charAt(i);
      // 转换为unicode
      unicode.append("\\u" + Integer.toHexString(c));
    }

    return unicode.toString();
  }

}
