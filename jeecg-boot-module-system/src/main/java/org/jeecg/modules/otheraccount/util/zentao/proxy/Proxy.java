package org.jeecg.modules.otheraccount.util.zentao.proxy;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaodizi
 */
public class Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Proxy.class);

    private Proxy() {
        LOGGER.warn("ZentaoProxy是工具类,不能实例化");
        throw new IllegalStateException("ZentaoProxy是工具类,不能实例化");
    }

    /**
     * 无参数get请求
     *
     * @param url 请求地址
     * @return 请求响应JSONObject
     */
    public static JSONObject get(final String url) {
        try {
            URIBuilder uriBuilder = new URIBuilder(Constant.ZENTAO_ADDRESS + url);
            return get(uriBuilder, Constant.ZENTAO_DATA_NODE);
        } catch (URISyntaxException e) {
            LOGGER.error("url格式错误,{}", e.getMessage());
        }
        return new JSONObject();
    }

    /**
     * 无参数get请求，指定DataNodeKey来获取返回json中的特定结构
     *
     * @param url 请求地址
     * @return 请求响应JSONObject
     */
    public static JSONObject get(final String url, final String dataNodeKey) {
        try {
            URIBuilder uriBuilder = new URIBuilder(Constant.ZENTAO_ADDRESS + url);
            return get(uriBuilder, dataNodeKey);
        } catch (URISyntaxException e) {
            LOGGER.error("url格式错误,{}", e.getMessage());
        }
        return new JSONObject();
    }

    /**
     * 带参数get请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求响应JSONObject
     */
    public static JSONObject get(final String url, @NotNull final Map<String, String> paramMap) {
        try {
            URIBuilder uriBuilder = new URIBuilder(Constant.ZENTAO_ADDRESS + url);
            for (Map.Entry<String, String> parameterStringMapEntry : paramMap.entrySet()) {
                //循环遍历添加字段
                uriBuilder.setParameter(parameterStringMapEntry.getKey(), parameterStringMapEntry.getValue());
            }
            return get(uriBuilder, Constant.ZENTAO_DATA_NODE);
        } catch (URISyntaxException e) {
            LOGGER.error("url格式错误,{}", e.getMessage());
        }
        return new JSONObject();
    }

    /**
     * 带参数get请求
     *
     * @param url         请求地址
     * @param paramMap    请求参数
     * @param dataNodeKey 数据节点的键
     * @return 请求响应JSONObject
     */
    public JSONObject get(final String url, @NotNull final Map<String, String> paramMap, final String dataNodeKey) {
        try {
            URIBuilder uriBuilder = new URIBuilder(Constant.ZENTAO_ADDRESS + url);
            for (Map.Entry<String, String> parameterStringMapEntry : paramMap.entrySet()) {
                //循环遍历添加字段
                uriBuilder.setParameter(parameterStringMapEntry.getKey(), parameterStringMapEntry.getValue());
            }
            return get(uriBuilder, dataNodeKey);
        } catch (URISyntaxException e) {
            LOGGER.error("url格式错误,{}", e.getMessage());
        }
        return new JSONObject();
    }


    /**
     * 私有get请求,URIBuilder已经包含请求参数
     *
     * @param uriBuilder URIBuilder
     * @return JSONObject 请求返回的json数据
     * @throws URISyntaxException URL格式异常
     */
    private static JSONObject get(@NotNull final URIBuilder uriBuilder, final String dataNodeKey) throws URISyntaxException {
        URI uri = uriBuilder.build();
        //创建http GET请求
        return httpExecute(new HttpGet(uri), dataNodeKey);
    }

    /**
     * 向禅道的post请求
     *
     * @param url      请求地址
     * @param paramMap 请求参数
     * @return 请求响应JSONObject
     */
    public static JSONObject post(final String url, @NotNull final Map<String, String> paramMap) {

        List<NameValuePair> formDataList = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, String> dataEntry : paramMap.entrySet()) {
            formDataList.add(new BasicNameValuePair(dataEntry.getKey(), dataEntry.getValue()));
        }

        try {
            URI uri = new URIBuilder(Constant.ZENTAO_ADDRESS + url).build();
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new UrlEncodedFormEntity(formDataList, StandardCharsets.UTF_8));
            return httpExecute(httpPost, Constant.ZENTAO_DATA_NODE);
        } catch (URISyntaxException e) {
            LOGGER.error("url格式错误,{}", e.getMessage());
        }

        return new JSONObject();
    }


    /**
     * 向禅道的post请求
     *
     * @param url         请求地址
     * @param paramMap    请求参数
     * @param dataNodeKey 数据节点
     * @return 请求响应JSONObject
     */
    public static JSONObject post(final String url, @NotNull final Map<String, String> paramMap, final String dataNodeKey) {

        List<NameValuePair> formDataList = new ArrayList<>(paramMap.size());
        for (Map.Entry<String, String> dataEntry : paramMap.entrySet()) {
            formDataList.add(new BasicNameValuePair(dataEntry.getKey(), dataEntry.getValue()));
        }

        try {
            final URI uri = new URIBuilder(Constant.ZENTAO_ADDRESS + url).build();
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new UrlEncodedFormEntity(formDataList, StandardCharsets.UTF_8));
            return httpExecute(httpPost, dataNodeKey);
        } catch (URISyntaxException e) {
            LOGGER.error("url格式错误,{}", e.getMessage());
        }

        return new JSONObject();
    }

    /**
     * 执行http请求并返回JSONObject
     *
     * @param httpUriRequest {HttpGet|HttpPost}
     * @return JSONObject
     */
    private static JSONObject httpExecute(final HttpUriRequest httpUriRequest, final String dataNodeKey) {
        //创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            //执行请求
            final CloseableHttpResponse response = httpclient.execute(httpUriRequest);
            //判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == Constant.HTTP_SUCCESS_CODE) {
                //解析响应数据
                String content = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                JSONObject zentaoReturnObject = JSONObject.parseObject(content);
                if (zentaoReturnObject.getString(Constant.ZENTAO_STATUS_NODE).equals(Constant.ZENTAO_SUCCESS)) {
                    String dataNode = zentaoReturnObject.getString(dataNodeKey);
                    return JSONObject.parseObject(dataNode);
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("禅道请求出错,{}", ioe.getMessage());
        }
        return new JSONObject();
    }
}
