package org.jeecg.modules.otheraccount.util.gitlab;

import java.util.List;
import java.util.Optional;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;

public class Main {

  public static void main(String[] args) throws GitLabApiException {
    // Create a GitLabApi instance to communicate with your GitLab server
    GitLabApi gitLabApi = new GitLabApi("http://192.168.11.202/",
        "BFFVZagViSji5SBUG754");

    // Get the list of projects your account has access to



    // Create a new user with no password who will recieve a reset password email
    User userConfig = new User()
        .withEmail("test001@qq.com")
        .withName("你好测试账号0001")
        .withUsername("test00002");
    String password = "Abc@123456";
    boolean sendResetPasswordEmail = false;
    User x = gitLabApi.getUserApi().createUser(userConfig, password, sendResetPasswordEmail);
    System.out.println(x);

    User u3 = gitLabApi.getUserApi().getUser("test00002");
    System.out.println(u3);
    gitLabApi.getUserApi().blockUser(u3.getId());

  }


}
