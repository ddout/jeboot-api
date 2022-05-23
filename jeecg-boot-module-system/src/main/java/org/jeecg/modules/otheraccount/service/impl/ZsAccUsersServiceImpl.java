package org.jeecg.modules.otheraccount.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.User;
import org.jeecg.modules.otheraccount.config.AppAuthConfig;
import org.jeecg.modules.otheraccount.entity.ZsAccUsers;
import org.jeecg.modules.otheraccount.entity.ZtUser;
import org.jeecg.modules.otheraccount.mapper.ZsAccUsersMapper;
import org.jeecg.modules.otheraccount.service.IZenTaoService;
import org.jeecg.modules.otheraccount.service.IZsAccUsersService;
import org.jeecg.modules.otheraccount.util.PinYinUtil;
import org.jeecg.modules.otheraccount.util.vpn.VpnApiUtil;
import org.jeecg.modules.otheraccount.util.vpn.VpnUser;
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
  @Autowired
  private IZenTaoService zenTaoService;

  @Override
  public boolean removeById(Serializable id) {
    ZsAccUsers u = super.getById(id);
    if(null != u){
      u.setUstatus("禁用");
      return updateById(u);
    }
    return false;
  }

  @Override
  public void resetPwd(List<String> asList) {
    //默认密码
    String password = appAuthConfig.getDefaultPsw();
    //
    for(String id : asList){
      ZsAccUsers zsAccUsers = super.getById(id);
      if(null != zsAccUsers){
        //GIT
        if(!StringUtils.isEmpty(zsAccUsers.getUaccGit())){
          User gitUser = getGitUser(zsAccUsers.getUaccGit());
          if (null != gitUser && null != gitUser.getId()) {
            GitLabApi gitLabApi = new GitLabApi(appAuthConfig.getGitUrl(),
                appAuthConfig.getGitToken());
            try {
              gitLabApi.getUserApi().updateUser(gitUser, password);
            } catch (GitLabApiException e) {
              e.printStackTrace();
              throw new RuntimeException("Git账号密码重置失败！", e);
            }
          }
        }
        //VPN
        if(!StringUtils.isEmpty(zsAccUsers.getUaccVpn())){
          VpnApiUtil vpnApiUtil = new VpnApiUtil(appAuthConfig.getVpnUrl())
              .login(appAuthConfig.getVpnAdmin(), appAuthConfig.getVpnPwd());
          VpnUser vpnUser = vpnApiUtil.getVpnUser(zsAccUsers.getUaccVpn());
          if (null != vpnUser && null != vpnUser.getId()) {
            vpnApiUtil.resetPwdVpnUser(vpnUser.getUsername(), password);
          }
        }
        //禅道
        if(!StringUtils.isEmpty(zsAccUsers.getUaccZentao())){
          zenTaoService.resetUserPwd(zsAccUsers.getUaccZentao());
        }
      }
    }
  }

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

    //选择开启哪些账号-uaccs: GIT,VPN,ZENTAO
    String uaccs = zsAccUsers.getUaccs();
    //
    //
    String unamePinyin = PinYinUtil.strToPinyin(zsAccUsers.getUname());
    //
    if (StringUtils.isEmpty(zsAccUsers.getUemail())) {
      zsAccUsers.setUemail(unamePinyin + "@qq.com");
    }
    //
    //GIT
    if (uaccs.indexOf("GIT") > -1) {
      User gitUser = getGitUser(unamePinyin);
      if (null != gitUser && null != gitUser.getId()) {
        zsAccUsers.setUaccGit(gitUser.getUsername());
      } else {
        //创建git账号
        gitUser = createGitUser(unamePinyin, zsAccUsers);
        if (null == gitUser) {
          throw new RuntimeException("Git账号创建失败！");
        }
        zsAccUsers.setUaccGit(gitUser.getUsername());
      }
    }
    //VPN
    if (uaccs.indexOf("VPN") > -1) {
      VpnApiUtil vpnApiUtil = new VpnApiUtil(appAuthConfig.getVpnUrl())
          .login(appAuthConfig.getVpnAdmin(), appAuthConfig.getVpnPwd());
      VpnUser vpnUser = vpnApiUtil.getVpnUser(unamePinyin);
      if (null != vpnUser && null != vpnUser.getId()) {
        zsAccUsers.setUaccVpn(vpnUser.getUsername());
      } else {
        //创建vpn账号
        vpnApiUtil
            .createVpnUser(zsAccUsers.getUname(), unamePinyin, appAuthConfig.getDefaultPsw(),
                zsAccUsers.getUemail());
        zsAccUsers.setUaccVpn(unamePinyin);
      }
    }
    //ZENTAO
    if (uaccs.indexOf("ZENTAO") > -1) {
      ZtUser ztUser = zenTaoService.getUser4Account(unamePinyin);
      if (null != ztUser && null != ztUser.getId()) {
        zsAccUsers.setUaccZentao(unamePinyin);
      } else {
        //创建vpn账号
        ztUser = new ZtUser();
        ztUser.setAccount(unamePinyin);
        ztUser.setRealname(zsAccUsers.getUname());
        ztUser.setEmail(zsAccUsers.getUemail());
        zenTaoService.createZtUser(ztUser);
        zsAccUsers.setUaccZentao(unamePinyin);
      }
    }
    zsAccUsers.setUstatus("启用");
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


  /**
   * 创建git用户
   *
   * @param account ZsAccUsers 账号信息
   * @return org.gitlab4j.api.models.User
   */
  private User createGitUser(String username, ZsAccUsers account) {
    GitLabApi gitLabApi = new GitLabApi(appAuthConfig.getGitUrl(),
        appAuthConfig.getGitToken());
    // Create a new user with no password who will recieve a reset password email
    User userConfig = new User()
        .withEmail(account.getUemail())
        .withName(account.getUname())
        .withUsername(username);
    //默认密码
    String password = appAuthConfig.getDefaultPsw();
    //不发送邮件
    boolean sendResetPasswordEmail = false;
    //
    User x = null;
    try {
      x = gitLabApi.getUserApi().createUser(userConfig, password, sendResetPasswordEmail);
    } catch (GitLabApiException e) {
      e.printStackTrace();
      throw new RuntimeException("Git账号创建失败！", e);
    }
    return x;
  }


  @Override
  public boolean updateById(ZsAccUsers entity) {
    //
    if (StringUtils.isEmpty(entity.getId())) {
      throw new RuntimeException("null id !");
    }
    //
    ZsAccUsers zsAccUsers = super.getById(entity.getId());
    //
    zsAccUsers.setUemail(entity.getUemail());
    zsAccUsers.setUphone(entity.getUphone());
    zsAccUsers.setUstatus(entity.getUstatus());
    //
    //
    String unamePinyin = PinYinUtil.strToPinyin(zsAccUsers.getUname());
    //
    if (StringUtils.isEmpty(zsAccUsers.getUemail())) {
      zsAccUsers.setUemail(unamePinyin + "@qq.com");
    }
    //选择开启哪些账号-uaccs: GIT,VPN,ZENTAO
    String uaccs = zsAccUsers.getUaccs();
    //
    if (!"启用".equals(entity.getUstatus())) {
      //禁用
      //GIT
      if (uaccs.indexOf("GIT") > -1) {
        User gitUser = getGitUser(unamePinyin);
        if (null != gitUser && null != gitUser.getId()) {
          GitLabApi gitLabApi = new GitLabApi(appAuthConfig.getGitUrl(),
              appAuthConfig.getGitToken());
          try {
            gitLabApi.getUserApi().blockUser(gitUser.getId());
          } catch (GitLabApiException e) {
            throw new RuntimeException("Git账号禁用失败！", e);
          }
        }
      }
      //VPN
      if (uaccs.indexOf("VPN") > -1) {
        VpnApiUtil vpnApiUtil = new VpnApiUtil(appAuthConfig.getVpnUrl())
            .login(appAuthConfig.getVpnAdmin(), appAuthConfig.getVpnPwd());
        VpnUser vpnUser = vpnApiUtil.getVpnUser(unamePinyin);
        if (null != vpnUser && null != vpnUser.getId()) {
          vpnApiUtil.blockVpnUser(vpnUser.getUsername(),appAuthConfig.getDefaultPsw());
        }
      }
      //ZENTAO
      if (uaccs.indexOf("ZENTAO") > -1) {
        ZtUser ztUser = zenTaoService.getUser4Account(unamePinyin);
        if (null != ztUser && null != ztUser.getId()) {
         zenTaoService.delZtUser(unamePinyin);
        }
      }
    } else {
      //
      //GIT
      if (uaccs.indexOf("GIT") > -1) {
        User gitUser = getGitUser(unamePinyin);
        if (null != gitUser && null != gitUser.getId()) {
          zsAccUsers.setUaccGit(gitUser.getUsername());
          GitLabApi gitLabApi = new GitLabApi(appAuthConfig.getGitUrl(),
              appAuthConfig.getGitToken());
          try {
            gitLabApi.getUserApi().unblockUser(gitUser.getId());
          } catch (GitLabApiException e) {
            throw new RuntimeException("Git账号禁用失败！", e);
          }
        } else {
          //创建git账号
          gitUser = createGitUser(unamePinyin, zsAccUsers);
          if (null == gitUser) {
            throw new RuntimeException("Git账号创建失败！");
          }
          zsAccUsers.setUaccGit(gitUser.getUsername());
        }
      }
      //VPN
      if (uaccs.indexOf("GIT") > -1) {
        VpnApiUtil vpnApiUtil = new VpnApiUtil(appAuthConfig.getVpnUrl())
            .login(appAuthConfig.getVpnAdmin(), appAuthConfig.getVpnPwd());
        VpnUser vpnUser = vpnApiUtil.getVpnUser(unamePinyin);
        if (null != vpnUser && null != vpnUser.getId()) {
          zsAccUsers.setUaccVpn(vpnUser.getUsername());
          vpnApiUtil.UnBlockVpnUser(vpnUser.getUsername(),appAuthConfig.getDefaultPsw());
        } else {
          //创建vpn账号
          vpnApiUtil
              .createVpnUser(zsAccUsers.getUname(), unamePinyin, appAuthConfig.getDefaultPsw(),
                  zsAccUsers.getUemail());
          zsAccUsers.setUaccVpn(unamePinyin);
        }
      }
      //ZENTAO
      if (uaccs.indexOf("ZENTAO") > -1) {
        ZtUser ztUser = zenTaoService.getUser4Account(unamePinyin);
        if (null != ztUser && null != ztUser.getId()) {
          zsAccUsers.setUaccZentao(unamePinyin);
          zenTaoService.unBlockZtUser(ztUser);
        } else {
          //创建vpn账号
          ztUser = new ZtUser();
          ztUser.setAccount(unamePinyin);
          ztUser.setRealname(zsAccUsers.getUname());
          ztUser.setEmail(zsAccUsers.getUemail());
          zenTaoService.createZtUser(ztUser);
          zsAccUsers.setUaccZentao(unamePinyin);
        }
      }
    }
    //
    return SqlHelper.retBool(this.getBaseMapper().updateById(zsAccUsers));
  }


}
