package org.jeecg.modules.otheraccount.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.otheraccount.entity.ZsAccUsers;
import org.jeecg.modules.otheraccount.entity.ZtUser;

/**
 * @Description: 研发资源账号
 * @Author: jeecg-boot
 * @Date:   2022-05-20
 * @Version: V1.0
 */
public interface ZtUserMapper extends BaseMapper<ZtUser> {

  void saveUserGroup(@Param("account") String account,@Param("defaultGroup") String defaultGroup);
}
