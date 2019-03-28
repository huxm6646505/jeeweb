package cn.jeeweb.common.sms.client;

import cn.jeeweb.common.sms.config.SmsConfigProperties;
import cn.jeeweb.common.sms.data.SmsResult;
import cn.jeeweb.common.sms.exception.SmsException;
import cn.jeeweb.common.utils.mapper.JsonMapper;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.dahantc.api.sms.json.JSONHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By kjt.gzst.gov.cn
 *
 * @version V1.0
 * @title: IOSSClient.java
 * @description: 大汉三通的客户端操作
 * @author: huxm
 * @date: 2018年11月22日 下午9:55:00
 * @copyright:
 */
public class DhstSmsClient implements ISmsClient {
    //是否开启
    private Boolean isOpen;
    //签名
    private String signName;
    //阿里云API的密钥Access Key ID
    private  String accessKeyId;
    //阿里云API的密钥Access Key Secret
    private  String accessKeySecret;
    private SmsConfigProperties smsConfigProperties;
    private JSONHttpClient jsonHttpClient ;

    @Override
    public void init(SmsConfigProperties smsConfigProperties) {
        this.smsConfigProperties = smsConfigProperties;
        isOpen=this.smsConfigProperties.getOpen();
        signName=this.smsConfigProperties.getSignName();
        accessKeyId = this.smsConfigProperties.getDhst().getAccessKeyId();
        accessKeySecret = this.smsConfigProperties.getDhst().getAccessKeySecret();
        try {
            jsonHttpClient = new JSONHttpClient("http://www.dh3t.com");
        }catch (Exception e){
            throw new SmsException("初始化失败");
        }
    }

    @Override
    public SmsResult send(String phone, String template) {
        Map<String, Object> datas = new HashMap<>();
        return send(phone,template,datas);
    }

    @Override
    public SmsResult send(String phone, String content, Map<String, Object> datas) {
        if (!isOpen){
            return SmsResult.success("测试成功");
        }
        SmsResult smsResult;
        try {
            // 这里需要处理templateId

            jsonHttpClient.setRetryCount(1);
            String result = jsonHttpClient.sendSms(accessKeyId, accessKeySecret, phone, content, signName, null);
            JSONObject jsonresult= JSONObject.parseObject(result);

            //jsonresult.put("yzmid",datas.get("uuid"));
            if ("0".equals(jsonresult.getString("result"))) {
                smsResult= SmsResult.success("发送成功");
                smsResult.setSmsid((String)datas.get("uuid"));
            } else {
                // 异常返回输出错误码和错误信息
                smsResult = SmsResult.fail("服务器异常");
            }
            smsResult.setReponseData(jsonresult.toJSONString());
            System.out.println("=======6");
        }catch (IllegalArgumentException e){
            smsResult = SmsResult.fail("发送短信提交的参数不对");
        }catch (Exception e){
            smsResult = SmsResult.fail(e.getMessage());
        }
        return smsResult;
    }
}
