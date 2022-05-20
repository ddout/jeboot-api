package org.jeecg.modules.otheraccount.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 研发资源账号
 * @Author: jeecg-boot
 * @Date:   2022-05-20
 * @Version: V1.0
 */
@Data
@TableName("zs_acc_users")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zs_acc_users对象", description="研发资源账号")
public class ZsAccUsers implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**所属部门*/
	@Excel(name = "所属部门", width = 15)
    @ApiModelProperty(value = "所属部门")
    private String udept;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String uname;
	/**手机*/
	@Excel(name = "手机", width = 15)
    @ApiModelProperty(value = "手机")
    private String uphone;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String uemail;
	/**账号状态*/
	@Excel(name = "账号状态", width = 15, dicCode = "disableer")
	@Dict(dicCode = "disableer")
    @ApiModelProperty(value = "账号状态")
    private String ustatus;
	/**开通账号*/
	@Excel(name = "开通账号", width = 15, dicCode = "zs_acc_sys")
	@Dict(dicCode = "zs_acc_sys")
    @ApiModelProperty(value = "开通账号")
    private String uaccs;
	/**GIT账号*/
	@Excel(name = "GIT账号", width = 15)
    @ApiModelProperty(value = "GIT账号")
    private String uaccGit;
	/**VPN账号*/
	@Excel(name = "VPN账号", width = 15)
    @ApiModelProperty(value = "VPN账号")
    private String uaccVpn;
	/**禅道账号*/
	@Excel(name = "禅道账号", width = 15)
    @ApiModelProperty(value = "禅道账号")
    private String uaccZentao;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
}
