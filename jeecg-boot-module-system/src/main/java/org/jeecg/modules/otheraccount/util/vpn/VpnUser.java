package org.jeecg.modules.otheraccount.util.vpn;

import java.util.Date;
import lombok.Data;

@Data
public class VpnUser {
  /**
   * "updated_at": "2022-02-28T08:43:56Z",
   * "pin_code": "duanbin",
   * "nickname": "段彬",
   * "groups": [
   * "智审数据"
   * ],
   * "send_email": false,
   * "created_at": "2022-02-28T08:01:05Z",
   * "id": 2,
   * "disable_otp": true,
   * "otp_secret": "22KLAOYB5S3C5LHIHC4VTTT7JUPAZQMT",
   * "email": "duanbin@aiaudit.cn",
   * "username": "duanbin",
   * "status": 1
   */

    private Long id;
    private Date updatedAt;
    private String pinCode;
    private String nickname;
    private String[] groups;
    private Boolean disableOtp;
    private String otpSecret;
    private String email;
    private String username;
    //1=正常
    private Integer status;
}
