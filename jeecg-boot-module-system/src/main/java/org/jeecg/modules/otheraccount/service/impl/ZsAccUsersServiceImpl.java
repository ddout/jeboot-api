package org.jeecg.modules.otheraccount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.User;
import org.jeecg.modules.otheraccount.config.AppAuthConfig;
import org.jeecg.modules.otheraccount.entity.ZsAccUsers;
import org.jeecg.modules.otheraccount.mapper.ZsAccUsersMapper;
import org.jeecg.modules.otheraccount.service.IZsAccUsersService;
import org.jeecg.modules.otheraccount.util.PinYinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 研发资源账号
 * @Author: jeecg-boot
 * @Date: 2022-05-20
 * @Version: V1.0
 */
@Service
public class ZsAccUsersServiceImpl extends ServiceImpl<ZsAccUsersMapper, ZsAccUsers> implements
    IZsAccUsersService {

  @Autowired
  private AppAuthConfig appAuthConfig;

  @Override
  public boolean save(ZsAccUsers zsAccUsers) {
    //
    //
    /**
     * 1、验证姓名是否在本库存在</br>
     * 2、姓名转拼音</br>
     * 3、验证git账号是否在git存在（存在则写入本库的git账号字段，不存在则创建git账号并写入本库git账号字段）</br>
     * 4、验证禅道账号是否在禅道存在（存在则写入本库的禅道账号字段，不存在则创建禅道账号并写入本库禅道账号字段）</br>
     * 5、验证VPN账号是否在VPN存在（存在则写入本库的VPN账号字段，不存在则创建VPN账号并写入本库VPN账号字段）</br>
     */
    //
    QueryWrapper<ZsAccUsers> queryWrapper = new QueryWrapper<ZsAccUsers>();
    queryWrapper.eq("uname", zsAccUsers.getUname());
    long count = super.count(queryWrapper);
    if (count > 0) {
      throw new RuntimeException("姓名已经存在");
    }
    //uaccs: GIT,VPN,ZENTAO
    String uaccs = zsAccUsers.getUaccs();

    //
    String unamePinyin = PinYinUtil.strToPinyin(zsAccUsers.getUname());
    //GIT
    if("GIT".indexOf(uaccs) > 0){
      User gitUser = getGitUser(unamePinyin);
      if (null != gitUser && null != gitUser.getId()) {
        zsAccUsers.setUaccGit(gitUser.getUsername());
      } else {
        //创建git账号
        zsAccUsers.setUaccGit(gitUser.getUsername());
      }
    }
    //VPN
    if("VPN".indexOf(uaccs) > 0){
    }
    //ZENTAO
    if("ZENTAO".indexOf(uaccs) > 0){
    }
    return super.save(zsAccUsers);
  }


  /**
   * 通过账号查询git用户(未获取到则返回null)
   *
   * @param account git账号
   * @return org.gitlab4j.api.models.User
   */
  private User getGitUser(String account) {
    GitLabApi gitLabApi = new GitLabApi(appAuthConfig.getGitUrl(),
        appAuthConfig.getGitToken());
    //
    User u3 = null;
    try {
      u3 = gitLabApi.getUserApi().getUser(account);
    } catch (GitLabApiException e) {
      throw new RuntimeException("Git账号获取失败！", e);
    }
    return u3;
  }


}
