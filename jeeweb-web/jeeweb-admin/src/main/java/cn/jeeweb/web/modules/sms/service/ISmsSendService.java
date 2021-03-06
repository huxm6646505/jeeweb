package cn.jeeweb.web.modules.sms.service;


import cn.jeeweb.common.sms.data.SmsResult;

import java.util.Map;

/**
 * @Title: 短信模版
 * @Description: 短信模版
 * @author 王存见
 * @date 2018-09-14 10:50:52
 * @version V1.0
 *
 */
public interface ISmsSendService {

	SmsResult send(String phone, String code, Map<String, Object> datas);

	SmsResult send(String[] phones, String code, Map<String, Object> datas);


}
