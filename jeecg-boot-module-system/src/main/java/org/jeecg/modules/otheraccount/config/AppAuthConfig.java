package org.jeecg.modules.otheraccount.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方APP配置
 *
 * @author sunjianlei
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "account-dev")
public class AppAuthConfig {
  //禅道
  private String zentaoUrl;
  private String adminAccount;
  private String adminPsw;
  private String defaultDept; //18
  private String defaultGroup; //24
  private String defaultPsw;
  private String defaultRole; //dev

  //GIT-lab
  private String gitUrl;
  private String gitToken;


}
