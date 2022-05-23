package org.jeecg.modules.otheraccount.service;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import java.io.Serializable;
import java.util.List;
import org.jeecg.modules.otheraccount.entity.ZsAccUsers;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 研发资源账号
 * @Author: jeecg-boot
 * @Date:   2022-05-20
 * @Version: V1.0
 */
public interface IZsAccUsersService extends IService<ZsAccUsers> {

    void resetPwd(List<String> asList);

    boolean save(ZsAccUsers zsAccUsers);
    boolean removeById(Serializable id);
}
