package org.jeecg.modules.otheraccount.util.zentao;

import com.alibaba.fastjson.JSONObject;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jeecg.modules.otheraccount.util.zentao.proxy.Authentication;
import org.jeecg.modules.otheraccount.util.zentao.proxy.application.ApiAdapter;

public class Main {

    public static void main(String[] args){
//        Map<String,String> paramMap = new LinkedHashMap<>();
//        paramMap.put(ApiAdapter.ZENTAO_MODULE_TAG,"bug");
//        paramMap.put(ApiAdapter.ZENTAO_FUNCTION_TAG,"view");
//        //设置请求参数id为1
//        paramMap.put("id","1");
//        ApiAdapter.get(paramMap);


        //
        System.out.println(ZenTaoAPI.createZentaoUser("test0011", "测试创建账号-001"));
        //
    }


}
