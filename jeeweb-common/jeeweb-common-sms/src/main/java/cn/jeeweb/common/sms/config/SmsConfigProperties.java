package cn.jeeweb.common.sms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * All rights Reserved, Designed By www.jeeweb.cn
 *
 * @version V1.0
 * @package cn.jeeweb.common.oss.config
 * @title:
 * @description: SMS配置
 * @author: 王存见
 * @date: 2018/4/26 9:43
 * @copyright: 2017 www.jeeweb.cn Inc. All rights reserved.
 */
@ConfigurationProperties(prefix = "sms")
public class SmsConfigProperties {

    //使用的上传存储空间,aliyun:阿里云
    String smsType="aliyun";
    //签名
    String signName="";
    //是否开启
    private Boolean open=Boolean.FALSE;

    private AliyunConfigProperties aliyun;

    public void setDhst(DhstConfigProperties dhst) {
        this.dhst = dhst;
    }

    public DhstConfigProperties getDhst() {
        return dhst;
    }

    private DhstConfigProperties dhst;

    private HuyiConfigProperties huyi;

    private TencentConfigProperties tencent;

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public AliyunConfigProperties getAliyun() {
        return aliyun;
    }



    public void setAliyun(AliyunConfigProperties aliyun) {
        this.aliyun = aliyun;
    }

    public HuyiConfigProperties getHuyi() {
        return huyi;
    }

    public void setHuyi(HuyiConfigProperties huyi) {
        this.huyi = huyi;
    }

    public TencentConfigProperties getTencent() {
        return tencent;
    }

    public void setTencent(TencentConfigProperties tencent) {
        this.tencent = tencent;
    }
}
