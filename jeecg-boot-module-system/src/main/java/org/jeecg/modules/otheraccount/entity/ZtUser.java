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
 * @Description: zt_user
 * @Author: jeecg-boot
 * @Date:   2022-05-23
 * @Version: V1.0
 */
@Data
@TableName("zt_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="zt_user对象", description="zt_user")
public class ZtUser implements Serializable {
    private static final long serialVersionUID = 1L;

	/**id*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Long id;
	/**dept*/
	@Excel(name = "dept", width = 15)
    @ApiModelProperty(value = "dept")
    private Integer dept;
	/**account*/
	@Excel(name = "account", width = 15)
    @ApiModelProperty(value = "account")
    private String account;
	/**type*/
	@Excel(name = "type", width = 15)
    @ApiModelProperty(value = "type")
    private String type;
	/**password*/
	@Excel(name = "password", width = 15)
    @ApiModelProperty(value = "password")
    private String password;
	/**role*/
	@Excel(name = "role", width = 15)
    @ApiModelProperty(value = "role")
    private String role;
	/**realname*/
	@Excel(name = "realname", width = 15)
    @ApiModelProperty(value = "realname")
    private String realname;
	/**nickname*/
	@Excel(name = "nickname", width = 15)
    @ApiModelProperty(value = "nickname")
    private String nickname;
	/**commiter*/
	@Excel(name = "commiter", width = 15)
    @ApiModelProperty(value = "commiter")
    private String commiter;
	/**avatar*/
	@Excel(name = "avatar", width = 15)
    @ApiModelProperty(value = "avatar")
    private String avatar;
	/**birthday*/
	@Excel(name = "birthday", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "birthday")
    private Date birthday;
	/**gender*/
	@Excel(name = "gender", width = 15)
    @ApiModelProperty(value = "gender")
    private String gender;
	/**email*/
	@Excel(name = "email", width = 15)
    @ApiModelProperty(value = "email")
    private String email;
	/**skype*/
	@Excel(name = "skype", width = 15)
    @ApiModelProperty(value = "skype")
    private String skype;
	/**qq*/
	@Excel(name = "qq", width = 15)
    @ApiModelProperty(value = "qq")
    private String qq;
	/**yahoo*/
	@Excel(name = "yahoo", width = 15)
    @ApiModelProperty(value = "yahoo")
    private String yahoo;
	/**gtalk*/
	@Excel(name = "gtalk", width = 15)
    @ApiModelProperty(value = "gtalk")
    private String gtalk;
	/**wangwang*/
	@Excel(name = "wangwang", width = 15)
    @ApiModelProperty(value = "wangwang")
    private String wangwang;
	/**mobile*/
	@Excel(name = "mobile", width = 15)
    @ApiModelProperty(value = "mobile")
    private String mobile;
	/**phone*/
	@Excel(name = "phone", width = 15)
    @ApiModelProperty(value = "phone")
    private String phone;
	/**weixin*/
	@Excel(name = "weixin", width = 15)
    @ApiModelProperty(value = "weixin")
    private String weixin;
	/**dingding*/
	@Excel(name = "dingding", width = 15)
    @ApiModelProperty(value = "dingding")
    private String dingding;
	/**slack*/
	@Excel(name = "slack", width = 15)
    @ApiModelProperty(value = "slack")
    private String slack;
	/**whatsapp*/
	@Excel(name = "whatsapp", width = 15)
    @ApiModelProperty(value = "whatsapp")
    private String whatsapp;
	/**address*/
	@Excel(name = "address", width = 15)
    @ApiModelProperty(value = "address")
    private String address;
	/**zipcode*/
	@Excel(name = "zipcode", width = 15)
    @ApiModelProperty(value = "zipcode")
    private String zipcode;
//	/**join*/
//	@Excel(name = "join", width = 15, format = "yyyy-MM-dd")
//	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern="yyyy-MM-dd")
//    @ApiModelProperty(value = "join")
//    private Date join;
	/**visits*/
	@Excel(name = "visits", width = 15)
    @ApiModelProperty(value = "visits")
    private Integer visits;
	/**ip*/
	@Excel(name = "ip", width = 15)
    @ApiModelProperty(value = "ip")
    private String ip;
	/**last*/
	@Excel(name = "last", width = 15)
    @ApiModelProperty(value = "last")
    private Integer last;
	/**fails*/
	@Excel(name = "fails", width = 15)
    @ApiModelProperty(value = "fails")
    private Integer fails;
	/**locked*/
	@Excel(name = "locked", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "locked")
    private Date locked;
	/**ranzhi*/
	@Excel(name = "ranzhi", width = 15)
    @ApiModelProperty(value = "ranzhi")
    private String ranzhi;
	/**score*/
	@Excel(name = "score", width = 15)
    @ApiModelProperty(value = "score")
    private Integer score;
	/**scorelevel*/
	@Excel(name = "scorelevel", width = 15)
    @ApiModelProperty(value = "scorelevel")
    private Integer scorelevel;
	/**deleted*/
	@Excel(name = "deleted", width = 15)
    @ApiModelProperty(value = "deleted")
    private String deleted;
	/**clientstatus*/
	@Excel(name = "clientstatus", width = 15)
    @ApiModelProperty(value = "clientstatus")
    private String clientstatus;
	/**clientlang*/
	@Excel(name = "clientlang", width = 15)
    @ApiModelProperty(value = "clientlang")
    private String clientlang;
}
