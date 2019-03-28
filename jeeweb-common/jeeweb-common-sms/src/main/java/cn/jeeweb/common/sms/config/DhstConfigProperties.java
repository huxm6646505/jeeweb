package cn.jeeweb.common.sms.config;

import cn.jeeweb.common.utils.PropertiesUtil;

public class DhstConfigProperties {


    public static final String DEFAULT_CONFIG_FILE = "dhst.sms.properties";

    //access key id
    String accessKeyId="";
    //access key secret
    String accessKeySecret="";

    public static SmsConfigProperties buildConfigProperties() {
        return buildConfigProperties(DEFAULT_CONFIG_FILE);
    }

    public static SmsConfigProperties buildConfigProperties(String propertiesName) {
        PropertiesUtil p = new PropertiesUtil(propertiesName);
        SmsConfigProperties smsConfigProperties = new SmsConfigProperties();
        smsConfigProperties.setOpen(p.getBoolean("sms.open"));
        smsConfigProperties.setSignName(p.getString("sms.sign-name"));
        DhstConfigProperties aliyunConfig = new DhstConfigProperties();
        aliyunConfig.setAccessKeyId(p.getString("sms.dhst.access-key-id"));
        aliyunConfig.setAccessKeySecret(p.getString("sms.dhst.access-key-secret"));
        smsConfigProperties.setDhst(aliyunConfig);
        return smsConfigProperties;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

}
