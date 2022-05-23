package org.jeecg.modules.otheraccount.util.zentao;


public class Main {

    public static void main(String[] args) {
//    System.out.println(createZentaoUser("test0010", "测试创建账号-001"));
        ZenTaoAPI api = new ZenTaoAPI("", "", "",
            "", "18", "24", "dev");
        api.createZentaoUser("test99901", "测试你好9901", "test001@qq.com");
    }


}
