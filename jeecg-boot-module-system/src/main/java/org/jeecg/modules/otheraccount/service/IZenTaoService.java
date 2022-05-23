package org.jeecg.modules.otheraccount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.otheraccount.entity.ZtUser;

/**
 * 禅道服务
 */
public interface IZenTaoService extends IService<ZtUser> {

  /**
   * 账号查找用户
   * @param username
   * @return
   */
  ZtUser getUser4Account(String username);

  /**
   * 创建禅道用户
   * @param user
   */
  void createZtUser(ZtUser user);

  /**
   * 删除用户
   * @param username
   */
  void delZtUser(String username);

  /**
   * 重置密码
   * @param username
   */
  void resetUserPwd(String username);
}
