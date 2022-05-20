package org.jeecg.modules.otheraccount.service.impl;

import org.jeecg.modules.otheraccount.config.AppAuthConfig;
import org.jeecg.modules.otheraccount.entity.ZsAccUsers;
import org.jeecg.modules.otheraccount.mapper.ZsAccUsersMapper;
import org.jeecg.modules.otheraccount.service.IZsAccUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 研发资源账号
 * @Author: jeecg-boot
 * @Date:   2022-05-20
 * @Version: V1.0
 */
@Service
public class ZsAccUsersServiceImpl extends ServiceImpl<ZsAccUsersMapper, ZsAccUsers> implements IZsAccUsersService {

  @Autowired
  private AppAuthConfig appAuthConfig;

  @Override
  public boolean save(ZsAccUsers zsAccUsers) {
    //
    //
    System.out.println(appAuthConfig);

    /**
     * 1、验证姓名是否在本库存在
     * 2、姓名转拼音
     * 3、验证git账号是否在git存在（存在则写入本库的git账号字段，不存在则创建git账号并写入本库git账号字段）
     * 4、验证禅道账号是否在禅道存在（存在则写入本库的禅道账号字段，不存在则创建禅道账号并写入本库禅道账号字段）
     * 5、验证VPN账号是否在VPN存在（存在则写入本库的VPN账号字段，不存在则创建VPN账号并写入本库VPN账号字段）
     */


    return super.save(zsAccUsers);
  }


}
