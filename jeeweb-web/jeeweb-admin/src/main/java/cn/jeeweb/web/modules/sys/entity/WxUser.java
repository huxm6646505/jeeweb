package cn.jeeweb.web.modules.sys.entity;

import cn.jeeweb.web.common.entity.DataEntity;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * Created by huxm on 2018/12/12
 */
@TableName("wx_user")
public class WxUser extends DataEntity<String> {
    /**主键*/
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**编号*/
    @TableId(value = "nickname")
    private String nickname;
    /**name*/
    @TableId(value = "realname")
    private String realname;
    @TableId(value = "openid")
    private String openid;
    @TableId(value = "phone")
    private String phone;
    @TableId(value = "sex")
    private String sex;
    @TableId(value = "email")
    private String email;
    @TableId(value = "password")
    private String password;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
