package org.jeecg.modules.otheraccount.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.Date;
import org.jeecg.common.util.Md5Util;
import org.jeecg.modules.otheraccount.config.AppAuthConfig;
import org.jeecg.modules.otheraccount.entity.ZtUser;
import org.jeecg.modules.otheraccount.mapper.ZtUserMapper;
import org.jeecg.modules.otheraccount.service.IZenTaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 研发资源账号
 * @Author: jeecg-boot
 * @Date: 2022-05-20
 * @Version: V1.0
 */
@Service
@DS("multi-datasource-zentao")
public class ZtUserServiceImpl extends ServiceImpl<ZtUserMapper, ZtUser> implements
    IZenTaoService {

  @Autowired
  private AppAuthConfig appAuthConfig;
  @Autowired
  private ZtUserMapper mapper;

  @Override
  public ZtUser getUser4Account(String username) {
    QueryWrapper<ZtUser> queryWrapper = new QueryWrapper<ZtUser>();
    queryWrapper.eq("account", username);
    ZtUser user = super.getOne(queryWrapper);
    return user;
  }


  @Override
  @Transactional
  public void createZtUser(ZtUser user) {
    QueryWrapper<ZtUser> queryWrapper = new QueryWrapper<ZtUser>();
    queryWrapper.eq("account", user.getAccount());
    long count = super.count(queryWrapper);
    if (count > 0) {
      throw new RuntimeException("账号已经存在");
    }
    //
    user.setPassword(Md5Util.md5Encode(appAuthConfig.getDefaultPsw(), "utf8"));
    //
    user.setDeleted("0");
//    user.setLocked(new Date("0000-00-00"));
    user.setDept(Integer.parseInt(appAuthConfig.getDefaultDept()));
    user.setRole(appAuthConfig.getDefaultRole());
    user.setType("inside");
//    user.setBirthday(new Date("0000-00-00"));
    user.setGender("m");
    user.setFails(0);
    user.setScore(1);
    user.setScorelevel(1);
    user.setClientlang("zh-cn");
    user.setClientstatus("offline");
    //
    user.setCommiter(user.getAccount());
    user.setWeixin(" ");
    user.setDingding(" ");
    user.setSlack(" ");
    user.setWhatsapp(" ");
    //zt_usergroup
    //zt_userview
    super.save(user);
    mapper.saveUserGroup(user.getAccount(), appAuthConfig.getDefaultGroup());
  }


  @Override
  public void delZtUser(String username) {
    ZtUser user = this.getUser4Account(username);
    if (null != user && null != user.getId()) {
      user.setDeleted("1");
      super.updateById(user);
    }
  }


  @Override
  public void resetUserPwd(String username) {
    ZtUser user = this.getUser4Account(username);
    if (null != user && null != user.getId()) {
      user.setPassword(Md5Util.md5Encode(appAuthConfig.getDefaultPsw(), "utf8"));
      super.updateById(user);
    }

  }


}
